package SimpleGUI;

import processing.core.*;
import processing.data.JSONObject;
import java.lang.reflect.*;

public class Component <T extends Component<T>> {
	public PApplet myParent;
	public PGraphics graphics;
	public Screen screenParent;
	public final String TYPE = this.getClass().getSimpleName();
	public String initX;
	public String initY;
	public String initW;
	public String initH;
	public String name = "";
	public String label = "";
	public String value = "";
	public int    drawOrder = 0;
	public int  fillColor;
	public float  fillAlphaValue;
	public int  strokeColor;
	public float  strokeAlphaValue;
	public int  textColor;
	public float  textAlphaValue;
	public int    strokeWeight = 1;
	public float  rounding = 0;
	public int  textSize = 12;
	public float textScalar = 0;
	public JSONObject payload;
	public String alignRight;
	public String alignLeft;
	public String alignTop;
	public String alignBottom;
	public String sameX;
	public String sameY;
	public String alignHorizontalGap = "0a";
	public String alignVerticalGap = "0a";
	public final float NULLERROR = -123.4f;
	public final String dimensionRegex = "^-?\\d+\\.?\\d*[awh]$";
	public float minValue = this.NULLERROR;
	public float maxValue = this.NULLERROR;
	public float minLabel = this.NULLERROR;
	public float maxLabel = this.NULLERROR;


	public Component(PApplet theParent, String x_, String y_, String w_, String h_) {
		this.initX = this.validateDimension(x_);
		this.initY = this.validateDimension(y_);
		this.initW = this.validateDimension(w_);
		this.initH = this.validateDimension(h_);
		this.generalInitialize(theParent);

	}

	public Component(PApplet theParent, float x_, float y_, float w_, float h_) {
		this.initX = String.valueOf(x_) + "a";
		this.initY = String.valueOf(y_) + "a";
		this.initW = String.valueOf(w_) + "a";
		this.initH = String.valueOf(h_) + "a";
		this.generalInitialize(theParent);
	}

	public Component(PApplet theParent, JSONObject settings) {
		this.generalInitialize(theParent);
		this.setSettingsByJSON(settings);
	}

	public void generalInitialize(PApplet theParent) {
		this.myParent = theParent;
		this.graphics = this.myParent.g;
		this.initializePayload();

	}
	public boolean initialize() {
		return false;
	}

	public void initializePayload() {
		this.payload = new JSONObject();
		this.payload.put("Name", "")
		.put("Screen", "")
		.put("Type", this.TYPE)
		.put("Target", "")
		.put("Command", "")
		.put("Command Parameters", "")
		.put("Custom Function", "");
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
	public float convertDimension(String dimension) {
		//due to this.validateDimension being called anytime a dimensional value is set,
		//  this function should not have to do any checking, it should be in the correct format
		if (dimension.length() == 0) {
			PApplet.println("YUH YOH " + this.TYPE);
		}
		String modifier = dimension.substring(dimension.length() - 1);
		float number = Float.parseFloat(dimension.substring(0, dimension.length() - 1));

		switch(modifier) {
		case "a":
			return number;
		case "w":
			return number * 1.0f * this.screenParent.getW();
		case "h":
			return number * 1.0f * this.screenParent.getH();
		}
		return 0;
	}
	public float getX() {
		if (this.sameX != null) {
			for (Component component : this.screenParent.getComponents()) {
				if (component.getName().equals(this.sameX)) {
					return component.getX() + this.convertDimension(this.alignHorizontalGap);
				}
			}
			this.sameX = null;
		}
		if (this.alignLeft == null && this.alignRight == null) {
			return this.screenParent.getX() + this.convertDimension(this.initX);
		}
		for (Component component : this.screenParent.getComponents()) {
			if (component.getName().equals(this.alignLeft)) {
				this.alignRight = null;
				return component.getX() - this.getW() - this.convertDimension(this.alignHorizontalGap);
			}
			if (component.getName().equals(this.alignRight)) {
				this.alignLeft = null;
				return component.getX() + component.getW() + this.convertDimension(this.alignHorizontalGap);
			}
		}
		this.alignLeft = null;
		this.alignRight = null;
		return this.screenParent.getX() + this.convertDimension(this.initX);
	}


	public float getY() {
		if (this.sameY != null) {
			for (Component component : this.screenParent.getComponents()) {
				if (component.getName().equals(this.sameY)) {
					return component.getY() + this.convertDimension(this.alignVerticalGap);
				}
			}
			this.sameY = null;
		}
		if (this.alignTop == null && this.alignBottom == null) {
			return this.screenParent.getY() + this.convertDimension(this.initY);
		}
		for (Component component : this.screenParent.getComponents()) {
			if (component.getName().equals(this.alignTop)) {
				this.alignBottom = null;
				return component.getY() - this.getH() - this.convertDimension(this.alignVerticalGap);
			}
			if (component.getName().equals(this.alignBottom)) {
				this.alignTop = null;
				return component.getY() + component.getH() + this.convertDimension(this.alignVerticalGap);
			}
		}
		this.alignTop = null;
		this.alignBottom = null;
		return this.screenParent.getY() + this.convertDimension(this.initY);
	}
	public float getW() {
		return this.convertDimension(this.initW);
	}
	public float getH() {
		return this.convertDimension(this.initH);
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
	public JSONObject getPayload() {
		return this.payload;
	}


	public void reset() {
	}


	public T setX(String x_) {
		this.initX =  this.validateDimension(x_);
		return (T) this;
	}

	public T setY(String y_) {
		this.initY =  this.validateDimension(y_);
		return (T) this;
	}

	public T setW(String w_) {
		this.initW = this.validateDimension(w_);
		return (T) this;
	}

	public T setH(String h_) {
		this.initH =  this.validateDimension(h_);
		return (T) this;
	}

	public T setFillColor(Integer f_) {
		this.fillColor = f_;
		return (T) this;
	}

	public T setTextColor(Integer t_) {
		this.textColor = t_;
		return (T) this;
	}

	public T setStrokeColor(Integer s_) {
		this.strokeColor = s_;
		return (T) this;
	}

	public T setStrokeWeight(Integer s_) {
		this.strokeWeight = s_;
		return (T) this;
	}

	public T setName(String n_) {
		this.name = n_;
		if (this.payload.hasKey("Name")) {
			this.payload.setString("Name", n_);
		}
		return (T) this;
	}

	public T setLabel(String l_) {
		String labelType = this.floatOrInt(this.label);
		if (labelType.equals("NEITHER")) {
			this.label = l_;
			return (T) this;
		}
		if (labelType.equals("INTEGER")) {
			int intLabel = Integer.parseInt(l_);
			if (this.minLabel != this.NULLERROR) {
				intLabel = Math.max(intLabel, (int) this.minLabel);
			}
			if (this.maxLabel != this.NULLERROR) {
				intLabel = Math.min(intLabel, (int) this.maxLabel);
			}
			this.label = String.valueOf(intLabel);
			return (T) this;
		}
		if (labelType.equals("FLOAT")) {
			float floatLabel = Float.parseFloat(l_);
			if (this.minLabel != this.NULLERROR) {
				floatLabel = Math.max(floatLabel, this.minLabel);
			}
			if (this.maxLabel != this.NULLERROR) {
				floatLabel = Math.min(floatLabel, this.maxLabel);
			}
			this.label = String.valueOf(floatLabel);
			return (T) this;
		}
		return (T) this;
	}

	public T setValue(String v_){
		String valueType = this.floatOrInt(this.value);
		if (valueType.equals("NEITHER")) {
			this.value = v_;
			return (T) this;
		}
		if (valueType.equals("INTEGER")) {
			int intValue = Integer.parseInt(v_);
			if (this.minValue != this.NULLERROR) {
				intValue = Math.max(intValue, (int) this.minValue);
			}
			if (this.maxValue != this.NULLERROR) {
				intValue = Math.min(intValue, (int) this.maxValue);
			}
			this.value = String.valueOf(intValue);
			return (T) this;
		}
		if (valueType.equals("FLOAT")) {
			float floatValue = Float.parseFloat(v_);
			if (this.minValue != this.NULLERROR) {
				floatValue = Math.max(floatValue, this.minValue);
			}
			if (this.maxValue != this.NULLERROR) {
				floatValue = Math.min(floatValue, this.maxValue);
			}
			this.value = String.valueOf(floatValue);
			return (T) this;
		}
		return (T) this;
	}

	public T setDrawOrder(Integer d_) {
		this.drawOrder = d_;
		return (T) this;
	}

	public T setRounding(Float r_) {
		this.rounding = r_;
		return (T) this;
	}

	public T setTextScalar(Double ts) {
		this.textScalar = ts.floatValue();
		return (T) this;
	}

	public T setTextSize(Integer t_) {
		this.textSize = t_;
		return (T) this;
	}

	public T setTextScalar(Float t_) {
		this.textScalar = t_;
		return (T) this;
	}

	public T setScreen(Screen s_) {
		this.screenParent = s_;
		return (T) this;
	}

	public T setAlignLeft(String component) {
		this.alignLeft = component;
		return (T) this;
	}

	public T setAlignRight(String component) {
		this.alignRight = component;
		return (T) this;
	}

	public T setAlignTop(String component) {
		this.alignTop = component;
		return (T) this;
	}

	public T setAlignBottom(String component) {
		this.alignBottom = component;
		return (T) this;
	}

	public T setAlignHorizontalGap(String gap) {
		this.alignHorizontalGap = this.validateDimension(gap);
		return (T) this;
	}

	public T setAlignVerticalGap(String gap) {
		this.alignVerticalGap = this.validateDimension(gap);
		return (T) this;
	}

	public T setSameX(String x) {
		this.sameX = x;
		return (T) this;
	}

	public T setSameY(String y) {
		this.sameY = y;
		return (T) this;
	}

	public void setType(String t) {
	}

	public T setMinValue(Double mv) {
		this.minValue = mv.floatValue();
		return (T) this;
	}

	public T setMaxValue(Double mv) {
		this.maxValue = mv.floatValue();
		return (T) this;
	}

	public T setMinLabel(Double ml) {
		this.minLabel = ml.floatValue();
		return (T) this;
	}

	public T setMaxLabel(Double ml) {
		this.maxLabel = ml.floatValue();
		return (T) this;
	}

	public T incrementValue(String i) {
		String incrementType = this.floatOrInt(i);
		String valueType = this.floatOrInt(this.value);
		if (valueType.equals("NEITHER")) {
			PApplet.println("INFO: " + this.name + " (" + this.TYPE + ") attempted to increment its NON NUMBER value of " + this.value + " by: " + i);
			return (T) this;
		}
		if (incrementType.equals("NEITHER")) {
			PApplet.println("INFO: " + this.name + " (" + this.TYPE + ") attempted to increment its value of " + this.value + " by NON NUMBER: " + i);
			return (T) this;	
		}
		if (valueType.equals("INTEGER")) {
			int increment = Integer.parseInt(i);
			int intValue = Integer.parseInt(this.value);
			intValue += increment;
			this.setValue(String.valueOf(intValue));
			return (T) this;
		}
		if (valueType.equals("FLOAT")) {
			float increment = Float.parseFloat(i);
			float floatValue = Float.parseFloat(this.value);
			floatValue += increment;
			this.setValue(String.valueOf(floatValue));
			return (T) this;
		}
		return (T) this;
	}

	public T incrementLabel(String i) {
		String incrementType = this.floatOrInt(i);
		String labelType = this.floatOrInt(this.label);
		if (labelType.equals("NEITHER")) {
			PApplet.println("INFO: " + this.name + " (" + this.TYPE + ") attempted to increment its NON NUMBER label of " + this.label + " by: " + i);
			return (T) this;
		}
		if (incrementType.equals("NEITHER")) {
			PApplet.println("INFO: " + this.name + " (" + this.TYPE + ") attempted to increment its label of " + this.label + " by NON NUMBER: " + i);
			return (T) this;	
		}
		if (labelType.equals("INTEGER")) {
			int intLabel = Integer.parseInt(this.label);
			int increment = 0;
			if (incrementType.equals("INTEGER")) {
				increment = Integer.parseInt(i);
			}
			if (incrementType.equals("FLOAT")) {
				increment = (int) Float.parseFloat(i);
			}
			intLabel += increment;
			this.setLabel(String.valueOf(intLabel));
			return (T) this;
		}
		if (labelType.equals("FLOAT")) {
			float floatLabel = Float.parseFloat(this.label);
			float increment = Float.parseFloat(i);
			floatLabel += increment;
			this.setLabel(String.valueOf(floatLabel));
			return (T) this;
		}

		return (T) this;
	}

	public T setPayload(JSONObject p) {
		for (Object key : p.keys()) {
			String keyString = (String) key;
			if (!this.payload.hasKey(keyString)) {
				continue;
			}
			this.payload.put(keyString, p.get(keyString));
		}
		return (T) this;
	}

	public boolean setPayloadValue(String key, String value) {
		if (this.payload.hasKey(key)) {
			this.payload.setString(key, value);
			return true;
		}
		return false;
	}

	public void setSettingsByJSON(JSONObject settings) {
		Method method;
		String[] prefixesToAdd = {"set", ""};
		for (Object key : settings.keys()) {
			int index = 0;
			boolean worked = false;
			while (!worked && index < prefixesToAdd.length) {
				String keyString = (String) key;
				Object value = settings.get(keyString);
				keyString = prefixesToAdd[index] + keyString;
				//PApplet.println(key + ": " + value.getClass().toString());
				try {
					method = this.getClass().getMethod(keyString, value.getClass());
					try {
						method.invoke(this, value);	
						worked = true;
					} catch (IllegalArgumentException e) {
						PApplet.println(keyString + ": Illegal Argument");
					} catch (IllegalAccessException e) { 
						PApplet.println(keyString + ": Illegal Access");
					} catch (InvocationTargetException e) {
						PApplet.println(keyString + ": Invocation Target");
					}
				} catch (SecurityException e) {
					PApplet.println(keyString + ": Security Exception");
				} catch (NoSuchMethodException e) {
					PApplet.println(keyString + "(" + value.getClass().toString() + "): No Such Method");
				}

				index++;
			}
		}
	}

	public String validateDimension(String dimension) {
		if (dimension == null) {
			PApplet.println("INFO: Supplied NULL dimension to " + this.name + " (" + this.TYPE + "), set to '" + this.NULLERROR + "a'");
			return String.valueOf(this.NULLERROR) + "a";
		}
		if (dimension.length() == 0) {
			PApplet.println("INFO: Supplied BLANK dimension to " + this.name + " (" + this.TYPE + "), set to '0a'");
			return "0a";
		}
		if (dimension.matches(this.dimensionRegex)) {
			return dimension;
		}
		try{
			Float.parseFloat(dimension);
			PApplet.println("INFO: Supplied dimension of " + dimension + " to " + this.name + " (" + this.TYPE + ") does not have a modifier, 'a' added to dimension");
			return dimension + "a";
		} catch (NumberFormatException e) {
			PApplet.println("INFO: Supplied dimension of " + dimension + " to " + this.name + " (" + this.TYPE + ") is in a format unrecognized, set to '0a'");
			return "0a";
		}
	}

	public String floatOrInt(String string) {
		try {
			Integer.parseInt(string);
			return "INTEGER";
		} catch (NumberFormatException e){
			try {
				Float.parseFloat(string);
				return "FLOAT";
			} catch (NumberFormatException n) {
				return "NEITHER";
			}
		}
	}


}
