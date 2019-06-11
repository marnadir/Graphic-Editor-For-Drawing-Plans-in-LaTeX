package Dialog;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import State.IState;
import State.InitialState;
import State.InitialStateCanvas;
import container.IContainerState;

public class CreateSoDialog extends IDialogNewState{
	

	IContainerState compCanvas;
	ArrayList<String> listPrec;
	InitialStateCanvas initialStateCanvas;
	IState initialState;
	
	public CreateSoDialog(Composite compCanvas) {
		super(compCanvas.getShell(),SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER | SWT.RESIZE);
		this.compCanvas=(IContainerState) compCanvas;
		
		
	}

	@Override
	public void createContent() {
		super.createContent();
		label.setText("Create a new initial state");
		pack();
	}

	@Override
	public Listener getOkbtnListener() {
		Listener btn = new Listener() {

			@Override
			public void handleEvent(Event event) {
				listPrec=getCond();
				if (initialStateCanvas == null) {
					initialState = new InitialState(listPrec);
					initialStateCanvas = new InitialStateCanvas(compCanvas, SWT.BORDER, initialState);
					initialStateCanvas.addDNDListener();
					initialStateCanvas.getState().generateLatexCodeDomain();
					initialStateCanvas.getState().getLatexCodeDomain();
				}
				if (listPrec != null) {
					initialState.updateConds(listPrec);
					initialStateCanvas.draw();
					setVisible(false);
					
				}

			}
			
			
		};
		return btn;
	}

	
}