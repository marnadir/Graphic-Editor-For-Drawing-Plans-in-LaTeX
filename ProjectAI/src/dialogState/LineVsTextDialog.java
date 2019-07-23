package dialogState;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import dialog.IDialog;
import so_goalState.IState;
/**
 * Dialog which allows to set/hide the label-text for the initial/goal state.
 * @author nadir
 * */
public class LineVsTextDialog extends IDialog {

	
	Composite textButton;
	Composite compoButton;
	Button btnText;
	Text textState;
	Combo comboColor;
	IState state;
	
	
	public LineVsTextDialog(Shell shell, int style) {
		super(shell, style);
		// TODO Auto-generated constructor stub
	}


	public void setState(IState state) {
		this.state = state;
	}

	@Override
	public Listener getOkbtnListener() {
		Listener l;
		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (btnText.getSelection()) {
					state.setText(textState.getText());
					state.setColor(comboColor.getText());
					state.setIsText(true);
				} else {
					state.setIsText(false);
				}

				dispose();
			}
		};
		return l;
	}

	@Override
	public void createContent() {
		label.setText("Line vs Text");
		//this.label.pack();


		compoButton = new Composite(mainComposite, SWT.NONE );
		compoButton.setLayout(new RowLayout(SWT.HORIZONTAL));
		compoButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));


		btnText = new Button(compoButton, SWT.RADIO);
		btnText.setText("Text");

		Button btnLine = new Button(compoButton, SWT.RADIO);
		btnLine.setText("Line");

		
		textButton = new Composite(mainComposite, SWT.NONE );
		textButton.setLayout(new GridLayout(2, false));
		textButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		
		
		
		Label l = new Label(textButton, SWT.ALL);
		l.setText("Name of state:");

		textState = new Text(textButton, SWT.BORDER| SWT.RESIZE);
		textState.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		textState.setText("state");
	
		Label lbColor=new Label(textButton, SWT.ALL);
		lbColor.setText("Color:");
		comboColor=new Combo(textButton, SWT.DROP_DOWN | SWT.READ_ONLY);
		String[] items = new String[] { "None", "Cyan","Yellow" };
		comboColor.setItems(items);
		comboColor.select(0);
		
		
		
		if(state.isText()) {
			btnText.setSelection(true);	
			textState.setText(state.getText());
			if(state.getColor().equalsIgnoreCase("None")) {
				comboColor.select(0);
			}else if(state.getColor().equalsIgnoreCase("Cyan")) {
				comboColor.select(1);
			}else {
				comboColor.select(2);
			}
			textState.pack();
			
			
		}else {
			
			btnLine.setSelection(true);
			textButton.setVisible(false);
			

			
		}


		btnText.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				textButton.setVisible(true);

			}
		});

		btnLine.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				textButton.setVisible(false);

			}
		});

		
		mainComposite.requestLayout();

		setSize(300,250);

		

	}


}
