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


	public void draw(Screen screenParent) {
		float x = this.getX(screenParent);
		float y = this.getY(screenParent);
		float w = this.getW(screenParent);
		float h = this.getH(screenParent);
		this.graphics.textSize(this.textSize);
		this.graphics.fill(this.textColor);
		this.graphics.textAlign(PConstants.LEFT, PConstants.TOP);
		this.graphics.text(this.label, x, y, w, h);
	}

}