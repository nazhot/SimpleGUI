package SimpleGUI;

import processing.core.*;


public class TextBox extends Component<TextBox> {
	private int hoverColor;
	private int horizontalOrientation;
	private boolean isSelected;
	private boolean keyReleased;
	private String defaultValue;


	public TextBox(PApplet theParent, String x_, String y_, String w_, String h_) {
		super(theParent, x_, y_, w_, h_);
		this.TYPE = "TextBox";
		horizontalOrientation = PConstants.LEFT;
		this.isSelected = false;
		this.keyReleased = true;
		this.defaultValue = "";
	}
	
	public TextBox(PApplet theParent, float x_, float y_, float w_, float h_) {
		super(theParent, x_, y_, w_, h_);
		this.TYPE = "TextBox";
		horizontalOrientation = PConstants.LEFT;
		this.isSelected = false;
		this.keyReleased = true;
		this.defaultValue = "";
	}

	public void draw(Screen screenParent) {
		float x = this.getX(screenParent);
		float y = this.getY(screenParent);
		float w = this.getW(screenParent);
		float h = this.getH(screenParent);
		if (this.myParent.keyPressed && this.isSelected && this.keyReleased) {
			char key = this.myParent.key;
			this.keyReleased = false;
			if (key != PConstants.CODED) {
				if (key == PConstants.BACKSPACE) {
					if (this.value.length() > 0) {
						this.value = this.value.substring(0, this.value.length() - 1);
					}
				} else if (key == PConstants.ESC || key == PConstants.TAB || key == PConstants.ENTER || key == PConstants.RETURN ||key == PConstants.DELETE) {
				} else {
					this.value += key;
				}
			}
		}
		if (!this.myParent.keyPressed && !this.keyReleased) {
			this.keyReleased = true;
		}

		if (this.myParent.mousePressed && !this.mouseOver(screenParent, false)) {
			this.isSelected = false;
		}

		this.graphics.rectMode(PConstants.CORNER);
		if (this.isSelected || this.mouseOver(screenParent, false)) {
			this.graphics.fill(hoverColor);
		} else {
			this.graphics.fill(this.fillColor);
		}
		if (this.strokeWeight == 0) {
			this.graphics.noStroke();
		} else {
			this.graphics.strokeWeight(this.strokeWeight);
			this.graphics.stroke(this.strokeColor);
		}
		this.graphics.rect(x, y, w, h, this.rounding);
		this.graphics.fill(this.textColor);
		this.graphics.textSize(this.textSize);
		this.graphics.textAlign(horizontalOrientation, PConstants.CENTER);
		float textX = x + w * 0.02f;
		if (horizontalOrientation == PConstants.CENTER) {
			textX += w * 0.48f;
		} else if (horizontalOrientation == PConstants.RIGHT) {
			textX += w * 0.96f;
		}
		this.graphics.text(this.value, textX, y + h / 2.0f - this.graphics.textAscent() * this.textScalar);
	}


	public boolean mouseOver(Screen screenParent, boolean calledByScreen) {
		float x = this.getX(screenParent);
		float y = this.getY(screenParent);
		float w = this.getW(screenParent);
		float h = this.getH(screenParent);
		boolean mouseOver = (this.myParent.mouseX >= x && this.myParent.mouseX <= x + w && this.myParent.mouseY >= y && this.myParent.mouseY <= y + h);
		if (calledByScreen) {
			this.isSelected = mouseOver;
		}

		return mouseOver;
	}

	public TextBox setHoverColor(int h_) {
		hoverColor = h_;
		return this;
	}

	public TextBox setHorizontalOrientation(int h_) {
		horizontalOrientation = h_;
		return this;
	}

	public TextBox setDefaultValue(String d_){
		this.defaultValue = d_;
		return this;
	}

	public String getValue(){
		if (this.value.equals("")) return this.defaultValue;
		return this.value;
	}

	public void reset(){
		this.value = "";
	}

}
