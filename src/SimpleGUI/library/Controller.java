package SimpleGUI;

import processing.core.*;
import java.util.*;
import processing.data.JSONObject;
import processing.data.JSONArray;

import java.io.File;


public class Controller {

	private ArrayList<Screen> screens;
	private Screen currentScreen;
	private PApplet myParent;

	public Controller(PApplet theParent) {
		this.myParent = theParent;
		this.screens = new ArrayList<Screen>();
		this.currentScreen = null;
	}

	public void draw() {
		if (this.currentScreen == null) {
			if (this.screens.size() > 0) {
				this.currentScreen = this.screens.get(0);
				PApplet.println("WARN: Attempted to draw current screen, but no current screen found, set to screens.get(0)");
			} else {
				PApplet.println("FATAL: Attempted to draw current screen, and controller does not have any screens attached");
				this.myParent.exit();
				return;
			}
		}
		this.currentScreen.draw();
	}

	public JSONObject checkClick() {
		JSONObject response = this.currentScreen.checkClick();
		if (response == null) {
			return null;
		}
		String command = response.getString("Command"); //shouldn't be able to change the fact that payload always has Command
		String parameters = response.getString("Command Parameters");
		String target = response.getString("Target");
		String name = response.getString("Name");
		if (command.equals("")) {
			return response;
		}
		
		Component component;
		switch (command) {
		case "Change Screen":
			this.setCurrentScreen(parameters);
			return response;
		case "Change Other Label":
			component = this.currentScreen.getComponent(target);
			if (component == null) {
				PApplet.println("INFO: " + name + " (" + response.getString("Screen") + 
						") attempted to change label of " + target + " to " + parameters + " and component was not found!");
				return response;
			}
			component.setLabel(parameters);
			return response;
		case "Change Other Value":
			component = this.currentScreen.getComponent(target);
			if (component == null) {
				PApplet.println("INFO: " + name + " (" + response.getString("Screen") + 
						") attempted to change value of " + target + " to " + parameters + " and component was not found!");
				return response;
			}
			component.setValue(parameters);
			return response;
		case "Increment Other Label":
			component = this.currentScreen.getComponent(target);
			if (component == null) {
				PApplet.println("INFO: " + name + " (" + response.getString("Screen") + 
						") attempted to increment label of " + target + " to " + parameters + " and component was not found!");
				return response;
			}
			component.incrementLabel(parameters);
			return response;
		case "Increment Other Value":
			component = this.currentScreen.getComponent(target);
			if (component == null) {
				PApplet.println("INFO: " + name + " (" + response.getString("Screen") + 
						") attempted to increment value of " + target + " to " + parameters + " and component was not found!");
				return response;
			}
			component.incrementValue(parameters);
			return response;
		case "Change Own Label":
			return response;
		case "Change Own Value":
			return response;
		case "Increment Own Value":
			return response;
		case "Set Screen Invisible":
			return response;
		case "Set Screen Visible":
			return response;
		case "Toggle Screen Visible":
			return response;
		default:
			PApplet.println("INFO: " + name + " (" + response.getString("Screen") + " attempted to run command " + command + " and command does NOT exist!");
		}
		return response;
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

	public boolean setCurrentScreen(String n_) {
		for (Screen screen : this.screens) {
			if (screen.getName().equals(n_)) {
				this.currentScreen = screen;
				return true;
			}
		}
		return false;
	}

	public boolean addScreen(Screen s_) {
		//s_.orderComponents();
		if (s_ == null) {
			return false;
		}
		this.screens.add(s_);
		return true;
	}

	public void setAllByJSON(String filePath) {
		File file = new File(filePath);
		if (file.isDirectory()) {
			return;
		}
		JSONArray settingsArray = PApplet.loadJSONArray(file);
		for (int i = 0; i < settingsArray.size(); i++) { //all of the screens
			JSONObject screenSettings = settingsArray.getJSONObject(i);

		}

	}

	public void setAllByJSON(String screensPath, String screenSettingsName) {
		//go to the directory given by user, if not a directory, quit
		//go through the files in there, and find all the directories
		//when a directory is found, see if there's a file with the name screenSettingsName
		//if yes, try to load it as a JSON
		//if either of those fails, don't make a screen
		File parentDirectory = new File(screensPath);
		if (!parentDirectory.isDirectory()) {
			return;
		}

		for (String directory : parentDirectory.list()) {
			File screenDirectory = new File(screensPath, directory);
			if (!screenDirectory.isDirectory()) {
				continue;
			}
			Screen screen = this.addScreenFromJSON(screenDirectory, screenSettingsName);
			this.addScreen(screen);
		}
	}

	public Screen addScreenFromJSON(File screenDirectory, String screenSettingsName) {
		//when this is called from setAllByJSON in this class, screenDirectory is a directory
		//null screen will be returned if settings file isn't found/can't be loaded into JSONObject format
		Screen screen = new Screen(this.myParent); 
		File settingsFile = new File(screenDirectory, screenSettingsName);
		if (!settingsFile.exists()) {
			return null;
		}
		try {
			JSONObject settings = PApplet.loadJSONObject(settingsFile);
			screen.setSettingsByJSON(settings);
		} catch (RuntimeException e) {
			return null;
		}

		for (String componentFile : screenDirectory.list()) {
			if (componentFile.equals(screenSettingsName)) {
				continue;
			}
			File componentSettings = new File(screenDirectory, componentFile);
			if (componentSettings.isDirectory()) {
				screen.addSubScreen(this.addScreenFromJSON(componentSettings, screenSettingsName));
				continue;
			}
			JSONObject settings;
			try {
				settings = PApplet.loadJSONObject(componentSettings);
			} catch (RuntimeException e) {
				continue;
			}
			if (!settings.hasKey("Type")) {
				continue;
			}
			switch (settings.getString("Type")) {
			case "Button":
				screen.addComponent(new Button(this.myParent, settings));
				break;
			case "DropDown":
				screen.addComponent(new DropDown(this.myParent, settings));
				break;
			case "Slider":
				screen.addComponent(new Slider(this.myParent, settings));
				break;
			case "Text":
				screen.addComponent(new Text(this.myParent, settings));
				break;
			case "Image":
				screen.addComponent(new Image(this.myParent, settings));
				break;
			case "TextBox":
				screen.addComponent(new TextBox(this.myParent, settings));
				break;
			}
		}
		return screen;

	}

	public void printHierarchy() {
		PApplet.println("\n-------------CONTROLLER HIERARCY-------------");
		for (Screen screen : this.screens) {
			String selectionText = screen == this.currentScreen ? " - Current Screen" : "";
			PApplet.println("┌" + screen.getName() + selectionText + "\n│");
			for (Component component : screen.getComponents()) {
				PApplet.println("├───" + component.getName() + " (" + component.getType() + ")\n│");
			}
			for (Screen subScreen : screen.getSubScreens()) {
				String isVisibleText = subScreen.getIsVisible() ? " - Visible" : " - Not Visible";
				PApplet.println("├──┬" + subScreen.getName() + "(SubScreen)" + isVisibleText + "\n│  │");
				for (Component component : subScreen.getComponents()) {
					PApplet.println("│  ├──" + component.getName() + " (" + component.getType() + ")\n│  │");
				}
				PApplet.println("│  └" + subScreen.getName() + " (END)");
			}
			PApplet.println("└" + screen.getName() + " (END)\n");
		}
	}
	
	public Component getComponent(String screenName, String componentName) {
		Screen screen = this.getScreen(screenName);
		if (screen == null) {
			return null;
		}
		return screen.getComponent(componentName);
	}
	
	public Component getComponent(String componentName) {
		return this.currentScreen.getComponent(componentName);
	}
}
