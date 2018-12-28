package State;

import java.util.ArrayList;

public class InitialState extends IState {

	public InitialState(ArrayList<String> conds) {
		super(conds);
		this.name="InitialState";
	}

}
