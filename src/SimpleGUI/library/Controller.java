package SimpleGUI;

import processing.core.*;
import java.util.*;


public class Controller {

	private ArrayList<Screen> screens;
	private Screen currentScreen;
	private ArrayList<Screen> backgroundScreens;
	private PApplet myParent;

	public Controller(PApplet theParent) {
		this.myParent = theParent;

		this.screens = new ArrayList<Screen>();
		this.currentScreen = null;
		this.backgroundScreens = new ArrayList<Screen>();
	}

	public void draw() {
		for (Screen screen : this.backgroundScreens) {
			screen.draw();
		}
		this.currentScreen.draw();
	}

	public String checkClick() {
		return this.currentScreen.checkClick();
	}

	public ArrayList<String> getValues() {
		return this.currentScreen.getValues();
	}

	public Screen getCurrentScreen() {
		return this.currentScreen;
	}

	public Screen getScreen(String name) {
		for (Screen screen : this.screens) {
			if (screen.getName().equals(name)) return screen;
		}
		return null;
	}

	public String getCurrentScreenName() {
		return this.currentScreen.getName();
	}

	public void changeScreen(String n_) {
	}

	public void setCurrentScreen(String n_) {
		for (Screen screen : this.screens) {
			if (screen.getName().equals(n_)) {
				this.currentScreen = screen;
				break;
			}
		}
	}

	public void addScreen(Screen s_) {
		//s_.orderComponents();
		this.screens.add(s_);
	}
}
