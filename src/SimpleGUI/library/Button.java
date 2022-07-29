package SimpleGUI;

import processing.core.*;


public class Button extends Component<Button> {
	private String shape;     //circle, rectangle
	private int shapeMode;    //how processing should display the shape, CORNER, CORNERS, RADIUS, CENTER
	private int hoverColor; //color to turn the fill color when the mouse is hovered over the button
	private boolean asLabel;  //whether the button should just be a label or an actual button. Label disables hoverColor, clicks still register

	public Button(PApplet theParent, String x_, String y_, String w_, String h_) {
		super(theParent, x_, y_, w_, h_);
		this.TYPE = "Button";
		this.shape = "rectangle";
		this.shapeMode = PConstants.CORNER;
		this.asLabel = false;
	}
	
	public Button(PApplet theParent, float x_, float y_, float w_, float h_) {
		super(theParent, x_, y_, w_, h_);
		this.TYPE = "Button";
		this.shape = "rectangle";
		this.shapeMode = PConstants.CORNER;
		this.asLabel = false;
	}

	public void draw() {
		float x = this.getX();
		float y = this.getY();
		float w = this.getW();
		float h = this.getH();
		float textX = x; //default case, for RADIUS or CENTER
		float textY = y;

		switch(this.shapeMode) { //set where the text should be displayed based on the shape mode
		case PConstants.CORNER: //move from corner to the center
			textX += w / 2.0;
			textY += h / 2.0;
			break;
		case PConstants.CORNERS: //move from corner to the center, w and h are coordinates of other corner this time
			textX += (w - x) / 2.0;
			textY += (h - y) / 2.0;
			break;
		}

		//set up all of the parameters for shape drawing
		this.graphics.rectMode(this.shapeMode);
		this.graphics.ellipseMode(this.shapeMode);
		this.graphics.textAlign(PConstants.CENTER, PConstants.CENTER);
		this.graphics.textSize(this.textSize);
		this.graphics.fill(this.fillColor);
		this.graphics.strokeWeight(this.strokeWeight);
		this.graphics.stroke(this.strokeColor);
		if (!this.asLabel && this.mouseOver(false)) this.graphics.fill(hoverColor); //override fill color if mouse is over and this isn't a label
		if (this.strokeWeight == 0)                 this.graphics.noStroke();

		if (this.shape.equals("rectangle")) {
			this.graphics.rect(x, y, w, h, this.rounding);
		} else if (this.shape.equals("circle")) {
			this.graphics.ellipse(x, y, w, h);
		} else {
			return;
		}

		this.graphics.fill(this.textColor);
		this.graphics.text(this.label, textX, textY - this.myParent.textAscent() * this.textScalar);
	}


	public boolean mouseOver(boolean calledByScreen) { //get the top left and bottom right x / y coordinates, and make sure mouse x / y are between them
		float x = this.getX();
		float y = this.getY();
		float w = this.getW();
		float h = this.getH();
		if (this.shape.equals("rectangle")) {
			switch(this.shapeMode) {
			case PConstants.CORNER:
				return (this.myParent.mouseX >= x && this.myParent.mouseX <= x + w && this.myParent.mouseY >= y && this.myParent.mouseY <= y + h);
			case PConstants.CORNERS:
				return (this.myParent.mouseX >= x && this.myParent.mouseX <= w && this.myParent.mouseY >= y && this.myParent.mouseY <= h);
			case PConstants.CENTER:
				return (this.myParent.mouseX >= x - w / 2.0 && this.myParent.mouseX <= x + w / 2.0 && this.myParent.mouseY >= y - h / 2.0 && this.myParent.mouseY <= y + h / 2.0);
			case PConstants.RADIUS:
				return (this.myParent.mouseX >= x - w && this.myParent.mouseX <= x + w && this.myParent.mouseY >= y - h && this.myParent.mouseY <= y + h);
			default:
				return false;
			}
		} else if (this.shape.equals("circle")) { //get center point and radius, make sure distance from mouse position to center is less than radius
			switch(this.shapeMode) {
			case PConstants.CORNER:
				return (PApplet.dist(this.myParent.mouseX, this.myParent.mouseY, x + w / 2.0f, y + h / 2.0f) <= w / 2.0f);
			case PConstants.CORNERS:
				return (PApplet.dist(this.myParent.mouseX, this.myParent.mouseY, x + (w - x) / 2.0f, y + (h - y) / 2.0f) <= (w - x) / 2.0f);
			case PConstants.CENTER:
				return (PApplet.dist(this.myParent.mouseX, this.myParent.mouseY, x, y) <= w / 2.0f);
			case PConstants.RADIUS:
				return (PApplet.dist(this.myParent.mouseX, this.myParent.mouseY, x, y) <= w);
			default:
				return false;
			}
		}
		return false;
	}

	public String getShape() {
		return this.shape;
	}

	public Button setShape(String s_) {
		this.shape = s_;
		return this;
	}

	public Button setShapeMode(int s_) {
		this.shapeMode = s_;
		return this;
	}

	public Button setHoverColor(int h_) {
		hoverColor = h_;
		return this;
	}

	public Button setAsLabel(boolean a_) {
		this.asLabel = a_;
		return this;
	}
}