package logic;

import java.util.ArrayList;

public class InitialState {

	ArrayList<String> precPos;
//	ArrayList<String> precNeg;
	
	public InitialState(ArrayList<String> pos) {
		this.precPos=new ArrayList<>(pos);
//		this.precNeg=new ArrayList<>(neg);
	}
	
	//TODO pay attention for contraction, example we have A, can't be added notA
	public ArrayList<String> getPrec() {
		return precPos;
		
	}
	 
	//TODO method allow to draw the initial state
	
	//TODO method that write the latex code 
}
