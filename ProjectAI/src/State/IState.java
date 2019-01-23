package State;

import java.io.Serializable;
import java.util.ArrayList;

public class IState  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2082191482890474964L;
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
