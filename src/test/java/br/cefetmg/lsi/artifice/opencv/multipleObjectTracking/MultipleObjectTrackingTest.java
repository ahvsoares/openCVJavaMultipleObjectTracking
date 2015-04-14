package br.cefetmg.lsi.artifice.opencv.multipleObjectTracking;

import br.cefetmg.lsi.opencv.multipleObjectTracking.processing.MultipleObjectTracking;


public class MultipleObjectTrackingTest {

	public static void main(String[] args) {
		MultipleObjectTracking tracker = new MultipleObjectTracking();
		
		try {
			tracker.startTracking();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}