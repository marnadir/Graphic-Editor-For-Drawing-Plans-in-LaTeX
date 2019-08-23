package dialog.action;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import Action.ICanvas;
import dialog.IDialog;
/**
 * Dialog which allows to edit the size of an action(box-height,box-width,preconditions and effects).
 * @author nadir
 * */
public class SetSizeActionDialog extends IDialog {

	
	ICanvas canvas;
	Text textWid;
	Text textHei;
	Text textPrec;
	Text textEff;


	public SetSizeActionDialog(Shell shell, int style,ICanvas canvas) {
		super(shell, style);
		this.canvas=canvas;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createContent() {
		label.setText("Set the size of the action: " + canvas.getAction().getName());
		label.pack();
		mainComposite.setLayout(new GridLayout(2, false));

		Label lWidth = new Label(mainComposite, SWT.ALL);
		lWidth.setText("Width in cm: ");

		textWid = new Text(mainComposite, SWT.BORDER);
		textWid.setText(canvas.getAction().getWidthRectInCm());
		textWid.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		

		Label lHeight = new Label(mainComposite, SWT.ALL);
		lHeight.setText("Height in cm: ");
		textHei = new Text(mainComposite, SWT.BORDER);
		textHei.setText(canvas.getAction().getHeightRectInCm());
		textHei.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

	
		//Size Prec
		
		
		if (canvas.getAction().isShownCond()) {
			Label labPrec = new Label(mainComposite, SWT.ALL);
			labPrec.setText("Prec-lenght in cm: ");
			textPrec = new Text(mainComposite, SWT.BORDER);
			textPrec.setText(canvas.getAction().getLengthPrecInCm());
			textPrec.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		

		} else {
			Label labPrec = new Label(mainComposite, SWT.ALL);
			labPrec.setText("Prec-lenght in cm: ");
			textPrec = new Text(mainComposite, SWT.BORDER);
			textPrec.setText(canvas.getAction().getStandardLengthPrecInCm());
			textPrec.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		
		}
		

		
		//Size Eff
		
		
		if (canvas.getAction().isShownCond()) {
			Label labEff = new Label(mainComposite, SWT.ALL);
			labEff.setText("Eff-lenght in cm: ");
			textEff = new Text(mainComposite, SWT.BORDER);
			textEff.setText(canvas.getAction().getLengthEffInCm());
			textEff.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		} else {
			Label labEff = new Label(mainComposite, SWT.ALL);
			labEff.setText("Eff-lenght in cm: ");
			textEff = new Text(mainComposite, SWT.BORDER);
			textEff.setText(canvas.getAction().getStandardLengthEffInCm());
			textEff.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		}
		
	
	
		
		pack();

	}

	@Override
	public Listener getOkbtnListener() {
		return new Listener() {

			@Override
			public void handleEvent(Event event) {
				
				//Size box
				if(isNumeric(textWid.getText())&& isNumeric(textHei.getText())) {
					canvas.getAction().setWidthRectFromCm(Double.parseDouble(textWid.getText()));
					canvas.getAction().setHeightRectFromCm(Double.parseDouble(textHei.getText()));
					canvas.getAction().setDefaultValueWid(false);
					canvas.getAction().setDefaultValueHeig(false);

					setVisible(false);
					canvas.resizeParent();
				}
			
				//Size Prec
				if(isNumeric(textPrec.getText())) {
					if (canvas.getAction().isShownCond()) {
						canvas.getAction().setDefaultValuePrecLenght(false);
						canvas.getAction().setLengthPrecFromCm(Double.parseDouble(textPrec.getText()));
					} else {
						canvas.getAction().setStandardLengthPrecFromCm(Double.parseDouble(textPrec.getText()));
					}
					canvas.resizeParent();
					setVisible(false);
				}
				
				//Size Eff
				if (isNumeric(textEff.getText())) {
					if (canvas.getAction().isShownCond()) {
						canvas.getAction().setDefaultValueEffLenght(false);
						canvas.getAction()
								.setLengthEffFromCm(Double.parseDouble(textEff.getText()));
					} else {
						canvas.getAction()
								.setStandardLengthEffFromCm(Double.parseDouble(textEff.getText()));

					}
					canvas.resizeParent();

					setVisible(false);
				}

			}
		};
	}
	
	private boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public ICanvas getCanvas() {
		return canvas;
	}

	public void setCanvas(ICanvas canvas) {
		this.canvas = canvas;
	}
	

}
