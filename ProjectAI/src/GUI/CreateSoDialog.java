package GUI;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import logic.IDialogNewState;
import logic.InitialState;

public class CreateSoDialog extends IDialogNewState{
	
//	ArrayList<String> listPrec=this.getCond();

	
	public CreateSoDialog(Shell shell) {
		super(shell);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createContent() {
		super.createContent();
		this.getLabel().setText("Create a new initial state");
	}
	
	@Override
	public Listener getOkbtnListener() {
		
		ArrayList<String> listPrec=this.getCond();

		
		Listener btn=new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				InitialState initialstate=new InitialState(listPrec);
				
			}
		};
		
		return btn;
	}

}