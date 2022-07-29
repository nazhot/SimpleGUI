package SimpleGUI;

import processing.core.*;


public class Text extends Component<Text> {

	public Text(PApplet myParent, String x_, String y_, String w_, String h_) {
		super(myParent, x_, y_, w_, h_);
		this.TYPE = "Text";
	}
	
	public Text(PApplet myParent, float x_, float y_, float w_, float h_) {
		super(myParent, x_, y_, w_, h_);
		this.TYPE = "Text";
	}


	public void draw() {
		float x = this.getX();
		float y = this.getY();
		float w = this.getW();
		float h = this.getH();
		this.graphics.textSize(this.textSize);
		this.graphics.fill(this.textColor);
		this.graphics.textAlign(PConstants.LEFT, PConstants.TOP);
		this.graphics.text(this.label, x, y, w, h);
	}

}