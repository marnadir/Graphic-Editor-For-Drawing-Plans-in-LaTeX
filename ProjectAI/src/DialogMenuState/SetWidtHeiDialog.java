package DialogMenuState;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import Dialog.IDialog;
import State.IState;

public class SetWidtHeiDialog extends IDialog {

	Text textWidth;
	Text textHeight;
	IState state;
	
	public SetWidtHeiDialog(Shell shell, int style) {
		super(shell, style);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createContent() {
		this.label.setText("set the Conditions-size  ");
		this.label.pack();
		composite.setLayout(new GridLayout(2, false));
		Button btnCond;	
		Label width = new Label(composite, SWT.ALL);
		width.setText("Lenght in cm: ");
		textWidth = new Text(composite, SWT.BORDER);
		textWidth.setText(state.getWidthInCm());
	
		Label height = new Label(composite, SWT.ALL);
		height.setText("Lenght in cm: ");
		textHeight = new Text(composite, SWT.BORDER);
		textHeight.setText(state.getHeiInCm());
		
		pack();
		
	}

	@Override
	public Listener getOkbtnListener() {
		Listener l;
		l=new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				
					if (isNumeric(textWidth.getText())&&isNumeric(textHeight.getText())) {
						state.setWidFromCm(Double.parseDouble(textWidth.getText()));
						state.setHeiFromCm(Double.parseDouble(textHeight.getText()));
						setVisible(false);
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
