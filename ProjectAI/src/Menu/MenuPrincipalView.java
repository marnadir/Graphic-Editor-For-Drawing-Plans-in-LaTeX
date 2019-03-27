package Menu;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.text.PlainView;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;

import Action.Action;
import Action.Node;
import Dialog.IDialog;
import Dialog.LoadDomainFileLocationDialog;
import Dialog.LoadPlanDialog;
import Dialog.SaveDomainFileLocationDialog;
import LaTex.LaTexGeneratorPlan;
import LaTex.LaTexGeneratorStatePlan;
import PlanPart.PlanContent;
import PlanPart.OrderConstrainCanvas;
import PlanPart.LinkCanvas;
import PlanPart.OrderConstrain;
import View.DomainView;
import View.PlanView;
import command.ExitCommand;




public class MenuPrincipalView extends IMenu{
	
	String fileName;
	File file;
	File directory;
	ArrayList<Action> updateActionListDomain;
	DomainView domainView;
	PlainView plainView;
	File dirLog;
	File dirLatex;
	File dirPlan;

	public MenuPrincipalView(Decorations parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}

	public void fillMenu(DomainView domainView,PlanView planView) {
		
		this.domainView=domainView;
		
		MenuItem fileItem = createItem("&File", SWT.CASCADE);
		IMenu menuFile = new IMenu(getShell(), SWT.DROP_DOWN);
		fileItem.setMenu(menuFile);

		MenuItem option = createItem("&Option", SWT.CASCADE);
		IMenu menuOption = new IMenu(getShell(), SWT.DROP_DOWN);
		option.setMenu(menuOption);

		MenuItem storeStateDomain = new MenuItem(menuFile, SWT.PUSH);
		storeStateDomain.setText("&Save Domain\tCtrl+S");
		storeStateDomain.setAccelerator( SWT.CONTROL + 'S');


		MenuItem restoreStateDomain = new MenuItem(menuFile, SWT.PUSH);
		restoreStateDomain.setText("&Load Domain\tCtrl+R");
		restoreStateDomain.setAccelerator( SWT.CONTROL + 'R');


		
		MenuItem loadPlan = new MenuItem(menuFile, SWT.PUSH);
		loadPlan.setText("&Load Plan\t");

		MenuItem exitItem = new MenuItem(menuFile, SWT.PUSH);
		exitItem.setText("&Exit");



		MenuItem menuLines = new MenuItem(menuOption, SWT.PUSH);
		menuLines.setText("Create Connection");

		updateActionListDomain=domainView.getTreeAction().getActionList();
		
		Listener listenerExit = new Listener() {
			@Override
			public void handleEvent(Event event) {
				ExitCommand command = new ExitCommand();
				command.execute(getShell(), event);
			}
		};

		
		Listener listenerStoreDomain = new Listener() {

			@Override
			public void handleEvent(Event event) {
				
				SaveDomainFileLocationDialog dialog=new SaveDomainFileLocationDialog(getShell(), SWT.SAVE);
				dialog.setDomainView(domainView);
				dialog.setUpdateActionListDomain(domainView.getTreeAction().getActionList());
				dialog.createContent();
			}
		};

		Listener listenerRestoreDomain = new Listener() {

			@Override
			public void handleEvent(Event event) {
				
				LoadDomainFileLocationDialog dialog=new LoadDomainFileLocationDialog(getShell(), SWT.MULTI);
				dialog.setDomainView(domainView);
				dialog.createContent();
				

			}
		};

		

		
		
		Listener listenerLink = new Listener() {
			Composite compButton;
			private Composite compPoint;

			private LinkCanvas link;
			private OrderConstrainCanvas constrain;
			private OrderConstrain orderCond;
			Label l1 = null;
			Label l2 = null;
			String c1 = "....";
			String c2 = "....";
			IDialog dialog = null;

			Button archBtn;

			public Listener getArchBtnList() {

				Listener archBtnList = new Listener() {

					@Override
					public void handleEvent(Event event) {

						orderCond = null;

						l1.setText("First Cond. :" + "Select the point");
						l1.pack();
						l2.setText("Second Cond. :" + "Select the point");
						l2.pack();
						compPoint.pack();
						dialog.pack();
						compPoint.setVisible(true);

						link = new LinkCanvas(planView.getPlan());
						link.addlistener(l1, l2, archBtn);

					}
				};

				return archBtnList;
			}

			public Listener getOrdBtnList() {
				Listener ordBtnList = new Listener() {

					@Override
					public void handleEvent(Event event) {

						link = null;
						c1 = "null";
						c2 = "null";
						l1.setText("ordering of actions");
						l2.setText(c1 + "<" + c2);
						l2.pack();
						compPoint.pack();
						dialog.pack();
						compPoint.setVisible(true);

						Composite comp = new Composite(planView.getPlan(), SWT.ALL);

						// sulla definizione di cio, ce qualcosa che mi turba!!
						comp.setSize(50, 50);
						comp.setLocation(20, 30);

						orderCond = new OrderConstrain(comp);
						orderCond.addlistener(l1, l2);
					}
				};

				return ordBtnList;
			}

			@Override
			public void handleEvent(Event event) {

				dialog = new IDialog(getShell(), SWT.DIALOG_TRIM) {

					@Override
					public Listener getOkbtnListener() {
						Listener l;
						l = new Listener() {

							@Override
							public void handleEvent(Event event) {

								if (link != null) {
									if (!l1.getText().contains("Select the point")
											&& !l2.getText().contains("Select the point")) {
										link.drawLine();
										planView.getPlan().getLink().add(link);
										l1.setText("First Cond. :" + "Select the point");
										l2.setText("Second Cond. :" + "Select the point");
										l1.pack();
										l2.pack();
										link.removeL();
										// link.removelistener(l1, l2,archBtn);

									}
								} else if (orderCond != null) {
									if (!l2.getText().contains("null")) {
										orderCond.setLocationParent();
										Composite parent=orderCond.getParent();
										constrain=new OrderConstrainCanvas(parent, SWT.ALL,orderCond);
										constrain.draw();
										constrain.pack();
										constrain.setSize(parent.getSize().x,parent.getSize().y);
										planView.getPlan().getOrds().add(orderCond);
										c1 = "null";
										c2 = "null";
										l1.setText("ordering of actions");
										l2.setText(c1 + "<" + c2);
										l1.pack();
										l2.pack();
										// orderCond.removelistener(l2);

									}
								}

							}
						};
						return l;
					}

					@Override
					public void dispose() {

//						link.removelistener(l1, l2, archBtn);
//						archBtn.removeListener(SWT.MouseDoubleClick, getArchBtnList());
//						removeListener(SWT.MouseDoubleClick, getArchBtnList());
//						link=null;
//
						super.dispose();

					}

					@Override
					public void createContent() {

						label.setText("Create Connection");
						this.label.pack();
						composite.setLayout(new GridLayout(1, false));
						compButton = new Composite(composite, SWT.ALL);
						compButton.setLayout(new RowLayout(SWT.VERTICAL));

						archBtn = new Button(compButton, SWT.PUSH);
						archBtn.setText("draw arch");

						Button ordBtn = new Button(compButton, SWT.PUSH);
						ordBtn.setText("draw Ord");

						compPoint = new Composite(composite, SWT.ALL);
						compPoint.setLayout(new GridLayout());
						l1 = new Label(compPoint, SWT.ALL);
						l2 = new Label(compPoint, SWT.ALL);
						compPoint.setVisible(false);

						ordBtn.addListener(SWT.Selection, getOrdBtnList());
						archBtn.addListener(SWT.Selection, getArchBtnList());

						
						dialog.pack();

					}

				};

				dialog.createContent();
			}
		};

		Listener loadL = new Listener() {

			@Override
			public void handleEvent(Event event) {
				LoadPlanDialog dialog=new LoadPlanDialog(getShell(), SWT.MULTI);
				//devo creare una nuova pagina dove salvare il nuovo piano
				dialog.setPlanContent(planView.getPlan());
				dialog.createContent();
			

			}
		};
		
		loadPlan.addListener(SWT.Selection, loadL);
	
		restoreStateDomain.addListener(SWT.Selection, listenerRestoreDomain);
		storeStateDomain.addListener(SWT.Selection, listenerStoreDomain);
		exitItem.addListener(SWT.Selection, listenerExit);
		menuLines.addListener(SWT.Selection, listenerLink);

		getShell().setMenuBar(this);
		getShell().addListener(SWT.Close, e -> {
			if (getShell().getModified()) {
				MessageBox box = new MessageBox(getShell(), SWT.PRIMARY_MODAL | SWT.OK | SWT.CANCEL);
				box.setText(getShell().getText());
				box.setMessage("You have unsaved changes, do you want to exit?");
				e.doit = box.open() == SWT.OK;
			}
		});
	}

	public void createFileLog() {
		createDirector();
		String filepath = dirLog.getAbsolutePath();
		file = new File(filepath, "TDP.txt");
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (file.exists() && !file.isDirectory()) {

		}

	}

	public void createDirPlan(PlanContent contentAction) {
		dirPlan = new File(dirLatex.getAbsolutePath() + "/" + contentAction.getText());
		// if the directory does n exist, create it
		if (!dirPlan.exists()) {
			System.out.println("creating directory: " + directory.getName());
			boolean result = false;

			try {
				dirPlan.mkdir();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				System.out.println("DIR created");
			}
		}

	}

	public void saveFile(PlanContent contentAction, String string) {
		createDirector();
		createDirPlan(contentAction);
		String filepath = dirPlan.getAbsolutePath();
		file = new File(filepath, "LatexPlan.tex");
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (file.isFile()) {
			WriteTextToFile(string);
		}

	}

	public void createDirector() {
		String filepath = System.getProperty("user.home");
		directory = new File(filepath + "/TDP");
		dirLog = new File(filepath + "/TDP" + "/dirLog");
		dirLatex = new File(filepath + "/TDP" + "/dirLatex");

		// if the directory does n exist, create it
		if (!directory.exists()) {
			System.out.println("creating directory: " + directory.getName());
			boolean result = false;

			try {
				directory.mkdir();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				System.out.println("DIR created");
			}
		}

		if (!dirLog.exists()) {
			System.out.println("creating directory: " + dirLog.getName());
			boolean result = false;

			try {
				dirLog.mkdir();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				System.out.println("DIR created");
			}
		}

		if (!dirLatex.exists()) {
			System.out.println("creating directory: " + dirLatex.getName());
			boolean result = false;

			try {
				dirLatex.mkdir();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				System.out.println("DIR created");
			}
		}

	}

	public void WriteTextToFile(String serObj) {

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file.getAbsolutePath(), "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.println(serObj);

		writer.close();

	}


	public PlainView getPlainView() {
		return plainView;
	}
	
}
