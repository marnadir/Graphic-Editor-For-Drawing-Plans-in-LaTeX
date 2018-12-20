package GUI;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import State.IState;
import State.InitialStateCanvas;
import logic.IDialogNewState;

public class CreateSoDialog extends IDialogNewState{
	

	Composite compCanvas;
	ArrayList<String> listPrec;
	Shell dialog=this.getDialog();
	Combo CombOption;
	InitialStateCanvas initialStateCanvas;
	IState initialState;
	
	public CreateSoDialog(Composite compCanvas) {
		super(compCanvas.getShell(),SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER);
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
				if(initialStateCanvas == null) {
				initialState=new IState(listPrec);
				 initialStateCanvas=new InitialStateCanvas(compCanvas,SWT.ALL,initialState);
				 initialStateCanvas.addDNDListener();
				}
				if(listPrec != null) {
					initialState.updateConds(listPrec);
					if(listPrec.size()>0) {
						initialStateCanvas.draw();
						dialog.setVisible(false);
					}
				}
				
				
			}
		};
		
		return btn;
	}


	

	public InitialStateCanvas getInitialState() {
		return this.initialStateCanvas;
	}
	
	
	
//	public Shell getDialog() {
//		return dialog;
//	}
	
}