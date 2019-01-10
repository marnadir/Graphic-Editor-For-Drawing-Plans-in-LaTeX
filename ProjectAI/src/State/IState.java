package State;

import java.util.ArrayList;

import LaTex.LaTexGeneratorAction;

public class IState {

	ArrayList<String> conds;
	String name="state";

	
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

	
	


}
