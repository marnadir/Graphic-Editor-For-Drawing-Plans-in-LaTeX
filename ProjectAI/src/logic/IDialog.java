package logic;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public abstract class IDialog {
	private Shell shell;
	private Shell dialog;
	private Label label;
	private Composite composite;

	public IDialog(Shell shell) {
		this.shell = shell;
		this.createDialog();
		
	}

	public void createDialog() {
		dialog = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER);

		Button okButton = new Button(dialog, SWT.PUSH);
		okButton.setText("&OK");
		okButton.addListener(SWT.Selection, getOkbtnListener());
		
		Button cancelButton = new Button(dialog, SWT.PUSH);
		cancelButton.setText("&Cancel");
		cancelButton.addListener(SWT.Selection, getCancListener());

		this.label = new Label (dialog, SWT.NONE);
		this.composite=new Composite(dialog, SWT.ALL);
		
		FormLayout form = new FormLayout ();
		form.marginWidth = form.marginHeight = 8;
		dialog.setLayout (form);
		
		FormData compositeData = new FormData ();
		compositeData.top = new FormAttachment (label, 8);
		composite.setLayoutData (compositeData);
		
		FormData okData = new FormData ();
		okData.top = new FormAttachment (composite, 8);
		okButton.setLayoutData (okData);
		
		FormData cancelData = new FormData ();
		cancelData.left = new FormAttachment (okButton, 8);
		cancelData.top = new FormAttachment (okButton, 0, SWT.TOP);
		cancelButton.setLayoutData (cancelData);

		dialog.setDefaultButton (okButton);
		dialog.pack ();
		dialog.open ();
	}
	
	public abstract void createContent();	
	
	public Label getLabel() {
		return this.label;
	}
	
	public Shell getDialog() {
		return this.dialog;
	}
	
	
	
	public Composite getComposite() {
		return this.composite;
	}
	
	public Listener getCancListener() {
		Listener buttonListener = new Listener() {

			@Override
			public void handleEvent(Event event) {
				dialog.close();
			}
		};

		return buttonListener;

	}
	
	public abstract Listener getOkbtnListener() ;
}
