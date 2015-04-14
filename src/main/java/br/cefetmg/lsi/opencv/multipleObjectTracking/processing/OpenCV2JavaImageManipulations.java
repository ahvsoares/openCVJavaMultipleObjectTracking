package br.cefetmg.lsi.opencv.multipleObjectTracking.processing;

import java.awt.image.BufferedImage;

import org.opencv.core.Mat;

public class OpenCV2JavaImageManipulations {

	/**
	 * Converts an OpenCV "Mat" to a Java "BufferedImage".
	 * Reference: http://cell0907.blogspot.com.es/2013/07/detecting-faces-in-your-webcam-stream.html
	 * 
	 * @param matrix OpenCV Mat dos tipos CV_8UC3 ou CV_8UC1
	 * 
	 * @return BufferedImage dos tipos TYPE_3BYTE_BGR ou TYPE_BYTE_GRAY
	 */
	public static BufferedImage matToBufferedImage(Mat matrix) {
		int cols = matrix.cols();
		int rows = matrix.rows();
		int elemSize = (int) matrix.elemSize();
		byte[] data = new byte[cols * rows * elemSize];
		int type;
		
		matrix.get(0, 0, data);
		
		switch (matrix.channels()) {
			case 1:
				type = BufferedImage.TYPE_BYTE_GRAY;
				break;
				
			case 3:
				type = BufferedImage.TYPE_3BYTE_BGR;
				
				// bgr to rgb
				byte b;
				
				for (int i = 0; i < data.length; i = i + 3) {
					b = data[i];
					data[i] = data[i + 2];
					data[i + 2] = b;
				}
				
				break;
				
			default:
				return null;
		}
		
		BufferedImage image = new BufferedImage(cols, rows, type);
		image.getRaster().setDataElements(0, 0, cols, rows, data);
		
		return image;
	}

}
