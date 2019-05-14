package Dialog;

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

public abstract class IDialog  extends Shell{
	protected Label label;
	protected Composite mainComposite;
	protected Composite compButton;

	int style;
	Button okButton ;
	Button cancelButton;
	
	

	public IDialog(Shell shell,int style) {
		super(shell, style);
		this.style=style;
		this.createDialog();
		
	}
	
	

	public void createDialog() {
		
//		okButton = new Button(this, SWT.PUSH);
//		okButton.setText("&OK");
//		okButton.addListener(SWT.Selection, getOkbtnListener());
//		
//		cancelButton = new Button(this, SWT.PUSH);
//		cancelButton.setText("&Cancel");
//		cancelButton.addListener(SWT.Selection, getCancListener());
//
//		this.label = new Label (this, SWT.NONE);
//		this.composite=new Composite(this, SWT.RESIZE| SWT.BORDER);
//		
//		FormLayout form = new FormLayout ();
//		form.marginWidth = form.marginHeight = 8;
//		setLayout (form);
//		
//		FormData compositeData = new FormData ();
//		compositeData.top = new FormAttachment(label, 8);
//		composite.setLayoutData(compositeData);
//
//		FormData okData = new FormData();
//		okData.top = new FormAttachment(composite, 8);
//		okButton.setLayoutData(okData);
//
//		FormData cancelData = new FormData();
//		cancelData.left = new FormAttachment(okButton, 8);
//		cancelData.top = new FormAttachment(okButton, 0, SWT.TOP);
//		cancelButton.setLayoutData(cancelData);
//		setDefaultButton(okButton);
//		pack();
//		Rectangle bounds = getParent().getBounds();
//		Rectangle rect = getBounds();
//		int x = bounds.x + (bounds.width - rect.width) / 3;
//		int y = bounds.y + (bounds.height - rect.height) / 3;
//		setLocation(x,y);
//		open();
		
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
