package State;

import java.awt.Toolkit;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

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
	double lengthCond;
	double standardCondLength=53;
	boolean defaultValue;
	double lenIn;
	String latexCodeDomain;
	float dpi = Toolkit.getDefaultToolkit().getScreenResolution();
	final double PIXEL_MEASUREMNT= 2.54/dpi;
	final double CM_MEASUREMNT= dpi/2.54;
	boolean isText=false;
	String text;
	boolean globalCond;
	boolean globalEmptyPrec;
	private double height=5.5*CM_MEASUREMNT;
	double width=0.01*CM_MEASUREMNT;



	public void copyAttribute(IState iState) {
		this.name="state";
		this.shownCond=iState.isShownCond();
		this.lengthCond=iState.getLengthCond();
		this.standardCondLength=iState.getStandardLengthCond();
		this.defaultValue=iState.defaultValue;
		this.lenIn=iState.getLenIn();
		this.isText=iState.isText();
		this.text=iState.getText();	
		this.height=iState.getHeight();
		this.width=iState.getWidth();
		
	
	}
	
	public void generateLatexCodeDomain() {
		LaTexGeneratorStateDomain generator=new LaTexGeneratorStateDomain();

		if(this instanceof GoalState) {
			latexCodeDomain=generator.getLatexGoalcode(this);
		}else {
			latexCodeDomain=generator.getLatexSocode(this);

		}
	
	}
	
	public String getHeiInCm() {
		NumberFormat nf_out = NumberFormat.getNumberInstance(Locale.UK);
		nf_out.setMaximumFractionDigits(2);
		String result = nf_out.format(height*PIXEL_MEASUREMNT);
		return result;
	}
	
	public String getWidInCm() {
		NumberFormat nf_out = NumberFormat.getNumberInstance(Locale.UK);
		nf_out.setMaximumFractionDigits(2);
		String result = nf_out.format(width*PIXEL_MEASUREMNT);
		return result;
	}
	
	public String getLengthCondInCm() {
		NumberFormat nf_out = NumberFormat.getNumberInstance(Locale.UK);
		nf_out.setMaximumFractionDigits(2);
		String result = nf_out.format(lengthCond*PIXEL_MEASUREMNT);
		return result;
	}
	public void setLengthFromCm(double d) {
		this.lengthCond = (d*CM_MEASUREMNT);
	}
	
	public void setHeigthFromCm(double d) {
		this.height=(d*CM_MEASUREMNT);
	}
	
	public void setWidFromCm(double d) {
		this.width=(d*CM_MEASUREMNT);
	}
	
	public double getLengthCond() {

		if (globalCond) {
			setLengthFromCm(Double.parseDouble(GlobalValue.lengthsOfConds));
		}
		return lengthCond;

	}
	
	public String getStandardLengthInCm() {
		
		NumberFormat nf_out = NumberFormat.getNumberInstance(Locale.UK);
		nf_out.setMaximumFractionDigits(2);
		String result = nf_out.format(standardCondLength*PIXEL_MEASUREMNT);
		return result;
	}
	
	
	public void setStandardLengthFromCm(double standardLengthPrec) {
		this.standardCondLength =  (standardLengthPrec*CM_MEASUREMNT);
	}

	public double getStandardLengthCond() {
		if(globalEmptyPrec) {
			setStandardLengthFromCm( Double.parseDouble(GlobalValue.lengthsOfEmptyTasks));
		}
		return standardCondLength;
	}
	
	public boolean isDefaultValuePrec() {
		return defaultValue;
	}

	
	
	public void setLengthCond(double lengthCond) {
		this.lengthCond = lengthCond;
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
	
	
	
	public String getLatexCodeDomain() {
		return latexCodeDomain;
	}

	public void setLenIn(double lenIn) {
		this.lenIn = lenIn;
	}

	public double getLenIn() {
		return lenIn;
	}


	public boolean isGlobalCond() {
		return globalCond;
	}


	public void setGlobalCond(boolean globalCond) {
		this.globalCond = globalCond;
	}
	
	public boolean isGlobalEmpty() {
		return globalEmptyPrec;
	}


	public void setGlobalEmpty(boolean globalEmptyPrec) {
		this.globalEmptyPrec = globalEmptyPrec;
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

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	
	
}
