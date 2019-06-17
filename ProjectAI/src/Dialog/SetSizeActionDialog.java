package Dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import Action.GlobalValue;
import Action.ICanvas;

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
		mainComposite.setLayout(new GridLayout(3, false));

		Label lWidth = new Label(mainComposite, SWT.ALL);
		lWidth.setText("Width in cm: ");

		textWid = new Text(mainComposite, SWT.BORDER);
		textWid.setText(canvas.getAction().getWidthRectInCm());
		textWid.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Button btnWidth = new Button(mainComposite, SWT.CHECK);
		btnWidth.setText("Global");
		btnWidth.setVisible(false);

		if (GlobalValue.isWidthOfAction) {
			btnWidth.setVisible(true);
			if (canvas.getAction().isGlobalWid()) {
				btnWidth.setSelection(true);
				textWid.setEditable(false);

			}

		}

		btnWidth.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (btnWidth.getSelection()) {
					textWid.setText(GlobalValue.widthOfAction);
					textWid.setEditable(false);
					canvas.getAction().setGlobalWid(true);
				} else {
					canvas.getAction().setGlobalWid(false);
					textWid.setEditable(true);

				}

			}
		});

		Label lHeight = new Label(mainComposite, SWT.ALL);
		lHeight.setText("Height in cm: ");
		textHei = new Text(mainComposite, SWT.BORDER);
		textHei.setText(canvas.getAction().getHeightRectInCm());
		textHei.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Button btnHeight = new Button(mainComposite, SWT.CHECK);
		btnHeight.setText("Global");
		btnHeight.setVisible(false);

		if (GlobalValue.isHeightOfAction) {
			btnHeight.setVisible(true);
			if (canvas.getAction().isGlobalHeight()) {
				btnHeight.setSelection(true);
				textHei.setEditable(false);

			}

		}

		btnHeight.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (btnHeight.getSelection()) {
					textHei.setText(GlobalValue.heightOfAction);
					textHei.setEditable(false);
					canvas.getAction().setGlobalHeight(true);
				} else {
					textHei.setEditable(true);
					canvas.getAction().setGlobalHeight(false);

				}

			}
		});

		//Size Prec
		
		Button btnPrec;
		
		if (canvas.getAction().isShownCond()) {
			Label labPrec = new Label(mainComposite, SWT.ALL);
			labPrec.setText("Prec-lenght in cm: ");
			textPrec = new Text(mainComposite, SWT.BORDER);
			textPrec.setText(canvas.getAction().getLengthPrecInCm());
			textPrec.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

			btnPrec=new Button(mainComposite, SWT.CHECK);
			btnPrec.setText("Global");
			btnPrec.setVisible(false);

		} else {
			Label labPrec = new Label(mainComposite, SWT.ALL);
			labPrec.setText("Prec-lenght in cm: ");
			textPrec = new Text(mainComposite, SWT.BORDER);
			textPrec.setText(canvas.getAction().getStandardLengthPrecInCm());
			textPrec.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

			btnPrec=new Button(mainComposite, SWT.CHECK);
			btnPrec.setText("Global");
			btnPrec.setVisible(false);

		}
		
		if(canvas.getAction().isShownCond()) {
			if(GlobalValue.isLengthsOfPrecs) {
				btnPrec.setVisible(true);
				if(canvas.getAction().isGlobalPrec()) {
					btnPrec.setSelection(true);
					textPrec.setEditable(false);

				}
				
			}
		}else {
			btnPrec.setVisible(true);
			if(canvas.getAction().isGlobalEmptyPrec()) {
				btnPrec.setSelection(true);
				textPrec.setEditable(false);

			}
		}
		

		btnPrec.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				if(btnPrec.getSelection()) {
					if(canvas.getAction().isShownCond()) {
						textPrec.setText(GlobalValue.lengthsOfPrecs);
						textPrec.setEditable(false);
						canvas.getAction().setGlobalPrec(true);
					}else {
						textPrec.setText(GlobalValue.lengthsOfEmptyTasks);
						textPrec.setEditable(false);
						canvas.getAction().setGlobalEmptyPrec(true);;
						
					}
					
				}else {
					if(canvas.getAction().isShownCond()) {
						textPrec.setEditable(true);
						canvas.getAction().setGlobalPrec(false);
					}else {
						textPrec.setEditable(true);
						canvas.getAction().setGlobalEmptyPrec(false);
					}
					

				}
			}
		});
		
		
		//Size Eff
		

		Button btnEff;
		
		if (canvas.getAction().isShownCond()) {
			Label labEff = new Label(mainComposite, SWT.ALL);
			labEff.setText("Eff-lenght in cm: ");
			textEff = new Text(mainComposite, SWT.BORDER);
			textEff.setText(canvas.getAction().getLengthEffInCm());
			textEff.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

			btnEff=new Button(mainComposite, SWT.CHECK);
			btnEff.setText("Global");
			btnEff.setVisible(false);
		} else {
			Label labEff = new Label(mainComposite, SWT.ALL);
			labEff.setText("Eff-lenght in cm: ");
			textEff = new Text(mainComposite, SWT.BORDER);
			textEff.setText(canvas.getAction().getStandardLengthEffInCm());
			textEff.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

			btnEff=new Button(mainComposite, SWT.CHECK);
			btnEff.setText("Global");
			btnEff.setVisible(false);
		}
		
		if(canvas.getAction().isShownCond()) {
			if(GlobalValue.isLengthsOfEffs) {
				btnEff.setVisible(true);
				if(canvas.getAction().isGlobalEff()) {
					btnEff.setSelection(true);								
					textEff.setEditable(false);

				}
				
			}
		}else {
			btnEff.setVisible(true);
			if(canvas.getAction().isGlobalEmptyEff()) {
				btnEff.setSelection(true);
				textEff.setEditable(false);

			}
		}
		
		
		btnEff.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				if(btnEff.getSelection()) {
					if(canvas.getAction().isShownCond()) {
						textEff.setText(GlobalValue.lengthsOfEffs);
						textEff.setEditable(false);
						canvas.getAction().setGlobalEff(true);	
					}else {
						textEff.setText(GlobalValue.lengthsOfEmptyTasks);
						textEff.setEditable(false);
						canvas.getAction().setGlobalEmptyEff(true);;
					}
				}else {
					if(canvas.getAction().isShownCond()) {
						
						textEff.setEditable(true);
						canvas.getAction().setGlobalEff(false);
					}else {
						textEff.setEditable(true);
						canvas.getAction().setGlobalEmptyEff(true);
					}
				


				}
				
			}
		});
		
		
		
		
		
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
