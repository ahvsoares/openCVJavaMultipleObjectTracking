package br.cefetmg.lsi.opencv.multipleObjectTracking.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.opencv.core.Mat;

import br.cefetmg.lsi.opencv.multipleObjectTracking.processing.OpenCV2JavaImageManipulations;

public class Panel extends JPanel {
	private static final long serialVersionUID = 4913353253704963163L;

	private BufferedImage image;

	public Panel() {
		super();
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage newImage) {
		image = newImage;
	}
	
	public void setImageWithMat(Mat newimage) {
		image = OpenCV2JavaImageManipulations.matToBufferedImage(newimage);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
        if (image != null){
        	g.drawImage(image, 10, 10, image.getWidth(), image.getHeight(), this);
        }
        
	}
}
