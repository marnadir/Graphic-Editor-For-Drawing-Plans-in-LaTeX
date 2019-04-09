package State;

import java.util.ArrayList;

public class InitialState extends IState {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InitialState(ArrayList<String> conds) {
		super(conds);
		this.name="start";
	}
	
	@Override
	public String getName() {
		this.name="start";
		return name;
	}

}
