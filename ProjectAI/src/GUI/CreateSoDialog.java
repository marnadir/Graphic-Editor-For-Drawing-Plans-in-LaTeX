package GUI;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
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
	boolean createdSo=false;
	Combo CombOption;
	InitialState initialState;
	
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
				 initialState=new InitialState(listPrec);
				if(listPrec.size()>0) {
					initialState.draw(compCanvas);
					updateCombo();
					
				}
				
				
				
				dialog.close();
			}
		};
		
		return btn;
	}

	public void updateCombo() {
		List<String> possibleOption=new ArrayList<String>();
		possibleOption.add("Change");
		possibleOption.add("Elimanate");
		String[] convertList=possibleOption.toArray(new String[possibleOption.size()]);
		this.CombOption.setItems (convertList);
		this.CombOption.pack();
	}
	
	public void setCombo(Combo combo) {
		this.CombOption=combo;
	}
	
	public void elimaneState() {
		listPrec.clear();
	}
	
	public InitialState getInitialState() {
		return this.initialState;
	}
	
	
	
}