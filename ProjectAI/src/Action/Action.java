package Action;

import java.util.ArrayList;

public class Action {

	String name;
	ArrayList<String> prec;
	ArrayList<String> effect;
	CanvasAction paint;

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
	
	

}
