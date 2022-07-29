package SimpleGUI;

import processing.core.*;


public class Image extends Component<Image> {

	private String imageFileName;

	public Image(PApplet myParent, String x_, String y_, String w_, String h_) {
		super(myParent, x_, y_, w_, h_);
		this.TYPE = "Image";
		this.imageFileName = "";
	}


	public void draw(Screen screenParent) {
		float x = this.getX(screenParent);
		float y = this.getY(screenParent);
		float w = this.getW(screenParent);
		float h = this.getH(screenParent);
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