package dataTrasfer;

import java.util.ArrayList;
/**
 * Object is used to transform action and initial/goal state into sequence of byte and vice versa
 * 
 * */
public class MyType {
	String name;
	ArrayList<String> prec;
	ArrayList<String> eff;

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

	public ArrayList<String> getEff() {
		return eff;
	}

	public void setEff(ArrayList<String> eff) {
		this.eff = eff;
	}
	
	
}
