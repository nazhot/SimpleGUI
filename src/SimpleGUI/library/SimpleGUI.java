package SimpleGUI;

import processing.core.*;


public class SimpleGUI {


	SimpleGUI(){

	}


	static String convertPConstant(int constant) {
		switch(constant) {
		case PConstants.CORNER:
			return "CORNER";
		case PConstants.CENTER:
			return "CENTER";
		case PConstants.RADIUS:
			return "RADIUS";
		case PConstants.CORNERS:
			return "CORNERS";
		case PConstants.LEFT:
			return "LEFT";
		case PConstants.RIGHT:
			return "RIGHT";
		case PConstants.TOP:
			return "TOP";
		case PConstants.BOTTOM:
			return "BOTTOM";
		default:
			return "";
		}
	}
	
	static int convertPConstant(String constant) {
		switch(constant) {
		case "CORNER":
			return PConstants.CORNER;
		case "CENTER":
			return PConstants.CENTER;
		case "RADIUS":
			return PConstants.RADIUS;
		case "CORNERS":
			return PConstants.CORNERS;
		case "LEFT":
			return PConstants.LEFT;
		case "RIGHT":
			return PConstants.RIGHT;
		case "TOP":
			return PConstants.TOP;
		case "BOTTOM":
			return PConstants.BOTTOM;
		default:
			return -1;
		}
	}




}
