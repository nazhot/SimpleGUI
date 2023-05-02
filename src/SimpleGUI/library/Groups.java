package SimpleGUI;

import processing.core.*;
import java.util.*;
import processing.data.JSONObject;

public class Groups {
	ArrayList<JSONObject> groups;
	String[] groupSettings = {"Name", "X", "Y", "Width", "Height"};
	
	Groups(){
		this.groups = new ArrayList<JSONObject>();
	}
	
	public void addGroup(JSONObject group) {
		this.groups.add(group);
	}
	
	public JSONObject getGroupByName(String name) {
		for (JSONObject group : this.groups) {
			if (group.getString("Name").equals(name)) {
				return group;
			}
		}
		return null;
	}
	
	
}
