package SimpleGUI;

import processing.core.*;

public class Component <T extends Component<T>> {
	public PApplet myParent;
	public PGraphics graphics;
	public String TYPE;
	public float  x;
	public float  y;
	public float  w;
	public float  h;
	public String name;
	public String label;
	public String value;
	public int    drawOrder;
	public int  fillColor;
	public float  fillAlphaValue;
	public int  strokeColor;
	public float  strokeAlphaValue;
	public int  textColor;
	public float  textAlphaValue;
	public int    strokeWeight;
	public float  rounding;
	public float  textSize;
	public float textScalar;


	public Component(PApplet theParent, float x_, float y_, float w_, float h_) {
		this.myParent = theParent;
		this.graphics = this.myParent.g;
		this.x = x_;
		this.y = y_;
		this.w = w_;
		this.h = h_;
		this.name = "";
		this.label = "";
		this.value = "";
		this.drawOrder = 0;
		this.strokeWeight = 1;
		this.rounding = 0;
		this.textSize = 12;
		this.textScalar = 0;
	}

	public void draw() {
	}

	public boolean mouseOver(boolean calledByScreen) {
		return false;
	}

	public boolean clicked() {
		return false;
	}
	public String getType() {
		return this.TYPE;
	}
	public float getX() {
		return this.x;
	}
	public float getY() {
		return this.y;
	}
	public float getW() {
		return this.w;
	}
	public float getH() {
		return this.h;
	}
	public String getName() {
		return this.name;
	}
	public String getLabel() {
		return this.label;
	}
	public String getValue() {
		return this.value;
	}
	public int getDrawOrder() {
		return this.drawOrder;
	}



	public void reset() {
	}

	public T setMultipliers(float colMultiplier, float rowMultiplier) {
		this.x *= colMultiplier;
		this.w *= colMultiplier;
		this.y *= rowMultiplier;
		this.h *= rowMultiplier;
		this.textSize *= colMultiplier;
		return (T) this;
	}

	public T setX(float x_) {
		this.x = x_;
		return (T) this;
	}

	public T setY(float y_) {
		this.y = y_;
		return (T) this;
	}

	public T setW(float w_) {
		this.w = w_;
		return (T) this;
	}

	public T setH(float h_) {
		this.h = h_;
		return (T) this;
	}

	public T setFillColor(int f_) {
		this.fillColor = f_;
		return (T) this;
	}

	public T setTextColor(int t_) {
		this.textColor = t_;
		return (T) this;
	}

	public T setStrokeColor(int s_) {
		this.strokeColor = s_;
		return (T) this;
	}

	public T setStrokeWeight(int s_) {
		this.strokeWeight = s_;
		return (T) this;
	}

	public T setName(String n_) {
		this.name = n_;
		return (T) this;
	}

	public T setLabel(String l_) {
		this.label = l_;
		return (T) this;
	}

	public T setValue(String v_){
		this.value = v_;
		return (T) this;
	}

	public T setDrawOrder(int d_) {
		this.drawOrder = d_;
		return (T) this;
	}

	public T setRounding(float r_) {
		this.rounding = r_;
		return (T) this;
	}

	public T setTextSize(float t_) {
		this.textSize = t_;
		return (T) this;
	}
}
