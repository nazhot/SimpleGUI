package SimpleGUI;

import processing.core.*;
import java.util.*;



public class Screen {
	private String initX;
	private String initY;
	private String initW;
	private String initH;
	private PApplet myParent;
	private PGraphics graphics;
	private ArrayList<Component> components;
	private String name;

	public Screen(PApplet theParent, String ix, String iy, String iw, String ih) {
		this.myParent = theParent;
		this.graphics = theParent.g;
		this.components = new ArrayList<Component>();
		this.name = "";
		this.initX = ix;
		this.initY = iy;
		this.initW = iw;
		this.initH = ih;
	}
	
	public Screen(PApplet theParent, float ix, float iy, float iw, float ih) {
		this.initX = String.valueOf(ix) + "a";
		this.initY = String.valueOf(iy) + "a";
		this.initW = String.valueOf(iw) + "a";
		this.initH = String.valueOf(ih) + "a";
	}


	public void draw() {
		for (Component component : components) {
			component.draw();
		}
	}

	public String checkClick() {
		for (int i = this.components.size() - 1; i >= 0; i--) {
			Component component = this.components.get(i);
			if (component.mouseOver(true)) {
				return this.name + ":" + component.getType() + ":" + component.getName();
			}
		}
		return "";
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

	//void orderComponents() { /doesn't work with older version of processing
	//  SortedSet<Interactable> sortedComponents = new TreeSet<>(Comparator.comparingInt(component -> component.getDrawOrder()));
	//  for (Interactable com : this.components) {
	//    sortedComponents.add(com);
	//  }
	//  this.components = new ArrayList<>(sortedComponents);
	//}


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
		boolean isAdded = false;
		for (int i = 0; i < this.components.size(); i++) {
			if (c_.getDrawOrder() <= this.components.get(i).getDrawOrder()) {
				this.components.add(i, c_);
				isAdded = true;
				break;
			}
		}
		if (!isAdded) this.components.add(c_);
		PApplet.println("Added " + c_.getName() + " with no issue");
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

	public Screen setName(String n_) {
		this.name = n_;
		return this;
	}

	public void printOrder() {
		for (int i = 0; i < this.components.size(); i++) {
			PApplet.println(PApplet.str(i) + ": " + this.components.get(i).getName() + " [" + this.components.get(i).getDrawOrder() + "]");
		}
	}

	float convertDimension(String dimension){
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
}