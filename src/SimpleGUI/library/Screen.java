package SimpleGUI;

import processing.core.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import processing.data.JSONObject;


public class Screen {
	private String initX;
	private String initY;
	private String initW;
	private String initH;
	private PApplet myParent;
	private PGraphics graphics;
	private ArrayList<Component> components;
	private ArrayList<Screen> subScreens;
	private String name = "";
	private int drawOrder = 0;
	private boolean isVisible = true;
	private boolean isResizable = false;
	private boolean isMoveable = false;
	private int backgroundColor = 0;
	private int strokeColor = 255;
	private int strokeWeight = 1;
	private final float NULLERROR = -123.4f;

	public Screen(PApplet theParent) {
		this.initX = "0a";
		this.initY = "0a";
		this.initW = "0a";
		this.initH = "0a";
		this.initialize(theParent);
	}

	public Screen(PApplet theParent, String ix, String iy, String iw, String ih) {
		this.name = "";
		this.initX = ix;
		this.initY = iy;
		this.initW = iw;
		this.initH = ih;
		this.initialize(theParent);

	}

	public Screen(PApplet theParent, float ix, float iy, float iw, float ih) {
		this.initX = String.valueOf(ix) + "a";
		this.initY = String.valueOf(iy) + "a";
		this.initW = String.valueOf(iw) + "a";
		this.initH = String.valueOf(ih) + "a";
		this.initialize(theParent);

	}

	public Screen(PApplet theParent, JSONObject settings) {
		this.initX = "0a";
		this.initY = "0a";
		this.initW = "0a";
		this.initH = "0a";
		this.initialize(theParent);
		this.setSettingsByJSON(settings);

	}

	public void initialize(PApplet theParent) {
		this.myParent = theParent;
		this.graphics = theParent.g;
		this.components = new ArrayList<Component>();
		this.subScreens = new ArrayList<Screen>();
	}


	public void draw() {
		if (!this.isVisible) {
			return;
		}
		if (this.strokeWeight == 0) {
			this.graphics.noStroke();
		} else {
			this.graphics.strokeWeight(this.strokeWeight);
			this.graphics.stroke(this.strokeColor);
		}
		this.graphics.rectMode(PConstants.CORNER);
		this.graphics.fill(this.backgroundColor);
		this.graphics.rect(this.getX(), this.getY(), this.getW(), this.getH());
		for (Screen subScreen : this.subScreens) {
			subScreen.draw();
		}
		for (Component component : this.components) {
			component.draw();
		}

	}

	public JSONObject checkClick() {
		//check subScreens first, since the subScreens are showing above the main screen
		//if subScreens don't show anything being pressed, then check the main screen
		//should add an option for what to do if subScreen isn't even clicked, like closing it
		for (int i = this.subScreens.size() - 1; i >= 0; i--) {
			Screen subScreen = this.subScreens.get(i);
			if (subScreen.getIsVisible()) {
				JSONObject subReturn = subScreen.checkClick();
				if (subReturn != null) {
					return subReturn;
				}
			}
		}
		for (int i = this.components.size() - 1; i >= 0; i--) {
			Component component = this.components.get(i);
			if (component.mouseOver(true)) {
				return component.getPayload();
				//return this.name + ":" + component.getType() + ":" + component.getName();
			}
		}
		return null;
	}

	public ArrayList<String> getValues() {
		ArrayList<String> vals = new ArrayList<String>();
		for (Component component : components) {
			vals.add(this.name + ":" + component.getType() + ":" + component.getName() + ":" + component.getValue());
		}
		return vals;
	}

	public void reset() {
		for (Component com : this.components) {
			com.reset();
		}
	}

	public String getName() {
		return this.name;
	}

	public Component getComponent(String n_) {
		for (Component com : this.components) {
			if (com.getName().equals(n_)) {
				return com;
			}
		}
		return null;
	}

	public void addComponent(Component c_) {
		c_.setScreen(this);
		c_.initialize();
		c_.setPayloadValue("Screen", this.name);
		boolean isAdded = false;
		for (int i = 0; i < this.components.size(); i++) {
			if (c_.getDrawOrder() <= this.components.get(i).getDrawOrder()) {
				this.components.add(i, c_);
				isAdded = true;
				break;
			}
		}
		if (!isAdded) {
			this.components.add(c_);
		}
	}

	public void addSubScreen(Screen subScreen) {
		if (subScreen == null) {
			return;
		}
		subScreen.setIsVisible(false);
		boolean isAdded = false;
		for (int i = 0; i < this.subScreens.size(); i++) {
			if (subScreen.getDrawOrder() <= this.subScreens.get(i).getDrawOrder()) {
				this.subScreens.add(subScreen);
				isAdded = true;
				break;
			}
		}
		if (!isAdded) {
			this.subScreens.add(subScreen);
		}
	}

	public void removeComponent(String c_){
		for (Component c : this.components){
			if (c.getName().equals(c_)){
				this.components.remove(c);
				break;
			}
		}
	}

	public void removeComponents(String c_){
		Iterator<Component> allComponents = this.components.iterator();
		while(allComponents.hasNext()){
			if (allComponents.next().getName().contains(c_)){
				allComponents.remove();
			}
		}
	}

	public void updateComponentsScreen() {
		for (Component c : this.components) {
			c.setScreen(this);
		}
	}

	public Screen setX(String x) {
		this.initX = x;
		return this;
	}

	public Screen setY(String y) {
		this.initY = y;
		return this;
	}

	public Screen setW(String w) {
		this.initW = w;
		return this;
	}

	public Screen setH(String h) {
		this.initH = h;
		return this;
	}

	public Screen setName(String n_) {
		this.name = n_;
		return this;
	}

	public Screen setDrawOrder(int newDrawOrder) {
		this.drawOrder = newDrawOrder;
		return this;
	}

	public Screen setIsVisible(boolean newIsVisible) {
		this.isVisible = newIsVisible;
		return this;
	}

	public Screen toggleIsVisible() {
		this.isVisible = !this.isVisible;
		return this;
	}

	public Screen toggleSubScreenIsVisible(String screenName) {
		for (Screen subScreen : this.subScreens) {
			if (subScreen.getName().equals(screenName)) {
				subScreen.toggleIsVisible();
				break;
			}
		}
		return this;
	}

	public Screen setIsResizable(boolean newIsResizable) {
		this.isResizable = newIsResizable;
		return this;
	}

	public Screen setIsMoveable(boolean newIsMoveable) {
		this.isMoveable = newIsMoveable;
		return this;
	}
	
	public Screen setBackgroundColor(Integer bc) {
		this.backgroundColor = bc;
		return this;
	}
	
	public Screen setStrokeColor(Integer sc) {
		this.strokeColor = sc;
		return this;
	}
	
	public Screen setStrokeWeight(Integer sw) {
		this.strokeWeight = sw;
		return this;
	}

	public void printOrder() {
		for (int i = 0; i < this.components.size(); i++) {
			PApplet.println(PApplet.str(i) + ": " + this.components.get(i).getName() + " [" + this.components.get(i).getDrawOrder() + "]");
		}
	}

	float convertDimension(String dimension){
		if (dimension == null) {
			return this.NULLERROR;
		}
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
			return number * 1.0f * this.myParent.width;
		case "h":
			return number * 1.0f * this.myParent.height;
		}
		return 0;
	}


	public float getX() {
		return this.convertDimension(this.initX);
	}
	public float getY() {
		return this.convertDimension(this.initY);
	}
	public float getW() {
		return this.convertDimension(this.initW);
	}
	public float getH() {
		return this.convertDimension(this.initH);
	}
	public int getDrawOrder() {
		return this.drawOrder;
	}
	public boolean getIsVisible() {
		return this.isVisible;
	}
	public boolean getIsResizable() {
		return this.isResizable;
	}
	public boolean getIsMoveable() {
		return this.isMoveable;
	}
	
	public ArrayList<Component> getComponents(){
		return this.components;
	}
	
	public ArrayList<Screen> getSubScreens(){
		return this.subScreens;
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
					PApplet.println(keyString + ": No Such Method");
				}

				index++;
			}
		}
	}

}