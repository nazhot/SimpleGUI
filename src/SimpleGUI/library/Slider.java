package SimpleGUI;

import processing.core.*;


public class Slider extends Component<Slider> {
	private float min;
	private float max;
	private float slidePosition;
	private int lineColor;
	private int tickColor;
	private int slideFillColor;
	private int slideStrokeColor;
	private float lineWeight;
	private float tickWeight;
	private float slideStrokeWeight;
	private boolean slideMoving;
	private String titlePosition; //"TOP", "LEFT", "RIGHT", "BOTTOM"
	private boolean isDefinedJumps;
	private float jumpAmount;
	private int numJumps;


	public Slider(PApplet theParent, String x_, String y_, String w_, String h_) {
		super(theParent, x_, y_, w_, h_);
		this.TYPE = "Slider";
		this.min = 0;
		this.max = 1;
		this.lineWeight = 4;
		this.tickWeight = 2;
		this.slideStrokeWeight = 3;
		this.slideMoving = false;
		this.titlePosition = "LEFT";
		this.isDefinedJumps = false;
		this.jumpAmount = 0;
		this.numJumps = 0;
		this.value = "0";
	}
	
	public boolean initialize(Screen screenParent) {
		this.slidePosition = this.getX(screenParent);
		return true;
		
	}

	public void draw(Screen screenParent) {
		float x = this.getX(screenParent);
		float y = this.getY(screenParent);
		float w = this.getW(screenParent);
		float h = this.getH(screenParent);
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

	public boolean mouseOver(Screen screenParent, boolean calledByScreen) {
		float x = this.getX(screenParent);
		float y = this.getY(screenParent);
		float w = this.getW(screenParent);
		float h = this.getH(screenParent);
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

	public void reset(Screen screenParent){
		this.slidePosition = this.getX(screenParent);
	}

}
