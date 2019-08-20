package so_goalState;

import java.util.ArrayList;
/**
 * Represents the logical part of the goal state
 * @author nadir
 * */
public class GoalState extends IState {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GoalState(ArrayList<String> conds) {
		super(conds);
		this.name="goal";
	}

	@Override
	public String getName() {
		this.name="goal";
		return name;
	}
}
