package State;

import java.util.ArrayList;

public class GoalState extends IState {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GoalState(ArrayList<String> conds) {
		super(conds);
		this.name="goal";
	}

}
