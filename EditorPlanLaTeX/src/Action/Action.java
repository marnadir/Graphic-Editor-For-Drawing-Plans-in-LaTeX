package Action;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import LaTex.LaTexGeneratorAction;
/**
 * Represents the logical part of an action
 * @author nadir
 *
 */
public class Action implements Serializable {


	private static final long serialVersionUID = 1L;
	final double PIXEL_MEASUREMNT= 0.026458;
	final double CM_MEASUREMNT= 37.7957517575025;
	
	
	String name;
	ArrayList<String> prec;
	ArrayList<String> effect;
	ActionDomainCanvas paintCanvas;
	String latexCode;

	
	double widthRect;
	double lengthPrec;
	double lengthEff;
	double heightRect = 30;
	double standardLengthEff=(CM_MEASUREMNT*Double.parseDouble(GlobalValue.lengthsOfEmptyTasks)); //Standard length of effect line 
	double standardLengthPrec= (CM_MEASUREMNT*Double.parseDouble(GlobalValue.lengthsOfEmptyTasks));
	boolean defaultValuePrecLenght=true;
	boolean defaultValueEffLenght=true;
	boolean defaultValueWid=true;
	boolean defaultValueHeig=true;
	
	boolean globalWidth;
	boolean globalHeight;
	boolean globalPrec;
	boolean globalEff;
	boolean globalEmptyPrec;
	boolean globalEmptyEff;
	boolean isPrimitive;
	boolean isAbstract;
	boolean borderIsSquare;
	boolean isFat;//true fett, false normal
	boolean Isborder;//true is black, false is white
	boolean defaultAction=true;

	int numPrec;
	int numEff;
	boolean shownCond = false;
	boolean shownName = true;
	boolean isFillColor=false;
	String colorString;
	

	

	public ActionDomainCanvas getPaint() {
		return paintCanvas;
	}

	public void setPaint(ActionDomainCanvas paint) {
		this.paintCanvas = paint;
	}

	/*
	 * used during the drag&drop and in the save/load program
	 */
	public void copyAttribute(Action a) {	
		widthRect=a.widthRect;
		lengthPrec=a.lengthPrec;
		lengthEff=a.lengthEff;
		standardLengthEff=a.standardLengthEff;
		standardLengthPrec=a.standardLengthPrec;
		defaultValuePrecLenght=a.defaultValuePrecLenght;
		defaultValueEffLenght=a.defaultValueEffLenght;
		defaultValueWid=a.defaultValueWid;
		defaultValueHeig=a.defaultValueHeig;
		defaultAction=a.defaultAction;

		shownCond=a.shownCond;
		shownName=a.shownName;
		Isborder=a.Isborder;
		isFillColor=a.isFillColor;
		colorString=a.colorString;
		isPrimitive=a.isPrimitive;
		isAbstract=a.isAbstract;
		isFat=a.isFat;
		borderIsSquare=a.borderIsSquare;
		
		 globalWidth=a.globalWidth;
		 globalHeight=a.globalHeight;
		 globalPrec=a.globalPrec;
		 globalEff=a.globalEff;
		 globalEmptyPrec=a.globalEmptyPrec;
		 globalEmptyEff=a.globalEmptyEff;	
		
	}
	
	public Action(String name, ArrayList<String> prec, ArrayList<String> eff) {
		this.name = name;
		if(prec != null) {
			this.prec = prec;

		}else {
			this.prec=new ArrayList<>();
		}
		
		if(eff != null) {
			this.effect=eff;
		}else {
			this.effect = new ArrayList<>();

		}
		resize();
	}
	
	
	public void generateLatexCode() {
		LaTexGeneratorAction generator=new LaTexGeneratorAction();
		latexCode=generator.getLatexActionCodeDomain(this);
	
		
	}
	
	public String getLatexCode() {
		return latexCode;
	}
	

	public void resize() {
		int max;
		if(defaultValueWid) {
			widthRect = name.length() * 12;
		}
		numPrec = prec.size();
		numEff= effect.size();
		if (defaultValuePrecLenght) {
			lengthPrec = getLenght(prec) *9;
		}
		if (defaultValueEffLenght) {
			lengthEff = getLenght(effect) *9;
		}
		max = numPrec;
		if (numEff > max) {
			max = numEff;
		}

		if (max > 1) {
			if(defaultValueHeig) {
				heightRect = 30 + max * 10;

			}
		}

		if (numPrec == 0) {
			standardLengthPrec = 0;
		} 
		

		if (numEff == 0) {
			standardLengthEff = 0;
		} 
		
		
		
	}
	
	
	public int getLenght(ArrayList<String> conds) {

		int lenght = 0;
		if (conds.size() > 0) {
			String stringa = conds.get(0);
			lenght=stringa.length();
			for (String cond : conds) {
				if (cond.length() > stringa.length()) {
					stringa = cond;
					lenght = cond.length();
				}
			}
		}
		return lenght;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getPrec() {
		return prec;
	}

	public void setPrec(ArrayList<String> prec) {
		this.prec = prec;
	}

	public ArrayList<String> getEffect() {
		return effect;
	}

	public void setEffect(ArrayList<String> effect) {
		this.effect = effect;
	}
	
	public double getWidthRect() {
		if(globalWidth) {
			setWidthRectFromCm( Double.parseDouble(GlobalValue.widthOfAction));
		}
		return widthRect;
	}

	public String getWidthRectInCm() {
		NumberFormat nf_out = NumberFormat.getNumberInstance(Locale.UK);
		nf_out.setMaximumFractionDigits(2);
		String result = nf_out.format(widthRect*PIXEL_MEASUREMNT);
		return result;
	}
	

	public void setWidthRectFromCm(double widthRect) {
		this.widthRect = (widthRect*CM_MEASUREMNT);
	}



	public double getLengthPrec() {
		if(isGlobalPrec()) {
			setLengthPrecFromCm(Double.parseDouble(GlobalValue.lengthsOfPrecs));
		}
		return lengthPrec;
	}

	public String getLengthPrecInCm() {
		NumberFormat nf_out = NumberFormat.getNumberInstance(Locale.UK);
		nf_out.setMaximumFractionDigits(2);
		String result = nf_out.format(lengthPrec*PIXEL_MEASUREMNT);
		return result;
	}

	public void setLengthPrecFromCm(double lengthPrec) {
		this.lengthPrec =(lengthPrec*CM_MEASUREMNT);
	}



	public double getLengthEff() {
		if(globalEff) {
			setLengthEffFromCm(Double.parseDouble(GlobalValue.lengthsOfEffs));
		}
		return lengthEff;
	}

	public String getLengthEffInCm() {
		NumberFormat nf_out = NumberFormat.getNumberInstance(Locale.UK);
		nf_out.setMaximumFractionDigits(2);
		String result = nf_out.format(lengthEff*PIXEL_MEASUREMNT);
		return result;
	}

	public void setLengthEffFromCm(double lengthEff) {
		this.lengthEff = (lengthEff*CM_MEASUREMNT);
	}



	public double getHeightRect() {
		if(globalHeight) {
			setHeightRectFromCm( Double.parseDouble(GlobalValue.heightOfAction));
		}
		return heightRect;
	}

	public String getHeightRectInCm() {
		NumberFormat nf_out = NumberFormat.getNumberInstance(Locale.UK);
		nf_out.setMaximumFractionDigits(2);
		String result = nf_out.format(heightRect*PIXEL_MEASUREMNT);
		return result;

	}
	
	
	public void setHeightRectFromCm(double heightRect) {
		this.heightRect =( heightRect*CM_MEASUREMNT);
	}


	public double getStandardLengthEff() {

		if (globalEmptyEff) {
			setStandardLengthEffFromCm(Double.parseDouble(GlobalValue.lengthsOfEmptyTasks));
		}
		return standardLengthEff;
	}

	public String getStandardLengthEffInCm() {
		NumberFormat nf_out = NumberFormat.getNumberInstance(Locale.UK);
		nf_out.setMaximumFractionDigits(2);
		String result = nf_out.format(standardLengthEff*PIXEL_MEASUREMNT);
		return result;
	}
	

	public void setStandardLengthEffFromCm(double standardLengthEff) {
		this.standardLengthEff = (standardLengthEff*CM_MEASUREMNT);
	}



	public String getStandardLengthPrecInCm() {
		NumberFormat nf_out = NumberFormat.getNumberInstance(Locale.UK);
		nf_out.setMaximumFractionDigits(2);
		String result = nf_out.format(standardLengthPrec*PIXEL_MEASUREMNT);
		return result;
	}

	public double getStandardLengthPrec() {
		if(globalEmptyPrec) {
			setStandardLengthPrecFromCm( Double.parseDouble(GlobalValue.lengthsOfEmptyTasks));
		}
		return standardLengthPrec;
	}
	
	
	public void setStandardLengthPrecFromCm(double standardLengthPrec) {
		this.standardLengthPrec = (standardLengthPrec*CM_MEASUREMNT);
	}



	public boolean isDefaultValuePrecLenght() {
		return defaultValuePrecLenght;
	}



	public void setDefaultValuePrecLenght(boolean defaultValuePrecLenght) {
		this.defaultValuePrecLenght = defaultValuePrecLenght;
	}

	public void setDefaultValueEffLenght(boolean defaultValueEffLenght) {
		this.defaultValueEffLenght = defaultValueEffLenght;
	}

	public void setWidthRect(double widthRect) {
		this.widthRect = widthRect;
	}

	public void setLengthPrec(double lengthPrec) {
		this.lengthPrec = lengthPrec;
	}

	public void setLengthEff(double lengthEff) {
		this.lengthEff = lengthEff;
	}

	public void setHeightRect(double heightRect) {
		this.heightRect = heightRect;
	}

	public void setStandardLengthEff(double standardLengthEff) {
		this.standardLengthEff = standardLengthEff;
	}

	public void setStandardLengthPrec(double standardLengthPrec) {
		this.standardLengthPrec = standardLengthPrec;
	}

	public int getNumPrec() {
		return numPrec;
	}

	public void setNumPrec(int numPrec) {
		this.numPrec = numPrec;
	}

	public int getNumEff() {
		return numEff;
	}

	public void setNumEff(int numEff) {
		this.numEff = numEff;
	}

	public boolean isDefaultValueWid() {
		return defaultValueWid;
	}

	public void setDefaultValueWid(boolean defaultValueWid) {
		this.defaultValueWid = defaultValueWid;
	}

	public boolean isDefaultValueHeig() {
		return defaultValueHeig;
	}

	public void setDefaultValueHeig(boolean defaultValueHeig) {
		this.defaultValueHeig = defaultValueHeig;
	}

	public boolean isShownCond() {
		return shownCond;
	}

	public boolean isShownName() {
		return shownName;
	}


	public void negateIsShownCond() {
		shownCond=!shownCond;
	}

	public void setIsShownCond(boolean bol) {
		shownCond=bol;
	}
	

	public void negateIsShownName() {
		shownName=!shownName;
	}

	public boolean Isborder() {
		return Isborder;
	}

	public void setIsborder(boolean form) {
		this.Isborder = form;
	}

	public boolean isFillColor() {
		if(colorString!=null) {
			if(!(colorString.equals("None"))) {
				isFillColor=true;
			}
		}
		return isFillColor;
	}

	public void setIsFillColor(boolean isFillColor) {
		this.isFillColor = isFillColor;
	}

	




	public String getColorString() {
		return colorString;
	}

	public void setColorString(String colorString) {
		this.colorString = colorString;
	}

	public boolean isGlobalWidth() {
		return globalWidth;
	}

	public void setGlobalWidth(boolean isGlobalWidth) {
		this.globalWidth = isGlobalWidth;
	}

	public boolean isGlobalHeight() {
		return globalHeight;
	}

	public void setGlobalHeight(boolean isGlobalHeight) {
		this.globalHeight = isGlobalHeight;
	}

	public boolean isGlobalPrec() {
		return globalPrec;
	}

	public void setGlobalPrec(boolean isGlobalPrec) {
		this.globalPrec = isGlobalPrec;
	}

	public boolean isGlobalEff() {
		return globalEff;
	}

	public void setGlobalEff(boolean isGlobalEff) {
		this.globalEff = isGlobalEff;
	}

	public boolean isGlobalEmptyPrec() {
		return globalEmptyPrec;
	}

	public void setGlobalEmptyPrec(boolean globalEmptyPrec) {
		this.globalEmptyPrec = globalEmptyPrec;
	}

	public boolean isGlobalEmptyEff() {
		return globalEmptyEff;
	}

	public void setGlobalEmptyEff(boolean globalEmptyEff) {
		this.globalEmptyEff = globalEmptyEff;
	}

	public boolean isPrimitive() {
		return isPrimitive;
	}

	public void setPrimitive(boolean isPrimitive) {
		this.isPrimitive = isPrimitive;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	public boolean isFett() {
		return isFat;
	}

	public void setIsFett(boolean fett) {
		this.isFat = fett;
	}

	public boolean isBorderIsSquare() {
		return borderIsSquare;
	}

	public void setBorderIsSquare(boolean borderIsSquare) {
		this.borderIsSquare = borderIsSquare;
	}

	public boolean isDefaultAction() {
		return defaultAction;
	}

	public void setDefaultAction(boolean defaultPrim) {
		this.defaultAction = defaultPrim;
	}

	
}
