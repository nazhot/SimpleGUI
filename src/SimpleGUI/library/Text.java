package SimpleGUI;

import processing.core.*;


public class Text extends Component<Text> {

	public Text(PApplet myParent, float x_, float y_, float w_, float h_) {
	    super(myParent, x_, y_, w_, h_);
	    this.TYPE = "Text";
	  }


	  public void draw() {
	    this.graphics.textSize(this.textSize);
	    this.graphics.fill(this.textColor);
	    this.graphics.textAlign(PConstants.LEFT, PConstants.TOP);
	    this.graphics.text(this.label, this.x, this.y, this.w, this.h);
	  }

	}