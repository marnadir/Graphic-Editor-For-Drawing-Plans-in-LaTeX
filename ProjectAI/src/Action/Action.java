package Action;

import java.text.DecimalFormat;
import java.util.ArrayList;

import LaTex.LaTexGeneratorAction;

public class Action {

	String name;
	ArrayList<String> prec;
	ArrayList<String> effect;
	CanvasAction paint;
	String latexCode;

	
	int widthRect;
	int lengthPrec;
	int lengthEff;
	int heightRect = 30;
	int standardLengthEff=14; //Standard lenght of effect line 
	int standardLengthPrec=14;
	boolean defaultValuePrecLenght=true;
	boolean defaultValueEffLenght=true;
	int numPrec;
	int numEff;
	
	final double PIXEL_MEASUREMNT= 0.026458;
	final double CM_MEASUREMNT= 37.7957517575025;
	

	public CanvasAction getPaint() {
		return paint;
	}

	public void setPaint(CanvasAction paint) {
		this.paint = paint;
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
		latexCode=generator.getLatexActionCode(this);
	
		
	}
	
	public String getLatexCode() {
		 System.out.println(latexCode);
		return latexCode;
	}
	

	public void resize() {
		int max;
		widthRect = name.length() * 12;
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
			heightRect = 30 + max * 10;
		}

		if (numPrec == 0) {
			standardLengthPrec = 0;
		} 
		else {
			standardLengthPrec = 14;
		}

		if (numEff == 0) {
			standardLengthEff = 0;
		} else {
			standardLengthEff = 14;
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
	
	public int getWidthRect() {
		return widthRect;
	}

	public String getWidthRectInCm() {
		DecimalFormat df = new DecimalFormat("#.00");
	    String angleFormated = df.format(widthRect*PIXEL_MEASUREMNT);
		return angleFormated;
	}
	

	public void setWidthRectFromCm(int widthRect) {
		this.widthRect = (int)(widthRect*CM_MEASUREMNT);
	}



	public int getLengthPrec() {
		return lengthPrec;
	}

	public String getLengthPrecInCm() {
		DecimalFormat df = new DecimalFormat("#.00");
	    String angleFormated = df.format(lengthPrec*PIXEL_MEASUREMNT);
		return angleFormated;
	}

	public void setLengthPrecFromCm(int lengthPrec) {
		this.lengthPrec = (int)(lengthPrec*CM_MEASUREMNT);
	}



	public int getLengthEff() {
		return lengthEff;
	}

	public String getLengthEffInCm() {
		DecimalFormat df = new DecimalFormat("#.00");
	    String angleFormated = df.format(lengthEff*PIXEL_MEASUREMNT);
		return angleFormated;
	}

	public void setLengthEffFromCm(int lengthEff) {
		this.lengthEff = (int)(lengthEff*CM_MEASUREMNT);
	}



	public int getHeightRect() {
		return heightRect;
	}

	public String getHeightRectInCm() {
		DecimalFormat df = new DecimalFormat("#.00");
	    String angleFormated = df.format(heightRect*PIXEL_MEASUREMNT);
		return angleFormated;
	}

	public void setHeightRectFromCm(int heightRect) {
		this.heightRect =(int)( heightRect*CM_MEASUREMNT);
	}



	public int getStandardLengthEff() {
		return standardLengthEff;
	}

	public String getStandardLengthEffInCm() {
		DecimalFormat df = new DecimalFormat("#.00");
	    String angleFormated = df.format(standardLengthEff*PIXEL_MEASUREMNT);
		return angleFormated;
	}

	public void setStandardLengthEffFromCM(int standardLengthEff) {
		this.standardLengthEff =(int) (standardLengthEff*CM_MEASUREMNT);
	}



	public int getStandardLengthPrec() {
		return standardLengthPrec;
	}

	public String getStandardLengthPrecInCm() {
		DecimalFormat df = new DecimalFormat("#.00");
	    String angleFormated = df.format(standardLengthPrec*PIXEL_MEASUREMNT);
		return angleFormated;
	}


	public void setStandardLengthPrecFromCm(int standardLengthPrec) {
		this.standardLengthPrec = (int)(standardLengthPrec*CM_MEASUREMNT);
	}



	public boolean isDefaultValuePrecLenght() {
		return defaultValuePrecLenght;
	}



	public void setDefaultValuePrecLenght(boolean defaultValuePrecLenght) {
		this.defaultValuePrecLenght = defaultValuePrecLenght;
	}



	public boolean isDefaultValueEffLenght() {
		return defaultValueEffLenght;
	}



	public void setDefaultValueEffLenght(boolean defaultValueEffLenght) {
		this.defaultValueEffLenght = defaultValueEffLenght;
	}

	public void setWidthRect(int widthRect) {
		this.widthRect = widthRect;
	}

	public void setLengthPrec(int lengthPrec) {
		this.lengthPrec = lengthPrec;
	}

	public void setLengthEff(int lengthEff) {
		this.lengthEff = lengthEff;
	}

	public void setHeightRect(int heightRect) {
		this.heightRect = heightRect;
	}

	public void setStandardLengthEff(int standardLengthEff) {
		this.standardLengthEff = standardLengthEff;
	}

	public void setStandardLengthPrec(int standardLengthPrec) {
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

	
	
	
}
