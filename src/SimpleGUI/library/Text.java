package SimpleGUI;

import processing.core.*;
import processing.data.JSONObject;


public class Text extends Component<Text> {

	public Text(PApplet myParent, String x_, String y_, String w_, String h_) {
		super(myParent, x_, y_, w_, h_);
	}

	public Text(PApplet myParent, float x_, float y_, float w_, float h_) {
		super(myParent, x_, y_, w_, h_);
	}

	public Text(PApplet myParent, JSONObject settings) {
		super(myParent, settings);
	}

	public void draw() {
		float x = this.getX();
		float y = this.getY();
		float w = this.getW();
		float h = this.getH();
		this.graphics.textSize(this.textSize);
		this.graphics.fill(this.textColor);
		this.graphics.rectMode(PConstants.CORNER);
		this.graphics.textAlign(PConstants.LEFT, PConstants.TOP);
		if (this.initH == null || this.initW == null) {
			this.graphics.text(this.label, x, y);
		} else {
			this.graphics.text(this.label, x, y, w, h);
		}
	}

	public float getW() {
		if (this.initW == null) {
			this.graphics.textSize(this.textSize);
			return this.graphics.textWidth(this.label);
		}
		return this.convertDimension(this.initW);
	}

	public float getH() {
		if (this.initH == null) {
			this.graphics.textSize(this.textSize);
			return this.graphics.textAscent() - this.graphics.textDescent();
		}
		return this.convertDimension(this.initH);
	}


}