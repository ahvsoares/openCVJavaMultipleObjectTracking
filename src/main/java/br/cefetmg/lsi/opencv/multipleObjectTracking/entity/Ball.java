package br.cefetmg.lsi.opencv.multipleObjectTracking.entity;

import org.opencv.core.Scalar;

import br.cefetmg.lsi.opencv.multipleObjectTracking.utils.PropertiesLoaderImpl;

public class Ball {
	private int xPos;
	private int yPos;
	private Colours type;
	private Scalar hsvMin;
	private Scalar hsvMax;
	private Scalar colour;
	static public enum Colours {
		NONE(""), RED("Red"), GREEN("Green"), BLUE("Blue");
		
		private final String name;
		
		Colours(String colourName){
			name = colourName;
		}
		
		public String toString(){
			return name;
		}
	}; 
	
	public Ball(){
		xPos = 0;
		yPos = 0;
		type = Colours.NONE;
		int hMin = new Integer(PropertiesLoaderImpl.getValor("multipleObjectTracking.color.red.hMin"));
		int sMin = new Integer(PropertiesLoaderImpl.getValor("multipleObjectTracking.color.red.sMin"));
		int vMin = new Integer(PropertiesLoaderImpl.getValor("multipleObjectTracking.color.red.vMin"));
		int hMax = new Integer(PropertiesLoaderImpl.getValor("multipleObjectTracking.color.red.hMax"));
		int sMax = new Integer(PropertiesLoaderImpl.getValor("multipleObjectTracking.color.red.sMax"));
		int vMax = new Integer(PropertiesLoaderImpl.getValor("multipleObjectTracking.color.red.vMax"));
		setHsvMin(new Scalar(hMin, sMin, vMin));
		setHsvMax(new Scalar(hMax, sMax, vMax));
		setColour(new Scalar(0, 0, 255));
	}

	public Ball(Colours name){
		setType(name);
		int hMin = 0;
		int sMin = 0;
		int vMin = 0;
		int hMax = 0;
		int sMax = 0;
		int vMax = 0;

		switch (name) {
			case RED:
				hMin = new Integer(PropertiesLoaderImpl.getValor("multipleObjectTracking.color.red.hMin"));
				sMin = new Integer(PropertiesLoaderImpl.getValor("multipleObjectTracking.color.red.sMin"));
				vMin = new Integer(PropertiesLoaderImpl.getValor("multipleObjectTracking.color.red.vMin"));
				hMax = new Integer(PropertiesLoaderImpl.getValor("multipleObjectTracking.color.red.hMax"));
				sMax = new Integer(PropertiesLoaderImpl.getValor("multipleObjectTracking.color.red.sMax"));
				vMax = new Integer(PropertiesLoaderImpl.getValor("multipleObjectTracking.color.red.vMax"));
				setColour(new Scalar(0, 0, 255));
				break;
				
			case GREEN:
				hMin = new Integer(PropertiesLoaderImpl.getValor("multipleObjectTracking.color.green.hMin"));
				sMin = new Integer(PropertiesLoaderImpl.getValor("multipleObjectTracking.color.green.sMin"));
				vMin = new Integer(PropertiesLoaderImpl.getValor("multipleObjectTracking.color.green.vMin"));
				hMax = new Integer(PropertiesLoaderImpl.getValor("multipleObjectTracking.color.green.hMax"));
				sMax = new Integer(PropertiesLoaderImpl.getValor("multipleObjectTracking.color.green.sMax"));
				vMax = new Integer(PropertiesLoaderImpl.getValor("multipleObjectTracking.color.green.vMax"));
				setColour(new Scalar(0, 255, 0));
				break;
				
			case BLUE:
				hMin = new Integer(PropertiesLoaderImpl.getValor("multipleObjectTracking.color.blue.hMin"));
				sMin = new Integer(PropertiesLoaderImpl.getValor("multipleObjectTracking.color.blue.sMin"));
				vMin = new Integer(PropertiesLoaderImpl.getValor("multipleObjectTracking.color.blue.vMin"));
				hMax = new Integer(PropertiesLoaderImpl.getValor("multipleObjectTracking.color.blue.hMax"));
				sMax = new Integer(PropertiesLoaderImpl.getValor("multipleObjectTracking.color.blue.sMax"));
				vMax = new Integer(PropertiesLoaderImpl.getValor("multipleObjectTracking.color.blue.vMax"));
				setColour(new Scalar(255, 0, 0));
				break;
	
			default:
				xPos = 0;
				yPos = 0;
				hsvMax = new Scalar(0, 0, 0);
				hsvMin = new Scalar(0, 0, 0);
				colour = new Scalar(0, 0, 0);
				break;
		}

		setHsvMin(new Scalar(hMin, sMin, vMin));
		setHsvMax(new Scalar(hMax, sMax, vMax));
	}

	public int getXPos() {
		return xPos;
	}

	public void setXPos(int xPos) {
		this.xPos = xPos;
	}

	public int getYPos() {
		return yPos;
	}

	public void setYPos(int yPos) {
		this.yPos = yPos;
	}

	public Colours getType() {
		return type;
	}

	public void setType(Colours type) {
		this.type = type;
	}

	public Scalar getHsvMin() {
		return hsvMin;
	}

	public void setHsvMin(Scalar hsvMin) {
		this.hsvMin = hsvMin;
	}

	public Scalar getHsvMax() {
		return hsvMax;
	}

	public void setHsvMax(Scalar hsvMax) {
		this.hsvMax = hsvMax;
	}

	public Scalar getColour() {
		return colour;
	}

	public void setColour(Scalar colour) {
		this.colour = colour;
	}
	
}
