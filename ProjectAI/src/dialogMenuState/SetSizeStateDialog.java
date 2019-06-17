package dialogMenuState;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import Action.GlobalValue;
import Dialog.IDialog;
import State.IState;

public class SetSizeStateDialog extends IDialog {

	Text textWidth;
	Text textHeight;
	IState state;
	Text textConds;

	
	
	public SetSizeStateDialog(Shell shell, int style) {
		super(shell, style);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createContent() {
		this.label.setText("Set the size of the line");
		this.label.pack();
		mainComposite.setLayout(new GridLayout(3, false));
		Label width = new Label(mainComposite, SWT.ALL);
		width.setText("Box-height in cm: ");
		textWidth = new Text(mainComposite, SWT.BORDER);
		textWidth.setText(state.getHeiInCm());
		Label empty=new Label(mainComposite, SWT.ALL);
		
		Label height = new Label(mainComposite, SWT.ALL);
		height.setText("Box-width in cm: ");
		textHeight = new Text(mainComposite, SWT.BORDER);
		textHeight.setText(state.getWidInCm());
		
		textWidth.setLayoutData(new GridData(SWT.FILL, SWT.FILL,true,true));
		textHeight.setLayoutData(new GridData(SWT.FILL, SWT.FILL,true,true));
		empty=new Label(mainComposite, SWT.ALL);

		
		Button btnCond;

		if (state.isShownCond()) {
			Label lPrec = new Label(mainComposite, SWT.ALL);
			lPrec.setText("Conds-lenght in cm: ");
			textConds = new Text(mainComposite, SWT.BORDER);
			textConds.setText(state.getLengthCondInCm());
			btnCond = new Button(mainComposite, SWT.CHECK);
			btnCond.setText("Global");
			btnCond.setVisible(false);
		} else {
			Label lWidth = new Label(mainComposite, SWT.ALL);
			lWidth.setText("Conds-lenght in cm: ");
			textConds = new Text(mainComposite, SWT.BORDER);
			textConds.setText((state.getStandardLengthInCm()));
			btnCond = new Button(mainComposite, SWT.CHECK);
			btnCond.setText("Global");
			btnCond.setVisible(false);

		}

		if (state.isShownCond()) {
			if (GlobalValue.isLengthsOfConds) {
				btnCond.setVisible(true);
				if (state.isGlobalCond()) {
					btnCond.setSelection(true);
					textConds.setEditable(false);
				}

			}
		} else {
			btnCond.setVisible(true);
			if (state.isGlobalEmpty()) {
				btnCond.setSelection(true);
				textConds.setEditable(false);

			}
		}

		btnCond.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (btnCond.getSelection()) {
					if (state.isShownCond()) {
						textConds.setText(GlobalValue.lengthsOfConds);
						textConds.setEditable(false);
						state.setGlobalCond(true);
					} else {
						textConds.setText(GlobalValue.lengthsOfEmptyTasks);
						textConds.setEditable(false);
						state.setGlobalEmpty(true);

					}

				} else {
					if (state.isShownCond()) {
						textConds.setEditable(true);
						state.setGlobalCond(false);
					} else {
						textConds.setEditable(true);
						state.setGlobalEmpty(false);
					}

				}

			}
		});
		textConds.setLayoutData(new GridData(SWT.FILL, SWT.FILL,true,true));
		pack();
		
	}

	@Override
	public Listener getOkbtnListener() {
		Listener l;
		l=new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				
					if (isNumeric(textWidth.getText())&&isNumeric(textHeight.getText())) {
						state.setHeigthFromCm(Double.parseDouble(textWidth.getText()));
						state.setWidFromCm(Double.parseDouble(textHeight.getText()));
						setVisible(false);
					}
					
					if (state.isShownCond()) {
						state.setDefaultValue(false);
						if (isNumeric(textConds.getText())) {
							state.setLengthFromCm(Double.parseDouble(textConds.getText()));
							setVisible(false);

						}
					} else {
						if (isNumeric(textConds.getText())) {
							state.setStandardLengthFromCm(Double.parseDouble(textConds.getText()));
							setVisible(false);

						}
					}
			}
		
		};
		return l;
	}

	public void setState(IState state) {
		this.state = state;
	}

	
	private boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}

	
	
}
