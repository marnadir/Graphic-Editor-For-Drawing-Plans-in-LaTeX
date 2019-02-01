package Menu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.text.PlainView;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;

import Action.Action;
import Action.Node;
import Dialog.IDialog;
import LaTex.LaTexGeneratorPlan;
import LaTex.LaTexGeneratorStatePlan;
import PlanPart.PlanContent;
import PlanPart.LinkCanvas;
import PlanPart.OrderCondition;
import State.GoalState;
import State.GoalStateCanvas;
import State.InitialState;
import State.InitialStateCanvas;
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
		this.plainView=plainView;
		
		MenuItem fileItem = createItem("&File", SWT.CASCADE);
		IMenu menuFile = new IMenu(getShell(), SWT.DROP_DOWN);
		fileItem.setMenu(menuFile);

		MenuItem option = createItem("&Option", SWT.CASCADE);
		IMenu menuOption = new IMenu(getShell(), SWT.DROP_DOWN);
		option.setMenu(menuOption);

		MenuItem storeStateDomain = new MenuItem(menuFile, SWT.PUSH);
		storeStateDomain.setText("&Save Domain\tCtrl+S");

		MenuItem restoreStateDomain = new MenuItem(menuFile, SWT.PUSH);
		restoreStateDomain.setText("&Reload domain\tCtrl+S");


		MenuItem saveAllItem = new MenuItem(menuFile, SWT.PUSH);
		saveAllItem.setText("&Save All\tShift+Ctrl+S");
		saveAllItem.setAccelerator(SWT.SHIFT + SWT.CONTROL + 'S');

		MenuItem exitItem = new MenuItem(menuFile, SWT.PUSH);
		exitItem.setText("&Exit");

		MenuItem showCond = new MenuItem(menuOption, SWT.PUSH);
		showCond.setText("Conditions in the Plan");

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
				createFileLog();
				ArrayList<Object> data = new ArrayList<Object>();
				data.add(updateActionListDomain);
				if (domainView.getInitialState() != null) {
					data.add(domainView.getInitialState().getState());
				} else {
					data.add(null);
				}
				if (domainView.getGoalState() != null) {
					data.add(domainView.getGoalState().getState());
				} else {
					data.add(null);
				}
				WriteObjectToFile(data);
			}
		};

		Listener listenerRestoreDomain = new Listener() {

			@Override
			public void handleEvent(Event event) {
				createFileLog();
				ReadObjectToFile();
				domainView.restoreActionList(updateActionListDomain);
			}
		};

		Listener listenerShow = new Listener() {

			@Override
			public void handleEvent(Event event) {
				IDialog dialog = new IDialog(getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER) {
					Composite compButton;

					@Override
					public Listener getOkbtnListener() {
						Listener l;
						l = new Listener() {

							@Override
							public void handleEvent(Event event) {
								Control[] child = compButton.getChildren();
								for (int i = 0; i < child.length; i++) {
									Button btn = (Button) child[i];
									if (btn.getSelection()) {
										System.out.println(btn.getText());
										getDialog().setVisible(false);
									}
								}

							}
						};
						return l;
					}

					@Override
					public void createContent() {
						getLabel().setText("Choice for Showing/Hiding conditions");
						this.getLabel().pack();
						Composite c = getComposite();
						c.setLayout(new GridLayout(1, false));
						compButton = new Composite(c, SWT.ALL);
						compButton.setLayout(new RowLayout(SWT.HORIZONTAL));

						Button showAllBtn = new Button(compButton, SWT.RADIO);
						showAllBtn.setText("ShowAll Conditions");

						Button hideAllBtn = new Button(compButton, SWT.RADIO);
						hideAllBtn.setText("HideAll Conditions");

						Button choiceUserBtn = new Button(compButton, SWT.RADIO);
						choiceUserBtn.setText("Depending on Choice");

						this.getDialog().pack();

					}
				};

				dialog.createContent();
			}
		};

		Listener listenerLink = new Listener() {
			Composite compButton;
			private Composite compPoint;

			private LinkCanvas link;
			private OrderCondition orderCond;
			Label l1 = null;

			Label l2 = null;
			String c1 = "....";
			String c2 = "....";

			@Override
			public void handleEvent(Event event) {

				IDialog dialog = new IDialog(getShell(), SWT.DIALOG_TRIM) {

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
										orderCond.drawOrder();
										planView.getPlan().getOrds().add(orderCond);
										orderCond.pack();
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
					public void createContent() {

						getLabel().setText("Create Connection");
						this.getLabel().pack();
						Composite c = getComposite();
						c.setLayout(new GridLayout(1, false));
						compButton = new Composite(c, SWT.ALL);
						compButton.setLayout(new RowLayout(SWT.VERTICAL));

						Button archBtn = new Button(compButton, SWT.PUSH);
						archBtn.setText("draw arch");

						Button ordBtn = new Button(compButton, SWT.PUSH);
						ordBtn.setText("draw Ord");

						compPoint = new Composite(c, SWT.ALL);
						compPoint.setLayout(new GridLayout());
						l1 = new Label(compPoint, SWT.ALL);
						l2 = new Label(compPoint, SWT.ALL);
						compPoint.setVisible(false);

						archBtn.addListener(SWT.Selection, new Listener() {
							

							@Override
							public void handleEvent(Event event) {

								orderCond = null;

								l1.setText("First Cond. :" + "Select the point");
								l1.pack();
								l2.setText("Second Cond. :" + "Select the point");
								l2.pack();
								compPoint.pack();
								getDialog().pack();
								compPoint.setVisible(true);

								link = new LinkCanvas(planView.getPlan());
								link.addlistener(l1, l2, archBtn);

							}
						});

						ordBtn.addListener(SWT.Selection, new Listener() {

							@Override
							public void handleEvent(Event event) {

								link = null;
								c1 = "null";
								c2 = "null";
								l1.setText("ordering of actions");
								l2.setText(c1 + "<" + c2);
								l2.pack();
								compPoint.pack();
								getDialog().pack();
								compPoint.setVisible(true);

								Composite comp = new Composite(planView, SWT.ALL);
//								comp.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
//								comp.setLayout(new FillLayout());

								// sulla definizione di cio, ce qualcosa che mi turba!!
								comp.setSize(50, 50);
								comp.setLocation(20, 30);
								// comp.setBackground(comp.getDisplay().getSystemColor(SWT.COLOR_RED));

								orderCond = new OrderCondition(comp);
								orderCond.addlistener(l1, l2);

							}
						});

						this.getDialog().pack();

					}

				};

				dialog.createContent();
			}
		};

		Listener saveAll=new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				
			
				for(PlanContent contentAction:planView.getAllPlan()) {
					StringBuilder sb = new StringBuilder();
					LaTexGeneratorPlan laTexGeneratorPlan=new LaTexGeneratorPlan();
					sb.append(laTexGeneratorPlan.getLatexIntro());
					
					ArrayList<Node> updateNodeList = contentAction.getActionInPlan();
					for (int i = 0; i < updateNodeList.size(); i++) {
						updateNodeList.get(i).generateLatexCode();
						sb.append(updateNodeList.get(i).getLatexCode());
					}

					ArrayList<LinkCanvas> updateLinkList = contentAction.getLink();
					for (int i = 0; i < updateLinkList.size(); i++) {
						updateLinkList.get(i).generateLatexCode();
						sb.append(updateLinkList.get(i).getLatexCode());
					}

					ArrayList<OrderCondition>updateOrder = contentAction.getOrds();
					for (int i = 0; i < updateOrder.size(); i++) {
						updateOrder.get(i).generateLatexCode();
						sb.append(updateOrder.get(i).getLatexCode());
					}
					

					LaTexGeneratorStatePlan generatorStatePlan=new LaTexGeneratorStatePlan();
					sb.append(generatorStatePlan.getLatexPlanCode(contentAction));
					
					sb.append(laTexGeneratorPlan.getLatexEnd());
					
					saveFile(contentAction,sb.toString());
					
				}
				
				
			}
		};
		
		saveAllItem.addListener(SWT.Selection,saveAll );
		showCond.addListener(SWT.Selection, listenerShow);
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
		file = new File(filepath,"TDP.txt");
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
	
	public void saveFile(PlanContent contentAction,String string) {
		createDirector();
		createDirPlan(contentAction);
		String filepath = dirPlan.getAbsolutePath();
		file = new File(filepath,"LatexPlan.tex");
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
	
	
	public void WriteObjectToFile(Object serObj) {

		try {
			FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath());
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(serObj);
			objectOut.close();
			System.out.println("The Object  was succesfully written to a file");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void ReadObjectToFile() {

		try {
			FileInputStream fileIn = new FileInputStream(file.getAbsolutePath());
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			ArrayList<Object> data = (ArrayList<Object>) objectIn.readObject();
			updateActionListDomain = (ArrayList<Action>) data.get(0);

			if (data.get(1) != null) {
				InitialState in = (InitialState) data.get(1);
				InitialStateCanvas initialStateCanvas = new InitialStateCanvas(
						domainView.getInitStateView().getContainerInitState(), SWT.BORDER, in);
				initialStateCanvas.draw();
				initialStateCanvas.addDNDListener();
				initialStateCanvas.generateLatexCodeDomain();
				initialStateCanvas.getLatexCodeDomain();
			}
			if (data.get(2) != null) {
				GoalState goal = (GoalState) data.get(2);
				GoalStateCanvas goalStateCanvas = new GoalStateCanvas(
						domainView.getGoalStateView().getContainerGoalState(), SWT.BORDER, goal);
				goalStateCanvas.draw();
				goalStateCanvas.addDNDListener();
				goalStateCanvas.generateLatexCodeDomain();
				goalStateCanvas.getLatexCodeDomain();
			}

			objectIn.close();
			System.out.println("The Object  was succesfully read from a file");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public PlainView getPlainView() {
		return plainView;
	}
	
	
	
}
