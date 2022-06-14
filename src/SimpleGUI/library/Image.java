package SimpleGUI;

import processing.core.*;


public class Image extends Component<Image> {

	  private String imageFileName;

	  public Image(PApplet myParent, float x_, float y_, float w_, float h_) {
	    super(myParent, x_, y_, w_, h_);
	    this.TYPE = "Image";
	    this.imageFileName = "";
	  }


	  public void draw() {
	  PImage image = this.myParent.loadImage(this.imageFileName);
	    if (image != null) {
	      this.graphics.image(image, this.x, this.y, this.w, this.h);
	    }
	  }


	  public Image setImageFileName(String i_) {
	    this.imageFileName = i_;
	    return this;
	  }
	}