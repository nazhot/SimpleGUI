package SimpleGUI;

import processing.core.*;

public class Component <T extends Component<T>> {
	public PApplet myParent;
	public PGraphics graphics;
	public String TYPE;
	public String initX;
	public String initY;
	public String initW;
	public String initH;
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


	public Component(PApplet theParent, String x_, String y_, String w_, String h_) {
		this.myParent = theParent;
		this.graphics = this.myParent.g;
		this.initX = x_;
		this.initY = y_;
		this.initW = w_;
		this.initH = h_;
		this.name = "";
		this.label = "";
		this.value = "";
		this.drawOrder = 0;
		this.strokeWeight = 1;
		this.rounding = 0;
		this.textSize = 12;
		this.textScalar = 0;
	}
	
	public Component(PApplet theParent, float x_, float y_, float w_, float h_) {
		this.myParent = theParent;
		this.graphics = this.myParent.g;
		this.initX = String.valueOf(x_) + "a";
		this.initY = String.valueOf(y_) + "a";
		this.initW = String.valueOf(w_) + "a";
		this.initH = String.valueOf(h_) + "a";
		this.name = "";
		this.label = "";
		this.value = "";
		this.drawOrder = 0;
		this.strokeWeight = 1;
		this.rounding = 0;
		this.textSize = 12;
		this.textScalar = 0;
	}

	public boolean initialize(Screen screenParent) {
		return false;
	}

	public void draw(Screen screenParent) {
	}

	public boolean mouseOver(Screen screenParent, boolean calledByScreen) {
		return false;
	}

	public boolean clicked() {
		return false;
	}
	public String getType() {
		return this.TYPE;
	}
	public float convertDimension(String dimension, Screen screenParent) {
		int numberSubtract = 1;
		String modifier = dimension.substring(dimension.length() - 1);
		try {
			Integer.parseInt(modifier);
			modifier = "a";
			numberSubtract = 0;
		} catch(NumberFormatException e) {
		}
		float number = Float.parseFloat(dimension.substring(0, dimension.length() - numberSubtract));

		switch(modifier) {
		case "a":
			return number;
		case "w":
			return number * 1.0f * screenParent.getW();
		case "h":
			return number * 1.0f * screenParent.getH();
		}
		return 0;
	}
	public float getX(Screen screenParent) {
		return screenParent.getX() + this.convertDimension(this.initX, screenParent);
	}
	public float getY(Screen screenParent) {
		return screenParent.getY() + this.convertDimension(this.initY, screenParent);
	}
	public float getW(Screen screenParent) {
		return this.convertDimension(this.initW, screenParent);
	}
	public float getH(Screen screenParent) {
		return this.convertDimension(this.initH, screenParent);
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



	public void reset(Screen screenParent) {
	}


	public T setX(String x_) {
		this.initX = x_;
		return (T) this;
	}

	public T setY(String y_) {
		this.initY = y_;
		return (T) this;
	}

	public T setW(String w_) {
		this.initW = w_;
		return (T) this;
	}

	public T setH(String h_) {
		this.initH = h_;
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

	public T setTextScalar(float t_) {
		this.textScalar = t_;
		return (T) this;
	}


}
