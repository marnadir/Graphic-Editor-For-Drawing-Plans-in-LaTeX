package State;

import java.util.ArrayList;

public class GoalState extends IState {

	public GoalState(ArrayList<String> conds) {
		super(conds);
		this.name="goal";
	}

}
