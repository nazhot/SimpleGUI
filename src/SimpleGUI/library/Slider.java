package SimpleGUI;

import processing.core.*;
import processing.data.JSONObject;


public class Slider extends Component<Slider> {
	private float min = 0;
	private float max = 1;
	private float slidePosition;
	private int lineColor;
	private int tickColor;
	private int slideFillColor;
	private int slideStrokeColor;
	private float lineWeight = 4;
	private float tickWeight = 2;
	private float slideStrokeWeight = 3;
	private boolean slideMoving = false;
	private String titlePosition = "LEFT"; //"TOP", "LEFT", "RIGHT", "BOTTOM"
	private boolean isDefinedJumps = false;
	private float jumpAmount = 0;
	private int numJumps = 0;


	public Slider(PApplet theParent, String x_, String y_, String w_, String h_) {
		super(theParent, x_, y_, w_, h_);
		this.value = "0";
	}
	
	public Slider(PApplet theParent, float x_, float y_, float w_, float h_) {
		super(theParent, x_, y_, w_, h_);
		this.value = "0";
	}
	
	public Slider(PApplet theParent, JSONObject settings) {
		super(theParent, settings);
		this.value = "0";
	}
	
	public boolean initialize() {
		this.slidePosition = this.getX();
		return true;
		
	}

	public void draw() {
		float x = this.getX();
		float y = this.getY();
		float w = this.getW();
		float h = this.getH();
		if (!this.myParent.mousePressed) {
			this.slideMoving = false;
		}
		this.graphics.ellipseMode(PConstants.RADIUS);
		this.graphics.stroke(this.lineColor);
		this.graphics.strokeWeight(this.lineWeight);
		this.graphics.line(x, y, x + w, y);
		this.graphics.stroke(this.slideStrokeColor);
		this.graphics.strokeWeight(this.slideStrokeWeight);
		this.graphics.fill(this.slideFillColor);
		this.graphics.ellipse(this.slidePosition, y, h, h);
		if (this.slideMoving) {
			this.slidePosition = PApplet.constrain(this.myParent.mouseX, x, x + w);
		}
		this.value = PApplet.str(PApplet.map(this.slidePosition, x, x + w, this.min, this.max));
		this.graphics.fill(this.textColor);
		this.graphics.textSize(this.textSize);
		this.graphics.textAlign(PConstants.CENTER, PConstants.BOTTOM);
		this.graphics.text(PApplet.nf(Float.parseFloat(this.value), 0, 2), x + w / 2.0f, y - h);
		switch(this.titlePosition) {
		case "LEFT":
			this.graphics.textAlign(PConstants.RIGHT, PConstants.CENTER);
			this.graphics.text(this.label, x - h, y - this.graphics.textAscent() * this.textScalar);
			break;
		case "RIGHT":
			this.graphics.textAlign(PConstants.LEFT, PConstants.CENTER);
			this.graphics.text(this.label, x + w + h, y - this.graphics.textAscent() * this.textScalar);
			break;
		case "TOP":
			this.graphics.textAlign(PConstants.CENTER, PConstants.BOTTOM);
			this.graphics.text(this.label, x + w / 2.0f, y - h - this.graphics.textAscent() - this.graphics.textDescent());
			break;
		case "BOTTOM":
			this.graphics.textAlign(PConstants.CENTER, PConstants.TOP);
			this.graphics.text(this.label, x + w / 2.0f, y + h);
			break;
		default:
			this.graphics.textAlign(PConstants.RIGHT, PConstants.CENTER);
			this.graphics.text(this.label, x - h, y);
			break;
		}
	}

	public boolean mouseOver(boolean calledByScreen) {
		float x = this.getX();
		float y = this.getY();
		float w = this.getW();
		float h = this.getH();
		boolean mouseOver = (PApplet.dist(this.myParent.mouseX, this.myParent.mouseY, this.slidePosition, y) <= h);
		if (calledByScreen) {
			if (mouseOver) {
				this.slideMoving = true;
			}
		}
		return (PApplet.dist(this.myParent.mouseX, this.myParent.mouseY, this.slidePosition, y) <= h);
	}

	public Slider setMin(float m_) {
		this.min = m_;
		return this;
	}

	public Slider setMax(float m_) {
		this.max = m_;
		return this;
	}

	public Slider setTitlePosition(String t_) {
		this.titlePosition = t_;
		return this;
	}

	public Slider setLineColor(int l_) {
		this.lineColor = l_;
		return this;
	}

	public Slider setTextColor(int t_){
		this.textColor = t_;
		return this;
	}

	public Slider setSlideFillColor(int s_) {
		this.slideFillColor = s_;
		return this;
	}

	public Slider setSlideStrokeColor(int s_) {
		this.slideStrokeColor = s_;
		return this;
	}

	public Slider setSlideStrokeWeight(float s_) {
		this.slideStrokeWeight = s_;
		return this;
	}

	public void reset(){
		this.slidePosition = this.getX();
	}

}
