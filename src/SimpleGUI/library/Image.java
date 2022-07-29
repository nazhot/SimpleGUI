package SimpleGUI;

import processing.core.*;


public class Image extends Component<Image> {

	private String imageFileName;

	public Image(PApplet myParent, String x_, String y_, String w_, String h_) {
		super(myParent, x_, y_, w_, h_);
		this.TYPE = "Image";
		this.imageFileName = "";
	}
	
	public Image(PApplet myParent, float x_, float y_, float w_, float h_) {
		super(myParent, x_, y_, w_, h_);
		this.TYPE = "Image";
		this.imageFileName = "";
	}


	public void draw() {
		float x = this.getX();
		float y = this.getY();
		float w = this.getW();
		float h = this.getH();
		PImage image = this.myParent.loadImage(this.imageFileName);
		if (image != null) {
			this.graphics.image(image, x, y, w, h);
		}
	}


	public Image setImageFileName(String i_) {
		this.imageFileName = i_;
		return this;
	}
}