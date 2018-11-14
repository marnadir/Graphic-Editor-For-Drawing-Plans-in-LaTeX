package GUI;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import logic.IDialogNewState;
import logic.InitialState;

public class CreateSoDialog extends IDialogNewState{
	
//	ArrayList<String> listPrec=this.getCond();

	Composite compCanvas;
	ArrayList<String> listPrec;
	Shell dialog=this.getDialog();
	
	public CreateSoDialog(Composite compCanvas) {
		super(compCanvas.getShell());
		this.compCanvas=compCanvas;
		listPrec=this.getCond();
		
	}

	@Override
	public void createContent() {
		super.createContent();
		this.getLabel().setText("Create a new initial state");
		
	}
	
	@Override
	public Listener getOkbtnListener() {
		Listener btn=new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				InitialState initialState=new InitialState(listPrec);
				initialState.draw(compCanvas);
				
				dialog.close();
			}
		};
		
		return btn;
	}

}