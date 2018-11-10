package logic;

import java.util.ArrayList;

public class InitialState {

	ArrayList<String> precPos;
	ArrayList<String> precNeg;
	
	public InitialState(ArrayList<String> pos,ArrayList<String> neg) {
		this.precPos=new ArrayList<>(pos);
		this.precNeg=new ArrayList<>(neg);
	}
	
	//TODO pay attention for contraction, example we have A, can't be added notA
	public void addPrec() {
		
	}
	
	public void removePrec() {
		
	} 
}
