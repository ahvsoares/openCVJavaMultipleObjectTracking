/*
 * Previously written in C++ by  Kyle Hounslow 2013.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE. 
 */

/*
 * Translated to Java by Alexandre Henrique Vieira Soares 2015, merging codes from the references below:
 * - https://www.youtube.com/watch?v=RS_uQGOQIdg
 * - http://cell0907.blogspot.com.es/2013/07/detecting-faces-in-your-webcam-stream.html
 */

package br.cefetmg.lsi.opencv.multipleObjectTracking.processing;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import br.cefetmg.lsi.opencv.multipleObjectTracking.entity.Ball;
import br.cefetmg.lsi.opencv.multipleObjectTracking.gui.CalibrationWindow;
import br.cefetmg.lsi.opencv.multipleObjectTracking.gui.Panel;
import br.cefetmg.lsi.opencv.multipleObjectTracking.utils.PropertiesLoaderImpl;

public class MultipleObjectTracking {
	private final boolean calibrationMode = new Boolean(PropertiesLoaderImpl.getValor("multipleObjectTracking.calibrationMode"));

	private CalibrationWindow calibrationWindow = new CalibrationWindow();
	private JFrame frameCamera;
	private JFrame frameThreshold;
	private Panel panelCamera = new Panel();
	private Panel panelThreshold = new Panel();
	
	//max number of objects to be detected in frame
	private final int MAX_NUM_OBJECTS = 50;
	
	//minimum and maximum object area
	private final int MIN_OBJECT_AREA = 40 * 40;

	public void startTracking() throws Exception {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);		
		
		mountFrames();
		
		// Matrices for image processing.
		Mat image = new Mat();
		Mat thresholdedImage = new Mat();
		Mat hsvImage = new Mat();
		
		// Opens camera capture flow.
		VideoCapture capture = null;
		String imagesource = PropertiesLoaderImpl.getValor("multipleObjectTracking.imagesource");
		if (imagesource.equalsIgnoreCase("webcam")){
			capture = new VideoCapture(0);			
		} else {
			
			if (imagesource.equalsIgnoreCase("ipcam")){
				String ipcamAddress = PropertiesLoaderImpl.getValor("multipleObjectTracking.imagesource.ipcam.address");
		    	capture = new VideoCapture(ipcamAddress);				
			}
			
		}
		
		if (capture == null){
			throw new Exception("Could not conect to camera.");
		}
		
		// Captures one image, for starting the process.
		try{
			capture.read(image);
		} catch (Exception e){
			throw new Exception("Could not read from camera. Maybe the URL is not correct.");
		}
		 
		setFramesSizes(image);
		
		if (capture.isOpened()) {
			
			while (true) {
				capture.read(image);
				
				if (!image.empty()) {
					Imgproc.cvtColor(image, hsvImage, Imgproc.COLOR_BGR2HSV);
		
					if (calibrationMode){
						thresholdedImage = processImage(hsvImage
								, new Scalar(calibrationWindow.getMinHValue(), calibrationWindow.getMinSValue(), calibrationWindow.getMinVValue())
								, new Scalar(calibrationWindow.getMaxHValue(), calibrationWindow.getMaxSValue(), calibrationWindow.getMaxVValue()));						
						trackFilteredObject(null, thresholdedImage, image);
						updateFrames(image, thresholdedImage);
					} else {
						Ball redBall = new Ball(Ball.Colours.RED);
						Ball greenBall = new Ball(Ball.Colours.GREEN);
						Ball blueBall = new Ball(Ball.Colours.BLUE);
						
						ArrayList<Ball> balls = new ArrayList<Ball>();
						balls.add(redBall);
						balls.add(greenBall);
						balls.add(blueBall);
						
						for (Ball ball : balls){
							thresholdedImage = processImage(hsvImage, ball.getHsvMin(), ball.getHsvMax());
							trackFilteredObject(ball, thresholdedImage, image);
							updateFrames(image, thresholdedImage);
						}

					}
					
				} else {
					throw new Exception("Could not read camera image.");
				}
				
				
			}
			
		} else {
			throw new Exception("Could not read from camera.");
		}
		
	}
	
	private Mat processImage(Mat hsvImage, Scalar hsvMin, Scalar hsvMax){
		Mat thresholdedImage = new Mat();
		Core.inRange(hsvImage, hsvMin , hsvMax, thresholdedImage);
		morphOps(thresholdedImage);
		
		return thresholdedImage;
	}

	private void updateFrames(Mat image, Mat thresholdedImage) {
		setPanelsImages(image, thresholdedImage);
		repaintFrames();
	}
	
	private void mountFrames(){
		
		if (calibrationMode){
			createTrackingFrame();		
			frameThreshold = creatFrame("Threshold", panelThreshold);
		}
		
		frameCamera = creatFrame("Câmera", panelCamera);
	}

	private void createTrackingFrame() {
		calibrationWindow.getFrame().setVisible(true);
	}
	
	private JFrame creatFrame(String frameName, Panel panel){
		JFrame frame = new JFrame(frameName); 
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(640, 480);
		frame.setBounds(0, 0, frame.getWidth(), frame.getHeight());		
		frame.setContentPane(panel);
		frame.setVisible(true);
		
		return frame;
	}

	private void setFramesSizes(Mat image) {
		frameCamera.setSize(image.width() + 20, image.height() + 60);
		
		if (calibrationMode){
			frameThreshold.setSize(image.width() + 20, image.height() + 60);
		}
		
	}

	private void repaintFrames() {
		frameCamera.repaint();
		
		if (calibrationMode){
			frameThreshold.repaint();
		}
		
	}

	private void setPanelsImages(Mat image, Mat thresholdedImage) {
		panelCamera.setImageWithMat(image);

		if (calibrationMode){
			panelThreshold.setImageWithMat(thresholdedImage);
		}
		
	}

	private void morphOps(Mat thresh) {	
		//create structuring element that will be used to "dilate" and "erode" image.
		//the element chosen here is a 3px by 3px rectangle
	
		Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
		//dilate with larger element so make sure object is nicely visible
		Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(8, 8));
	
		Imgproc.erode(thresh, thresh, erodeElement);
		Imgproc.erode(thresh, thresh, erodeElement);
	
		Imgproc.dilate(thresh, thresh, dilateElement);
		Imgproc.dilate(thresh, thresh, dilateElement);
	}

	private void trackFilteredObject(Ball theBall, Mat threshold, Mat cameraFeed) {
		List<Ball> balls = new ArrayList<Ball>();
	
		Mat temp = new Mat();
		threshold.copyTo(temp);
		
		// The two variables below are the return of "findContours" processing.
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Mat hierarchy = new Mat();
		
		// find contours of filtered image using openCV findContours function		
		Imgproc.findContours(temp, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);
		
		// use moments method to find our filtered object
		boolean objectFound = false;
	
		if (contours.size() > 0) {
			int numObjects = contours.size();
	
			//if number of objects greater than MAX_NUM_OBJECTS we have a noisy filter
			if (numObjects < MAX_NUM_OBJECTS) {
	
				for (int i=0; i< contours.size(); i++){
					Moments moment = Imgproc.moments(contours.get(i));
					double area = moment.get_m00();
	
					//if the area is less than 20 px by 20px then it is probably just noise
					//if the area is the same as the 3/2 of the image size, probably just a bad filter
					//we only want the object with the largest area so we safe a reference area each
					//iteration and compare it to the area in the next iteration.
					if (area > MIN_OBJECT_AREA) {
						Ball ball = new Ball();
						ball.setXPos((int)(moment.get_m10() / area));
						ball.setYPos((int)(moment.get_m01() / area));
						
						if (theBall != null){
							ball.setType(theBall.getType());
							ball.setColour(theBall.getColour());
						}
						
						balls.add(ball);
	
						objectFound = true;
					} else {
						objectFound = false;
					}
	
				}
	
				//let user know you found an object
				if (objectFound) {
					//draw object location on screen
					drawObject(balls, cameraFeed);
				}
	
			} else {
				Core.putText(cameraFeed, "TOO MUCH NOISE! ADJUST FILTER", new Point(0, 50), 1, 2, new Scalar(0, 0, 255), 2);
			}
	
		}
	
	}

	private void drawObject(List<Ball> theBalls, Mat frame) {

		for (int i = 0; i < theBalls.size(); i++) {
			Ball theBall = theBalls.get(i);

			Core.circle(frame, new Point(theBall.getXPos(), theBall.getYPos()), 10, new Scalar(0, 0, 255));
			Core.putText(frame, theBall.getXPos() + " , " + theBall.getYPos(), new Point(theBall.getXPos(), theBall.getYPos() + 20)
					, 1, 1, new Scalar(0, 255, 0));
			Core.putText(frame, theBall.getType().toString(), new Point(theBall.getXPos(),
					theBall.getYPos() - 30), 1, 2, theBall.getColour());
		}

	}

}
