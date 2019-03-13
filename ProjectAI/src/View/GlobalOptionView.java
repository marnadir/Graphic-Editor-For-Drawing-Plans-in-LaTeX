package View;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;


public class GlobalOptionView extends Composite{

	
	Composite compListdetail;
	Text t1;
	
	public GlobalOptionView(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}
	
	public void setLayout() {
		setLayout(new GridLayout(2, true));
	}
	
	public void createContent() {
			
		Label l=new Label(this, SWT.ALL);
		l.setText("enable the details, which you want to used");
		GridData gridData = new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false);
		gridData.horizontalSpan = 2;
	   l.setLayoutData(gridData);
		
		
		compListdetail=new Composite(this, SWT.ALL);
		compListdetail.setLayout(new GridLayout(4, false));
		

		Button bnt1=new Button(compListdetail, SWT.CHECK | SWT.BORDER);
		bnt1.addListener(SWT.Selection, getAddBtnListener(bnt1));
		Label l1=new Label(compListdetail, SWT.ALL);
		l1.setText("Height of Action");
		t1=new Text(compListdetail, SWT.BORDER );
		t1.setEditable(false);
		t1.setText("NONE");
		Button btnOk1=new Button(compListdetail, SWT.ALL);
		btnOk1.setText("ok");
		btnOk1.addListener(SWT.Selection, getOkBtnListener());
		
		


		pack();
	}
	
	private Listener getAddBtnListener(Button b1) {
		Listener l;

		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (b1.getSelection()) {
					t1.setEditable(true);
					//insert the new command in the latex code
				} else {
					t1.setEditable(false);

				}

				t1.pack();
			}
		};

		return l;
	}

	private Listener getOkBtnListener() {
		Listener l;

		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				//update the new value in the latex code
			}
		};

		return l;
	}
	
	
}
