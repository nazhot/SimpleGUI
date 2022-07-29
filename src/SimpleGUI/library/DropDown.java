package SimpleGUI;

import processing.core.*;
import java.util.*;


public class DropDown extends Component<DropDown> {
	private ArrayList<String> entries;            //the text entries that can be chosen from the dropdown menu
	private ArrayList<Boolean> selected;          //whether each entry has been selected or not
	private float entryHeight;                    //height of each entry box
	private float entryWidth;                     //width of each entry box
	private boolean multiSelect;                  //whether the user can select more than one entry at a time or not
	private boolean isOpen;                       //whether the menu is dropped down or not
	private int selectedColor;                    //fill color for selected entries
	private boolean alwaysOpen;                   //whether the menu should always be dropped down or not
	private boolean isVertical;                   //whether the entries should be shown in a vertical line or not
	private String direction;                     //LEFT, RIGHT, UP, DOWN: the direction the drop down should go from the title block
	private boolean selectAll;                    //whether the drop down should have a "Select All" button or not
	private int titleHorizontalOrientation;       //orientation for the text in the title
	private int entryHorizontalOrientation;       //orientation for the text in each entry
	private float entryVsTitleOrientationPercent; //position of the entry boxes compared to the title box (0 is fully left/up, 1 is fully right/down)
	private boolean titleStroke;                  //whether the title block has stroke lines or not

	public DropDown(PApplet theParent, String x_, String y_, String w_, String h_) {
		super(theParent, x_, y_, w_, h_);
		this.TYPE = "DropDown";
		this.entries = new ArrayList<String>();
		this.selected = new ArrayList<Boolean>();
		this.multiSelect = false;
		this.isOpen = false;
		this.alwaysOpen = false;
		this.isVertical = true;
		this.direction = "DOWN";
		this.selectAll = false;
		this.titleHorizontalOrientation = PConstants.LEFT;
		this.entryHorizontalOrientation = PConstants.LEFT;
		this.entryVsTitleOrientationPercent = 0;
		this.titleStroke = true;
	}
	
	public DropDown(PApplet theParent, float x_, float y_, float w_, float h_) {
		super(theParent, x_, y_, w_, h_);
		this.TYPE = "DropDown";
		this.entries = new ArrayList<String>();
		this.selected = new ArrayList<Boolean>();
		this.multiSelect = false;
		this.isOpen = false;
		this.alwaysOpen = false;
		this.isVertical = true;
		this.direction = "DOWN";
		this.selectAll = false;
		this.titleHorizontalOrientation = PConstants.LEFT;
		this.entryHorizontalOrientation = PConstants.LEFT;
		this.entryVsTitleOrientationPercent = 0;
		this.titleStroke = true;
	}
	
	public boolean initialize(Screen screenParent) {
		this.entryHeight = this.getH(screenParent);
		this.entryWidth = this.getW(screenParent);
		return true;
		
	}

	public void draw(Screen screenParent) {
		float x = this.getX(screenParent);
		float y = this.getY(screenParent);
		float w = this.getW(screenParent);
		float h = this.getH(screenParent);
		//displaying the title block
		this.graphics.rectMode(PConstants.CORNER);
		this.graphics.fill(this.fillColor);
		this.graphics.stroke(this.strokeColor);
		this.graphics.strokeWeight(this.strokeWeight);
		if (!this.titleStroke) {
			this.graphics.noStroke();
		}
		this.graphics.rect(x, y, w, h, this.rounding);

		//displaying each entry box, if applicable
		this.graphics.fill(this.textColor);
		this.graphics.textSize(this.textSize);
		String tempTitle = this.label;
		if (!this.isOpen && !this.alwaysOpen) { //if it is always open, nothing is added to the title ever
			boolean moreThanOne = false;
			for (int i = 0; i < this.selected.size(); i++) { //append the title block if something is selected, and if multiple are selected add "..." after the first selected entry
				if (this.selected.get(i)) {
					if (moreThanOne) {
						tempTitle += "...";
						break;
					}
					tempTitle += this.entries.get(i);
					moreThanOne = true;
				}
			}
		}
		this.graphics.textAlign(this.titleHorizontalOrientation, PConstants.CENTER);
		float titleX = x + w * 0.02f;
		if (this.titleHorizontalOrientation == PConstants.CENTER) {
			titleX += w * 0.48f;
		} else if (this.titleHorizontalOrientation == PConstants.RIGHT) {
			titleX += w * 0.96f;
		}
		this.graphics.text(tempTitle, titleX, y + h / 2.0f - this.graphics.textAscent() * this.textScalar);

		if (!this.isOpen && !this.alwaysOpen) {
			return;
		}

		this.graphics.strokeWeight(this.strokeWeight);
		this.graphics.stroke(this.strokeColor);
		float entryXOffset = PApplet.map(this.entryVsTitleOrientationPercent, 0, 1, 0, w - this.entryWidth);
		float rectX = x + entryXOffset;
		float rectY = y;
		float textX = x + this.entryWidth * 0.02f + entryXOffset;
		float textY = y + h / 2.0f - this.graphics.textAscent() * this.textScalar;
		if (this.isVertical) {
			rectY += h;
			textY += h / 2.0f + this.entryHeight / 2.0f;
		} else {
			rectY += h / 2.0f - this.entryHeight / 2.0f;
			rectX += this.entryWidth;
			textX += this.entryWidth;
		}
		if (this.entryHorizontalOrientation == PConstants.CENTER) {
			textX += this.entryWidth * 0.48f;
		}
		this.graphics.textAlign(this.entryHorizontalOrientation, PConstants.CENTER);
		for (int i = 0; i < this.entries.size(); i++) {

			String entry = this.entries.get(i);
			float extraWidth = 0;
			if (this.selected.get(i)) {
				this.graphics.fill(this.selectedColor);
				extraWidth += this.entryWidth * 0.1f;
			} else {
				this.graphics.fill(this.fillColor);
			}
			this.graphics.rect(rectX - extraWidth / 2.0f, rectY, this.entryWidth + extraWidth, this.entryHeight, this.rounding);
			this.graphics.fill(this.textColor);
			this.graphics.text(entry, textX + this.entryWidth * 0.02f, textY);
			if (this.isVertical) {
				rectY += this.entryHeight;
				textY += this.entryHeight;
			} else {
				rectX += this.entryWidth;
				textX += this.entryWidth;
			}
		}
	}


	public int mouseOverEntry(Screen screenParent) {
		float x = this.getX(screenParent);
		float y = this.getY(screenParent);
		float w = this.getW(screenParent);
		float h = this.getH(screenParent);
		float entryXOffset = PApplet.map(this.entryVsTitleOrientationPercent, 0, 1, 0, w - this.entryWidth);
		if (this.myParent.mouseX >= x + entryXOffset && this.myParent.mouseX <= x + this.entryWidth + entryXOffset) {
			for (int i = 0; i < this.entries.size(); i++) {
				if (this.myParent.mouseY >= y + h + this.entryHeight * i && this.myParent.mouseY <= y + h + this.entryHeight * (i + 1)) {
					return i;
				}
			}
		}
		return -1;
	}

	public boolean mouseOver(Screen screenParent, boolean calledByScreen) {
		float x = this.getX(screenParent);
		float y = this.getY(screenParent);
		float w = this.getW(screenParent);
		float h = this.getH(screenParent);
		boolean isMouseOver = (this.myParent.mouseX >= x && this.myParent.mouseX <= x + w && this.myParent.mouseY >= y && this.myParent.mouseY <= y + h);
		boolean isMouseOverEntry = false;
		if (this.isOpen || this.alwaysOpen) {
			int index = this.mouseOverEntry(screenParent);
			if (index >= 0) {
				isMouseOverEntry = true;
				if (this.selectAll && index == 0) {
					boolean allSelected = true;
					for (int i = 1; i < this.selected.size(); i++) {
						if (!this.selected.get(i)) {
							allSelected = false;
							break;
						}
					}
					this.selected.set(0, true);
					for (int i = 1; i < this.selected.size(); i++) {
						this.selected.set(i, !allSelected);
					}
				}
				if (this.multiSelect) {
					this.selected.set(index, !this.selected.get(index));
				} else {
					boolean tempSelected = !this.selected.get(index);
					for (int i = 0; i < this.selected.size(); i++) {
						this.selected.set(i, false);
					}
					this.selected.set(index, tempSelected);
				}
			}
		}
		if (isMouseOver) {
			this.toggleIsOpen();
		}


		return isMouseOver || isMouseOverEntry;
	}

	public void reset() {
		for (int i = 0; i < this.selected.size(); i++) {
			this.selected.set(i, false);
		}
		this.isOpen = false;
	}



	public DropDown setMultiSelect(boolean m_) {
		this.multiSelect = m_;
		return this;
	}

	public DropDown setIsVertical(boolean i_) {
		this.isVertical = i_;
		return this;
	}

	public DropDown addEntry(String e_) {
		this.entries.add(e_);
		this.selected.add(false);
		return this;
	}

	public DropDown setAlwaysOpen(boolean a_) {
		this.alwaysOpen = a_;
		return this;
	}


	public DropDown setEntryHeight(float e_) {
		this.entryHeight = e_;
		return this;
	}

	public DropDown setEntryWidth(float e_) {
		this.entryWidth = e_;
		return this;
	}

	public DropDown addEntries(ArrayList<String> e_) {
		for (String entry : e_) {
			this.entries.add(entry);
			this.selected.add(false);
		}
		return this;
	}

	public DropDown addEntries(String[] e_) {
		for (String entry : e_) {
			this.entries.add(entry);
			this.selected.add(false);
		}
		return this;
	}

	public DropDown addSelectAll() {
		this.selectAll = true;
		this.entries.add(0, "Select All");
		this.selected.add(0, false);
		return this;
	}

	public DropDown setIsOpen(boolean i_) {
		this.isOpen = i_;
		return this;
	}

	public DropDown toggleIsOpen() {
		this.isOpen = !this.isOpen;
		return this;
	}

	public DropDown setTitleHorizontalOrientation(int t_) {
		this.titleHorizontalOrientation = t_;
		return this;
	}

	public DropDown setEntryHorizontalOrientation(int e_) {
		this.entryHorizontalOrientation = e_;
		return this;
	}

	public DropDown setEntryVsTitleOrientationPercent(float e_) {
		this.entryVsTitleOrientationPercent = e_;
		return this;
	}

	public DropDown setTitleStroke(boolean t_) {
		this.titleStroke = t_;
		return this;
	}
	
	public DropDown setSelectedColor(int s_) {
		this.selectedColor = s_;
		return this;
	}

	public String getValue() {
		String val = "";
		for (int i = 0; i < this.selected.size(); i++) {
			if (this.selected.get(i)) {
				if (val.length() > 0) {
					val += ",";
				}
				val += this.entries.get(i);
			}
		}
		return val;
	}

}