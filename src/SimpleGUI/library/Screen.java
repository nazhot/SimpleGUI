package SimpleGUI;

import processing.core.*;
import java.util.*;



public class Screen {

	private PApplet myParent;
	private PGraphics graphics;
	private ArrayList<Component> components;
	private String name;
	private int gridRows;
	private int gridCols;
	private float rowMultiplier;
	private float colMultiplier;

	public Screen(PApplet theParent) {
		this.myParent = theParent;
		this.graphics = theParent.g;
		this.components = new ArrayList<Component>();
		this.name = "";
		this.gridRows = this.myParent.height;
		this.gridCols = this.myParent.width;
		this.rowMultiplier = 1;
		this.colMultiplier = 1;
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
		boolean isAdded = false;
		c_.setMultipliers(this.colMultiplier, this.rowMultiplier);
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

	public Screen setName(String n_) {
		this.name = n_;
		return this;
	}

	public void printOrder() {
		for (int i = 0; i < this.components.size(); i++) {
			PApplet.println(PApplet.str(i) + ": " + this.components.get(i).getName() + " [" + this.components.get(i).getDrawOrder() + "]");
		}
	}

	public void setComponenetsMultipliers(){
		for (Component c : this.components){
			c.setMultipliers(this.colMultiplier, this.rowMultiplier);
		}
	}

	public Screen setGridRows(int g_) {
		this.gridRows = g_;
		this.rowMultiplier = 1.0f * g_ / this.myParent.height;
		this.setComponenetsMultipliers();
		return this;
	}

	public Screen setGridCols(int g_) {
		this.gridCols = g_;
		this.colMultiplier = 1.0f * g_ / this.myParent.width;
		this.setComponenetsMultipliers();
		return this;
	}

	public Screen setRowMultiplier(float r_) {
		this.rowMultiplier = r_;
		this.gridRows = PApplet.round(r_ * this.myParent.height);
		this.setComponenetsMultipliers();
		return this;
	}

	public Screen setColMultiplier(float c_) {
		this.colMultiplier = c_;
		this.gridCols = PApplet.round(c_ * this.myParent.width);
		this.setComponenetsMultipliers();
		return this;
	}
}