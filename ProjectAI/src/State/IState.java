package State;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

import Action.GlobalValue;
import LaTex.LaTexGeneratorStateDomain;

public class IState  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2082191482890474964L;
	ArrayList<String> conds;
	String name="state";
	boolean shownCond = false;
	int lengthCond;
	int standardCondLength=53;
	boolean defaultValue;
	int lenIn;
	String latexCodeDomain;
	final double PIXEL_MEASUREMNT= 0.026458;
	final double CM_MEASUREMNT= 37.7957517575025;
	boolean isText=false;
	String text;
	boolean globalCond;
	boolean globalEmptyPrec;


	

	public boolean isGlobalEmptyPrec() {
		return globalEmptyPrec;
	}


	public void setGlobalEmptyPrec(boolean globalEmptyPrec) {
		this.globalEmptyPrec = globalEmptyPrec;
	}


	public void copyAttribute(IState iState) {
		this.name="state";
		this.shownCond=iState.isShownCond();
		this.lengthCond=iState.getLengthCond();
		this.standardCondLength=iState.getStandardLength();
		this.defaultValue=iState.defaultValue;
		this.lenIn=iState.getLenIn();
		this.isText=iState.isText();
		this.text=iState.getText();	
	}
	
	
	public IState(ArrayList<String> conds) {
		this.conds=conds;
	}

	public ArrayList<String> getConds() {
		return conds;
	}
	
	public void addCond(String cond) {
		conds.add(cond);
	}
	
	public void removeConds() {
		conds.clear();
	}
	
	public void updateConds(ArrayList<String> conds) {
		this.conds=conds;
	}
	

	public String getName() {
		return name;
	}

	
	public boolean isShownCond() {
		return shownCond;
	}



	public void setShownCond(boolean shownCond) {
		this.shownCond = shownCond;
	}



	public int getLengthCond() {

		if (globalCond) {
			setLengthFromCm(Double.parseDouble(GlobalValue.lengthsOfConds));
		}
		return lengthCond;

	}

	
	
	public void setLengthCond(int lengthCond) {
		this.lengthCond = lengthCond;
	}

	public String getLengthCondInCm() {
		DecimalFormat df = new DecimalFormat("#.00");
	    String angleFormated = df.format(lengthCond*PIXEL_MEASUREMNT);
		return angleFormated;
	}


	public void setLengthFromCm(double d) {
		this.lengthCond = (int)(d*CM_MEASUREMNT);
	}



	public int getStandardLength() {
		if(globalEmptyPrec) {
			setStandardLengthFromCm( Double.parseDouble(GlobalValue.lengthsOfEmptyTasks));
		}
		return standardCondLength;
	}

	public String getStandardLengthInCm() {
		DecimalFormat df = new DecimalFormat("#.00");
	    String angleFormated = df.format(standardCondLength*PIXEL_MEASUREMNT);
		return angleFormated;
	}

	public void setStandardLengthFromCm(double standardLengthPrec) {
		this.standardCondLength = (int)(standardLengthPrec*CM_MEASUREMNT);
	}



	public boolean isDefaultValuePrec() {
		return defaultValue;
	}



	public void setDefaultValue(boolean defaultValuePrec) {
		this.defaultValue = defaultValuePrec;
	}
	
	public void negateIsShownCond() {
		shownCond=!shownCond;
	}

	public String getText() {
		return text;
	}



	public void setText(String text) {
		this.text = text;
	}



	public boolean isText() {
		return isText;
	}
	
	public void setIsText(boolean isText) {
		this.isText=isText;
	}
	
	public void generateLatexCodeDomain() {
		LaTexGeneratorStateDomain generator=new LaTexGeneratorStateDomain();

		if(this instanceof GoalState) {
			latexCodeDomain=generator.getLatexGoalcode(this);
		}else {
			latexCodeDomain=generator.getLatexSocode(this);

		}
	
	}
	
	public String getLatexCodeDomain() {
		return latexCodeDomain;
	}

	public void setLenIn(int lenIn) {
		this.lenIn = lenIn;
	}

	public int getLenIn() {
		return lenIn;
	}


	public boolean isGlobalCond() {
		return globalCond;
	}


	public void setGlobalCond(boolean globalCond) {
		this.globalCond = globalCond;
	}
	
	
	
}
