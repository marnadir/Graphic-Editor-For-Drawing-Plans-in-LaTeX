package View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import Action.Action;
import Action.Node;
import Dialog.IDialog;
import GraphPart.GraphContent;
import GraphPart.LinkCanvas;
import GraphPart.OrderCondition;
import GraphPart.Oval;
import Menu.IMenu;
import State.GoalState;
import State.GoalStateCanvas;
import State.InitialState;
import State.InitialStateCanvas;
import command.ExitCommand;

public class PrincipalView {

	private Shell shell;
	// private Composite child;
	private IMenu menuBar;
	private SashForm sashForm;
	private Group domainGroup;
	private SashForm sashForm2;
	private CTabFolder planView;
	private Group console;
	private DomainView domainView;
	private GraphContent contentAction;
	private ArrayList<Action> updateActionListDomain;
	private ArrayList<Node> updateNodeList;
	private ArrayList<LinkCanvas> updateLinkList;
	private ArrayList<OrderCondition> updateOrder;
	private ArrayList<GraphContent> listOfPlan;
	
	String fileName;
	File file;
	File directory;
	
	
	

	public PrincipalView(Shell shell) {
		this.shell = shell;

	}

	public void draw() {
		createMenuWindow();
		createContent();
		createDirector();
	}

	public void createMenuWindow() {

		menuBar = new IMenu(shell, SWT.BAR);
		MenuItem fileItem = menuBar.createItem("&File", SWT.CASCADE);
		IMenu menuFile = new IMenu(shell, SWT.DROP_DOWN);
		fileItem.setMenu(menuFile);

		MenuItem option = menuBar.createItem("&Option", SWT.CASCADE);
		IMenu menuOption = new IMenu(shell, SWT.DROP_DOWN);
		option.setMenu(menuOption);

		MenuItem storeStateDomain = new MenuItem(menuFile, SWT.PUSH);
		storeStateDomain.setText("&Save Domain\tCtrl+S");
		
		MenuItem restoreStateDomain = new MenuItem(menuFile, SWT.PUSH);
		restoreStateDomain.setText("&Reload domain\tCtrl+S");
		
		
		MenuItem saveAsItem = new MenuItem(menuFile, SWT.PUSH);
		saveAsItem.setText("&Save LatexCode");

		MenuItem saveAllItem = new MenuItem(menuFile, SWT.PUSH);
		saveAllItem.setText("&Save All\tShift+Ctrl+S");
		saveAllItem.setAccelerator(SWT.SHIFT + SWT.CONTROL + 'S');

		MenuItem exitItem = new MenuItem(menuFile, SWT.PUSH);
		exitItem.setText("&Exit");

		
		MenuItem showCond = new MenuItem(menuOption, SWT.PUSH);
		showCond.setText("Conditions in the Plan");
		
		MenuItem menuLines = new MenuItem(menuOption, SWT.PUSH);
		menuLines.setText("Create Connection");
		
		Listener listenerExit = new Listener() {
			@Override
			public void handleEvent(Event event) {
				ExitCommand command = new ExitCommand();
				command.execute(shell, event);
			}
		};


		Listener listenerStoreDomain = new Listener() {

			@Override
			public void handleEvent(Event event) {
				createFileLog();
				ArrayList<Object> data = new ArrayList<Object>();
				data.add(updateActionListDomain);
				if(domainView.getInitialState()!=null) {
					data.add(domainView.getInitialState().getState());
				}else {
					data.add(null);
				}
				if(domainView.getGoalState()!=null) {
					data.add(domainView.getGoalState().getState());
				}else {
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
				IDialog dialog = new IDialog(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER) {
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

				IDialog dialog = new IDialog(shell, SWT.DIALOG_TRIM) {

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
										contentAction.getLink().add(link);
										l1.setText("First Cond. :" + "Select the point");
										l2.setText("Second Cond. :" + "Select the point");
										l1.pack();
										l2.pack();
										link.removeL();
										//link.removelistener(l1, l2,archBtn);

									}
								} else if (orderCond != null) {
									if (!l2.getText().contains("null")) {
										orderCond.drawOrder();
										contentAction.getOrds().add(orderCond);
										orderCond.pack();
										c1 = "null";
										c2 = "null";
										l1.setText("ordering of actions");
										l2.setText(c1 + "<" + c2);
										l1.pack();
										l2.pack();
										//orderCond.removelistener(l2);

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

								link = new LinkCanvas(contentAction);
								link.addlistener(l1, l2,archBtn);

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

								Composite comp = new Composite(contentAction, SWT.ALL);
//								comp.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
//								comp.setLayout(new FillLayout());

								// sulla definizione di cio, ce qualcosa che mi turba!!
								comp.setSize(50, 50);
								comp.setLocation(20, 30);
								// comp.setBackground(comp.getDisplay().getSystemColor(SWT.COLOR_RED));

								orderCond = new OrderCondition(comp);
								orderCond.addlistener(l1,l2);

							}
						});

						this.getDialog().pack();

					}

				};

				dialog.createContent();
			}
		};
		
		
		showCond.addListener(SWT.Selection, listenerShow);
		restoreStateDomain.addListener(SWT.Selection, listenerRestoreDomain);
		storeStateDomain.addListener(SWT.Selection, listenerStoreDomain);
		exitItem.addListener(SWT.Selection, listenerExit);
		menuLines.addListener(SWT.Selection, listenerLink);

		shell.setMenuBar(menuBar);
		shell.addListener(SWT.Close, e -> {
			if (shell.getModified()) {
				MessageBox box = new MessageBox(shell, SWT.PRIMARY_MODAL | SWT.OK | SWT.CANCEL);
				box.setText(shell.getText());
				box.setMessage("You have unsaved changes, do you want to exit?");
				e.doit = box.open() == SWT.OK;
			}
		});
	}

	
	
	
	
	
	public void createContent() {

		ScrolledComposite firstScroll = new ScrolledComposite(shell, SWT.V_SCROLL | SWT.H_SCROLL);
		firstScroll.setLayout(new GridLayout(1, false));
		firstScroll.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		sashForm = new SashForm(firstScroll, SWT.HORIZONTAL);

		firstScroll.setContent(sashForm);
		firstScroll.setExpandHorizontal(true);
		firstScroll.setExpandVertical(true);

		domainView = new DomainView(sashForm);
		domainView.createContent();

		sashForm2 = new SashForm(sashForm, SWT.VERTICAL);
		sashForm.setWeights(new int[] { 1, 3 });
		
		planView = new CTabFolder(sashForm2, SWT.PUSH);
		planView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		planView.setSimple(false);
		planView.setUnselectedImageVisible(false);
		planView.setUnselectedCloseVisible(false);
		
		CTabItem item = new CTabItem(planView, SWT.CLOSE);
		contentAction = new GraphContent(planView, SWT.ALL);
		item.setControl(contentAction);
		planView.setSelection(item);
		listOfPlan = new ArrayList<>();
		listOfPlan.add(contentAction);
		item.setText("Plan" + listOfPlan.size());

		ToolBar t = new ToolBar(planView, SWT.ALL);
		ToolItem toolShow=new ToolItem(t, SWT.CHECK);
		Image icon = new Image(shell.getDisplay(), "img/eye.png");
		toolShow.setImage(icon);
		toolShow.addListener(SWT.Selection,new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				
				updateNodeList = contentAction.getActionInPlan();
				for(Node node:updateNodeList) {
					if(toolShow.getSelection()) {
						node.getAction().setIsShownCond(true);
						node.pack();

					}else {
						node.getAction().setIsShownCond(false);
						node.pack();
					}
				}
				
			}
		} );
		
		
		ToolItem toolSetLenght=new ToolItem(t, SWT.PUSH);
		icon = new Image(shell.getDisplay(), "img/setL.png");
		toolSetLenght.setImage(icon);
		toolSetLenght.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				IDialog d=new IDialog(shell,SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER) {
					Combo combo;
					Text lenghtPrec;
					Text lenghtEff;
					@Override
					public Listener getOkbtnListener() {
						Listener l;
						l = new Listener() {

							@Override
							public void handleEvent(Event event) {
								if(combo.getText().equalsIgnoreCase("action")) {
									if(!(lenghtPrec.getText().equals(""))&&!(lenghtPrec.getText().equals(""))) {
										updateNodeList = contentAction.getActionInPlan();
										for(Node node:updateNodeList) {
											ArrayList<Oval> listOval=contentAction.getOvalCounter().getListOval();
											for(int i=0;i<listOval.size();i++) {
												if(listOval.get(i).getStateCanvas()!=null) {
													if(listOval.get(i).getNode()  instanceof Node) {
														listOval.get(i).dispose();
														listOval.remove(i);
														contentAction.getOvalCounter().setListOval(listOval);
													}
												}
											}
											
											
											
											node.getAction().setLengthPrecFromCm(Double.parseDouble(lenghtPrec.getText()));
											node.getAction().setStandardLengthPrecFromCm(Double.parseDouble(lenghtPrec.getText()));

											node.getAction().setLengthEffFromCm(Double.parseDouble(lenghtEff.getText()));
											node.getAction().setStandardLengthEffFromCm(Double.parseDouble(lenghtEff.getText()));
											node.pack();
											
											
										}
									}
								}else {
									if(!(lenghtPrec.getText().equals(""))) {
										if(	contentAction.getInitialStateCanvas()!=null) {
											
											ArrayList<Oval> listOval=contentAction.getOvalCounter().getListOval();
											for(int i=0;i<listOval.size();i++) {
												if(listOval.get(i).getStateCanvas()!=null) {
													if(listOval.get(i).getStateCanvas()  instanceof InitialStateCanvas) {
														listOval.get(i).dispose();
														listOval.remove(i);
														contentAction.getOvalCounter().setListOval(listOval);
													}
												}
											}
																						
											contentAction.getInitialStateCanvas().setLengthFromCm(Double.parseDouble(lenghtPrec.getText()));
											contentAction.getInitialStateCanvas().setStandardLengthFromCm(Double.parseDouble(lenghtPrec.getText()));
											contentAction.getInitialStateCanvas().pack();
										}
										if(contentAction.getGoalStateCanvas()!=null) {
											if(	contentAction.getGoalStateCanvas()!=null) {
												
												ArrayList<Oval> listOval=contentAction.getOvalCounter().getListOval();
												for(int i=0;i<listOval.size();i++) {
													if(listOval.get(i).getStateCanvas()!=null) {
														if(listOval.get(i).getStateCanvas()  instanceof GoalStateCanvas) {
															listOval.get(i).dispose();
															listOval.remove(i);
															contentAction.getOvalCounter().setListOval(listOval);
														}
													}
												}
																							
												contentAction.getGoalStateCanvas().setLengthFromCm(Double.parseDouble(lenghtPrec.getText()));
												contentAction.getGoalStateCanvas().setStandardLengthFromCm(Double.parseDouble(lenghtPrec.getText()));
												contentAction.getGoalStateCanvas().pack();
											}
										}
										
									}
								}
							}
						};
						return l;
					}
					
					@Override
					public void createContent() {
						getLabel().setText("Set global lenght");
						this.getLabel().pack();
						Composite c = getComposite();
						c.setLayout(new GridLayout(1, false));
						Composite comp1 = new Composite(c, SWT.ALL);
						comp1.setLayout(new RowLayout(SWT.VERTICAL));
						Label l=new Label(comp1, SWT.ALL);
						l.setText("choose: ");
						combo = new Combo(comp1, SWT.PUSH);
						combo.setItems("Action","So/Goal State");
						combo.setText(combo.getItem(0));
						combo.pack();
						
						Composite comp2=new Composite(c, SWT.ALL);
						comp2.setLayout(new GridLayout(2, false));
						Label l2=new Label(comp2, SWT.ALL);
						l2.setText("Prec:");	
						lenghtPrec=new Text(comp2, SWT.BORDER);
						
						
						Label l3=new Label(comp2, SWT.ALL);
						l3.setText("Eff:");

						lenghtEff=new Text(comp2, SWT.BORDER);
						Label l4=new Label(comp2, SWT.ALL);
						l4.setText("Default value is 1.2cm");
						

						combo.addSelectionListener(new SelectionAdapter() {
							  public void widgetSelected(SelectionEvent e) {
									if(combo.getText().equalsIgnoreCase("action")) {	
										l3.setVisible(true);
										lenghtEff.setVisible(true);

										comp2.pack();
										getDialog().pack();

										
									}else {
										l2.setText("Cond:");
										l3.setVisible(false);
										lenghtEff.setVisible(false);
										getDialog().pack();

									}
							  }
						});
						
						
						this.getDialog().pack();
						
					}
				};
				
				d.createContent();
			}
		});
		
		
		ToolItem i = new ToolItem(t, SWT.PUSH);
		i.setToolTipText("add a new Plan");
		 icon = new Image(shell.getDisplay(), "img/add-documents.png");
		i.setImage(icon);
		i.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent var1) {

				CTabItem item2 = new CTabItem(planView, SWT.CLOSE);
				contentAction = new GraphContent(planView, SWT.ALL);
				item2.setControl(contentAction);
				listOfPlan.add(contentAction);
				item2.setText("Plan" + listOfPlan.size());
				contentAction.addDndListener(domainView.getTreeAction());

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent var1) {
				// TODO Auto-generated method stub

			}
		});

		planView.setTopRight(t, SWT.RIGHT);
		planView.setTabHeight(Math.max(t.computeSize(SWT.DEFAULT, SWT.DEFAULT).y, planView.getTabHeight()));

		
		
		console = new Group(sashForm2, SWT.SCROLL_LINE);
		console.setText("Console");
		console.setLayout(new GridLayout(2, true));

		Group consoleDomain = new Group(console, SWT.ALL);
		consoleDomain.setText("Domain");
		consoleDomain.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		consoleDomain.setLayout(new GridLayout(1, true));

		ToolBar toolBarDomain = new ToolBar(consoleDomain, SWT.FLAT | SWT.WRAP | SWT.RIGHT);

		ToolItem updateTextDomain = new ToolItem(toolBarDomain, SWT.PUSH);
		updateTextDomain.setText("update");
		icon = new Image(shell.getDisplay(), "img/refresh.png");
		updateTextDomain.setImage(icon);
		Text textDomain = new Text(consoleDomain, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);

		textDomain.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		textDomain.pack();

		/* domain */
		updateTextDomain.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				textDomain.setText("");
				if (domainView.getInitialState() != null) {
					domainView.getInitialState().generateLatexCode();
					textDomain.insert(domainView.getInitialState().getLatexCode());
				}
				if (domainView.getGoalState() != null) {
					domainView.getGoalState().generateLatexCode();
					textDomain.insert(domainView.getGoalState().getLatexCode());
				}

				updateActionListDomain = domainView.getTreeAction().getActionList();
				for (int i = 0; i < updateActionListDomain.size(); i++) {
					updateActionListDomain.get(i).generateLatexCode();
					textDomain.insert(updateActionListDomain.get(i).getLatexCode());
				}

			}
		});

		ToolItem clearTextDomain = new ToolItem(toolBarDomain, SWT.PUSH);
		clearTextDomain.setText("clear");
		icon = new Image(shell.getDisplay(), "img/clear.ico");
		clearTextDomain.setImage(icon);
		clearTextDomain.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				textDomain.setText(".....");

			}
		});

		toolBarDomain.pack();

		Group consolePlan = new Group(console, SWT.ALL);
		consolePlan.setText("Plan");
		consolePlan.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		consolePlan.setLayout(new GridLayout(1, true));

		ToolBar toolBarPlan = new ToolBar(consolePlan, SWT.FLAT | SWT.WRAP | SWT.RIGHT);

		ToolItem updateTextPlan = new ToolItem(toolBarPlan, SWT.PUSH);
		updateTextPlan.setText("update");
		icon = new Image(shell.getDisplay(), "img/refresh.png");
		updateTextPlan.setImage(icon);
		ToolItem clearTextPlan = new ToolItem(toolBarPlan, SWT.PUSH);
		clearTextPlan.setText("clear");
		icon = new Image(shell.getDisplay(), "img/clear.ico");
		clearTextPlan.setImage(icon);

		Text textPlan = new Text(consolePlan, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
		textPlan.insert("update data...");
		textPlan.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		textPlan.pack();

		toolBarPlan.pack();
		/* Plan */
		updateTextPlan.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				textPlan.setText("");

				
				contentAction = (GraphContent)planView.getSelection().getControl();
				updateNodeList = contentAction.getActionInPlan();
				for (int i = 0; i < updateNodeList.size(); i++) {
					updateNodeList.get(i).generateLatexCode();
					textPlan.insert(updateNodeList.get(i).getLatexCode());
				}

				updateLinkList = contentAction.getLink();
				for (int i = 0; i < updateLinkList.size(); i++) {
					updateLinkList.get(i).generateLatexCode();
					textPlan.insert(updateLinkList.get(i).getLatexCode());
				}

				updateOrder = contentAction.getOrds();
				for (int i = 0; i < updateOrder.size(); i++) {
					updateOrder.get(i).generateLatexCode();
					textPlan.insert(updateOrder.get(i).getLatexCode());
				}

			}
		});

		updateActionListDomain = domainView.getTreeAction().getActionList();
		contentAction.addDndListener(domainView.getTreeAction());

//		Display.getDefault().timerExec(100, new Runnable() {
//			@Override
//			public void run() {
//				contentAction.redraw();
//				Display.getDefault().timerExec(100, this);
//			}
//		});

		shell.setMaximized(false);
	}

	
	public void createFileLog() {
		String filepath = directory.getAbsolutePath()+"TDP.txt";
		file = new File(filepath);
		if (file.exists() && !file.isDirectory()) {
			
		}

	}
	
	
	public void createDirector() {
		String filepath = System.getProperty("user.home") ;
		 directory = new File(filepath+ "/TDP");
		 File dirLog=new File(filepath+ "/TDP"+"/dirLog");
		 File dirLatex=new File(filepath+ "/TDP"+"/dirLatex");

		// if the directory does not exist, create it
		if (!directory.exists()) {
		    System.out.println("creating directory: " + directory.getName());
		    boolean result = false;

		    try{
		        directory.mkdir();
		        result = true;
		    } 
		    catch(SecurityException se){
		        //handle it
		    }        
		    if(result) {    
		        System.out.println("DIR created");  
		    }
		}
		
		if (!dirLog.exists()) {
		    System.out.println("creating directory: " + dirLog.getName());
		    boolean result = false;

		    try{
		    	dirLog.mkdir();
		        result = true;
		    } 
		    catch(SecurityException se){
		        //handle it
		    }        
		    if(result) {    
		        System.out.println("DIR created");  
		    }
		}
		
		if (!dirLatex.exists()) {
		    System.out.println("creating directory: " + dirLatex.getName());
		    boolean result = false;

		    try{
		    	dirLatex.mkdir();
		        result = true;
		    } 
		    catch(SecurityException se){
		        //handle it
		    }        
		    if(result) {    
		        System.out.println("DIR created");  
		    }
		}
		
		
	}

	
	
	
	public void WriteObjectToFile(Object serObj){
	
		
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
			updateActionListDomain=(ArrayList<Action>)data.get(0);
			
			
			
			if(data.get(1)!=null) {
				InitialState in=(InitialState) data.get(1);
				InitialStateCanvas initialStateCanvas=new InitialStateCanvas(
						domainView.getInitStateView().getContainerInitState(), SWT.BORDER, in);
				initialStateCanvas.draw();
				initialStateCanvas.addDNDListener();
				initialStateCanvas.generateLatexCode();
				initialStateCanvas.getLatexCode();
			}
			if(data.get(2) !=null) {
				GoalState goal=(GoalState) data.get(2);
				GoalStateCanvas goalStateCanvas=new GoalStateCanvas(
						domainView.getGoalStateView().getContainerGoalState(), SWT.BORDER, goal);
				goalStateCanvas.draw();
				goalStateCanvas.addDNDListener();
				goalStateCanvas.generateLatexCode();
				goalStateCanvas.getLatexCode();
			}
			
			
			
			
			objectIn.close();
			System.out.println("The Object  was succesfully read from a file");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	public Shell getShell() {
		return shell;
	}

	public IMenu getMenuBar() {
		return menuBar;
	}

	public SashForm getSashForm() {
		return sashForm;
	}

	public Group getDomainGroup() {
		return domainGroup;
	}

	public SashForm getSashForm2() {
		return sashForm2;
	}

	public CTabFolder getPlanView() {
		return planView;
	}

	public Group getConsole() {
		return console;
	}

}
