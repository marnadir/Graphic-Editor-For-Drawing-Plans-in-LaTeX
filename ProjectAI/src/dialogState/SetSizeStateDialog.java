package dialogState;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import dialog.IDialog;
import so_goalState.IState;
/**
 * Dialog which allows to edit the size of an initial/goal state(preconditions/effects, width and height).
 * @author nadir
 * */
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
		Label empty = new Label(mainComposite, SWT.ALL);

		Label height = new Label(mainComposite, SWT.ALL);
		height.setText("Box-width in cm: ");
		textHeight = new Text(mainComposite, SWT.BORDER);
		textHeight.setText(state.getWidInCm());

		textWidth.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		textHeight.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		empty = new Label(mainComposite, SWT.ALL);

		if (state.isShownCond()) {
			Label lPrec = new Label(mainComposite, SWT.ALL);
			lPrec.setText("Conds-lenght in cm: ");
			textConds = new Text(mainComposite, SWT.BORDER);
			textConds.setText(state.getLengthCondInCm());

		} else {
			Label lWidth = new Label(mainComposite, SWT.ALL);
			lWidth.setText("Conds-lenght in cm: ");
			textConds = new Text(mainComposite, SWT.BORDER);
			textConds.setText((state.getStandardLengthInCm()));

		}

		textConds.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
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
	    Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}

	
	
}
