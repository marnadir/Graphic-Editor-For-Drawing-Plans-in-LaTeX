package newHope;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public abstract class newIDialog  extends Shell{
	protected Label label;
	protected Composite composite;
	protected Composite compButton;
	int style;
	Button okButton ;
	Button cancelButton;
	
	

	public newIDialog(Shell shell,int style) {
		super(shell, style);
		this.style=style;
		this.createDialog();
		
	}
	
	

	public void createDialog() {
		
		Composite COMP=new Composite(this, SWT.ALL);
		COMP.setLayout(new GridLayout(1, false));

	

		this.label = new Label (COMP, SWT.NONE);
		this.composite=new Composite(COMP, SWT.RESIZE| SWT.BORDER);
		this.compButton=new Composite(COMP, SWT.ALL);
		compButton.setLayout(new GridLayout(2, false));

		
		
		okButton = new Button(compButton, SWT.PUSH);
		okButton.setText("&OK");
		okButton.addListener(SWT.Selection, getOkbtnListener());
		
		cancelButton = new Button(compButton, SWT.PUSH);
		cancelButton.setText("&Cancel");
		cancelButton.addListener(SWT.Selection, getCancListener());
		setDefaultButton(okButton);
		pack();


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

