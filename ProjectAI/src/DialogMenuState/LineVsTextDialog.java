package DialogMenuState;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import Dialog.IDialog;
import State.IState;

public class LineVsTextDialog extends IDialog {

	
	Composite compButton;
	Composite textButton;
	Button btnText;
	Text text;
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
					state.setText(text.getText());
					
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
		this.label.pack();
		composite.setLayout(new GridLayout(1, false));
		compButton = new Composite(composite, SWT.ALL);
		compButton.setLayout(new RowLayout(SWT.HORIZONTAL));

		btnText = new Button(compButton, SWT.RADIO);
		btnText.setText("Text");

		Button btnLine = new Button(compButton, SWT.RADIO);
		btnLine.setText("Line");

		textButton = new Composite(composite, SWT.ALL);
		textButton.setLayout(new GridLayout(2, false));

		Label l = new Label(textButton, SWT.ALL);
		l.setText("Name of state:");

		text = new Text(textButton, SWT.BORDER);
		text.setText("state");
		text.setSize(20, 10);
	
		btnText.setSelection(true);


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

		pack();

	}


}
