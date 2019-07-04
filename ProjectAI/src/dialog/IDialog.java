package dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

/** 
 *super abstract class which is used as base for the creating of all dialogs of the tool 
 *
 **/

public abstract class IDialog  extends Shell{
	protected Label label;
	protected Composite mainComposite;
	protected Composite compButton;

	int style;
	protected Button okButton ;
	Button cancelButton;
	
	

	public IDialog(Shell shell,int style) {
		super(shell, style);
		this.style=style;
		this.createDialog();
		
	}
	
	public void createDialog() {

		
		setLayout(new GridLayout(1, false));
		
		Composite COMP=new Composite(this, SWT.BORDER);
		COMP.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		COMP.setLayout(new GridLayout(1, false));

	
	

		this.label = new Label (COMP, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		
		
		this.mainComposite=new Composite(COMP, SWT.RESIZE| SWT.BORDER);
		mainComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		mainComposite.setLayout(new GridLayout(1, false));
		


		this.compButton=new Composite(COMP, SWT.ALL);
		compButton.setLayout(new GridLayout(2, false));

		
		
		okButton = new Button(compButton, SWT.PUSH);
		okButton.setText("&OK");
		okButton.addListener(SWT.Selection, getOkbtnListener());
		
		cancelButton = new Button(compButton, SWT.PUSH);
		cancelButton.setText("&Cancel");
		cancelButton.addListener(SWT.Selection, getCancListener());
		setDefaultButton(okButton);
		//pack();

		
		Rectangle bounds = getParent().getBounds();
		Rectangle rect = getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 3;
		int y = bounds.y + (bounds.height - rect.height) / 3;
		
		setLocation(x,y);

		open();
		
	}
	
	public abstract void createContent();	

	

	public Button getOkButton() {
		return okButton;
	}

	public Button getCancelButton() {
		return cancelButton;
	}


	
	public Listener getCancListener() {
		Listener buttonListener = new Listener() {

			@Override
			public void handleEvent(Event event) {
				dispose();
			}
		};

		return buttonListener;

	}
	
	public abstract Listener getOkbtnListener() ;
	
	@Override
	protected void checkSubclass() {
		
	}
}
