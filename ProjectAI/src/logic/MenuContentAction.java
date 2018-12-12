package logic;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;

public class MenuContentAction implements MenuDetectListener{

	CanvasAction canvas;
	
	public MenuContentAction(CanvasAction canvas) {
		this.canvas=canvas;
	}
	
	
	@Override
	public void menuDetected(MenuDetectEvent e) {
		Menu m = new Menu(canvas);
		canvas.setMenu(m);

		MenuItem c = new MenuItem(m, SWT.ALL);
		c.setText("Clear");
		c.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				canvas.clearDisplay();
			}
		});

		MenuItem showC = new MenuItem(m, SWT.ALL);
		showC.setText("Show/Hide Cond...");
		showC.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				canvas.negateIsShownCond();
				canvas.redraw();

			}
		});

		MenuItem showN = new MenuItem(m, SWT.ALL);
		showN.setText("Show/Hide Name..");
		showN.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				canvas.negateIsShownName();
				canvas.redraw();

			}
		});

		MenuItem setSize = new MenuItem(m, SWT.CASCADE);
		setSize.setText("Set Size...");

		Menu subMenu = new Menu(m);
		setSize.setMenu(subMenu);

	
		
		
		MenuItem boxSize = new MenuItem(subMenu, SWT.ALL);
		boxSize.setText("Size Box");
		boxSize.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				IDialog dialog = new IDialog(boxSize.getParent().getShell()) {

					Text textWid;
					Text textHei;

					@Override
					public Listener getOkbtnListener() {
						return new Listener() {

							@Override
							public void handleEvent(Event event) {
								canvas.setWidthRect(Integer.parseInt(textWid.getText()));
								canvas.setHeightRect(Integer.parseInt(textHei.getText()));
								getDialog().setVisible(false);

							}
						};
					}

					@Override
					public void createContent() {
						this.getLabel().setText("set the Box-size: " + canvas.getAction().getName());
						this.getLabel().pack();
						Composite c = getComposite();
						c.setLayout(new GridLayout(2, false));

						Label lWidth = new Label(c, SWT.ALL);
						lWidth.setText("Width: ");
						textWid = new Text(c, SWT.BORDER);
						textWid.setText(Integer.toString(canvas.getWidthRect()));
						textWid.setLayoutData(new GridData(40, 20));


						Label lHeight = new Label(c, SWT.ALL);
						lHeight.setText("Height: ");
						textHei = new Text(c, SWT.BORDER);
						textHei.setText(Integer.toString(canvas.getHeightRect()));
						textHei.setLayoutData(new GridData(40, 20));

						Label info = new Label(c, SWT.BORDER);
						info.setText("the default size is: " + canvas.getWidthRect()+ "x" + canvas.getHeightRect());
						GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
						gridData.horizontalSpan = 2;
						info.setLayoutData(gridData);
						this.getDialog().pack();

					}
				};
				dialog.createContent();
			}
		});
		
		
		MenuItem precSize = new MenuItem(subMenu, SWT.ALL);
		precSize.setText("Size Precondition lines");
		precSize.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				IDialog dialog = new IDialog(boxSize.getParent().getShell()) {

					Text textWid;

					@Override
					public Listener getOkbtnListener() {
						return new Listener() {

							@Override
							public void handleEvent(Event event) {
								
								if (canvas.isShownCond()) {
									canvas.setDefaultValuePrecLenght(false);
									canvas.setLengthPrec(Integer.parseInt(textWid.getText()));
								} else {
									canvas.setStandardLengthPrec(Integer.parseInt(textWid.getText()));
								}
								
								getDialog().setVisible(false);

							}
						};
					}

					@Override
					public void createContent() {
						this.getLabel().setText("set the PrecLine-size of the action: " + canvas.getAction().getName());
						this.getLabel().pack();
						Composite c = getComposite();
						c.setLayout(new GridLayout(2, false));

						if(canvas.isShownCond()) {
							Label lWidth = new Label(c, SWT.ALL);
							lWidth.setText("Lenght: ");
							textWid = new Text(c, SWT.BORDER);
							textWid.setText(Integer.toString(canvas.getLengthPrec()));
							Label info = new Label(c, SWT.BORDER);
							info.setText("the minimum lenght is: " + canvas.getLengthPrec());	
							GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
							gridData.horizontalSpan = 2;
							info.setLayoutData(gridData);
						}else {
							Label lWidth = new Label(c, SWT.ALL);
							lWidth.setText("Lenght: ");
							textWid = new Text(c, SWT.BORDER);
							textWid.setText(Integer.toString(canvas.getStandardLengthPrec()));
							Label info = new Label(c, SWT.BORDER);
							info.setText("the default lenght is: " + 35);	
							GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
							gridData.horizontalSpan = 2;
							info.setLayoutData(gridData);
									
						}
						
						
						this.getDialog().pack();
						
						

					}
				};
				dialog.createContent();
			}
		});
		

		MenuItem effSize = new MenuItem(subMenu, SWT.ALL);
		effSize.setText("Size Effect lines");
		effSize.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				IDialog dialog = new IDialog(boxSize.getParent().getShell()) {

					Text textWid;

					@Override
					public Listener getOkbtnListener() {
						return new Listener() {

							@Override
							public void handleEvent(Event event) {
								
								if (canvas.isShownCond()) {
									canvas.setDefaultValueEffLenght(false);
									canvas.setLengthEff(Integer.parseInt(textWid.getText()));
								} else {
									canvas.setStandardLengthEff(Integer.parseInt(textWid.getText()));

								}
								
								getDialog().setVisible(false);

							}
						};
					}

					@Override
					public void createContent() {
						this.getLabel().setText("set the EffectLine-size of the action: " + canvas.getAction().getName());
						this.getLabel().pack();
						Composite c = getComposite();
						c.setLayout(new GridLayout(2, false));

						if(canvas.isShownCond()) {
							Label lWidth = new Label(c, SWT.ALL);
							lWidth.setText("Lenght: ");
							textWid = new Text(c, SWT.BORDER);
							textWid.setText(Integer.toString(canvas.getLengthEff()));
							Label info = new Label(c, SWT.BORDER);
							info.setText("the minimum lenght is: " + canvas.getLengthEff());	
							GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
							gridData.horizontalSpan = 2;
							info.setLayoutData(gridData);
						}else {
							Label lWidth = new Label(c, SWT.ALL);
							lWidth.setText("Lenght: ");
							textWid = new Text(c, SWT.BORDER);
							textWid.setText(Integer.toString(canvas.getStandardLengthEff()));
							Label info = new Label(c, SWT.BORDER);
							info.setText("the default lenght is: " + 30);	
							GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
							gridData.horizontalSpan = 2;
							info.setLayoutData(gridData);
									
						}
						
						
						this.getDialog().pack();
						
						

					}
				};
				dialog.createContent();
			}
		});
		
	}

}
