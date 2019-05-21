package View;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import Action.Node;
import Dialog.NewConnectionDialog;
import Dialog.SaveLAtexCode;
import Dialog.SavePlanDialog;
import PDFConverter.PdfConverter;
import PlanPart.PlanContent;
import State.LoadLink;
import command.SaveDomainCommand;
import command.SavePlanCommand;
import resourceLoader.ResourceLoader;

public class PlanView  extends CTabFolder{
	
	ConsoleView consoleView;
	DomainView domainView;
	PdfView pdfView;
	ToolItem showCondition;
	PlanContent contentPlan;
	SaveLAtexCode dialog = null;
	SavePlanDialog dialogPlan;
	
	public PlanView(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}
	
	public void setLayout() {
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		setSimple(false);
		setUnselectedImageVisible(false);
		setUnselectedCloseVisible(false);
		
		dialog=new SaveLAtexCode(getShell(), SWT.SAVE);

		
	}
	
	
	public void createContent(DomainView domainView) {
		this.domainView=domainView;
		CTabItem item = new CTabItem(this, SWT.CLOSE);
		contentPlan = new PlanContent(this, SWT.ALL);
	
		//TODO Quanto mi consuma sta pezzo di codice
		
		Display.getDefault().timerExec(10, new Runnable() {
		    @Override
		    public void run() {
		    	contentPlan.redraw();

		      // Run again - TODO add logic to stop after correct number of moves
		      Display.getDefault().timerExec(10, this);
		    }
		   });
		
		contentPlan.addDndListener(domainView.getTreeAction());
		Button b1=new Button(contentPlan, SWT.PUSH);
		b1.setText("Load Link");
		b1.pack();
		b1.setVisible(false);
		b1.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				b1.setVisible(false);
				LoadLink loadLink=new LoadLink(getPlan());
				loadLink.draw();
				
				
			}
		});
		
		
		item.setControl(contentPlan);
		setSelection(item);
		ArrayList<PlanContent> listOfPlan = new ArrayList<>();
		listOfPlan.add(contentPlan);
		item.setText("Plan" + listOfPlan.size());

		ToolBar t = new ToolBar(this, SWT.ALL);
		
		
		ToolItem newConnection=new ToolItem(t, SWT.PUSH);
		Image icon = new Image(getDisplay(), ResourceLoader.load("img/connection.png"));
		newConnection.setToolTipText("New Link/Constraint");
		newConnection.setImage(icon);
		newConnection.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				NewConnectionDialog dialog=new NewConnectionDialog(getShell(), 
						SWT.DIALOG_TRIM | SWT.CENTER | SWT.RESIZE);
				dialog.setPlanView(getPlanView());
				dialog.createContent();
				
			}
		});
		
		showCondition=new ToolItem(t, SWT.CHECK);
		icon = new Image(getDisplay(), ResourceLoader.load("img/eye.png"));
		showCondition.setToolTipText("Show/Hide Conditions");
		showCondition.setImage(icon);
		showCondition.addListener(SWT.Selection,new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				
				ArrayList<Node> updateNodeList = getPlan().getActionInPlan();
				for(Node node:updateNodeList) {
//					Iterator<Oval> i = listOval.iterator();
//					while (i.hasNext()) {
//							Oval oval = i.next(); // must be called before you can call i.remove()
//							if(oval!=null) {
////								if(oval.getNode()  instanceof Node) {
//									oval.dispose();
//									 i.remove();
//									 contentAction.getOvalCounter().setListOval(listOval);
////								}
//							}
//							
//					}
					if(showCondition.getSelection()) {
						node.getAction().setIsShownCond(true);
						//node.pack();
						node.redraw();
					


					}else {
						node.getAction().setIsShownCond(false);
						//node.pack();
						node.redraw();					
					}
					

				
				}
		
				
				if (showCondition.getSelection()) {
					if (getPlan().getInitialStateCanvas() != null) {

						getPlan().getInitialStateCanvas().getState().setShownCond(true);
						getPlan().getInitialStateCanvas().redraw();

					}
					if (getPlan().getGoalStateCanvas() != null) {
						getPlan().getGoalStateCanvas().getState().setShownCond(true);
						getPlan().getInitialStateCanvas().redraw();
					}

				} else {
					if (getPlan().getInitialStateCanvas() != null) {
						getPlan().getInitialStateCanvas().getState().setShownCond(false);
						getPlan().getInitialStateCanvas().redraw();

					}
					if (getPlan().getGoalStateCanvas() != null) {
						getPlan().getGoalStateCanvas().getState().setShownCond(false);
						getPlan().getGoalStateCanvas().redraw();
					}
				}
				
				
				redraw();
				contentPlan.redraw();

			}
		} );
		ToolItem savePlan=new ToolItem(t,SWT.PUSH);
		icon = new Image(getDisplay(), ResourceLoader.load("img/save.ico"));
		savePlan.setImage(icon);
		savePlan.setToolTipText("Save Plan");
		savePlan.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				//save first time the plan
				if(getPlan().getSavedPlanFile()==null) {
					dialogPlan=new SavePlanDialog(getShell(), SWT.SAVE);
					dialogPlan.setPlanContent(getPlan());
					dialogPlan.createContent();
					getPlan().setSavedPllan(dialogPlan.getCommand().getPlanFile());
				}else {
					//load the plan and then save it
					SavePlanCommand command=new SavePlanCommand();
					command.setPlanContent(getPlan());
					command.execute( getPlan().getSavedPlanFile().getParentFile()
							.getAbsolutePath(),"PlanStore.txt");
				}
				SaveDomainCommand command=new SaveDomainCommand();
				command.copyFileDomain(getPlan().getSavedPlanFile().getParentFile()
							.getAbsolutePath(), domainView);
			
			}
		});
		
		ToolItem PDFPreview=new ToolItem(t,SWT.PUSH);
		icon = new Image(getDisplay(),ResourceLoader.load("img/pdf.ico") );
		PDFPreview.setImage(icon);
		PDFPreview.setToolTipText("Generete PDF");
		
		PDFPreview.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				
	
				ConsoleViewPlan consoleViewPlan=consoleView.getConsoleViewPlan();
				ConsoleViewDomain consoleViewDomain=consoleView.getConsoleViewDomain();
				
				dialog.setConsoleViewPlan(consoleViewPlan);
				dialog.setConsoleViewDomain(consoleViewDomain);
				
				if(getPlan().getDirectory()==null) {
					
					dialog.createContent();
					System.out.println(dialog.getFileName());
					if(dialog.getFileLatex()!=null) {
						getPlan().setLatexFile(dialog.getFileLatex());
						getPlan().setDirectory(dialog.getFileLatex().getParentFile());
					}
			
				}else {
					File dir=getPlan().getDirectory();
					if(getPlan().getLatexFile()==null) {
						dialog.createFilePlan(dir.getAbsolutePath(),"PlanLatex.tex");
						System.out.println(dialog.getFileName());
						getPlan().setLatexFile(dialog.getFileLatex());

						
					}else {
						dialog.createFilePlan(dir.getAbsolutePath(),"PlanLatex.tex");
						System.out.println(dialog.getFileName());

						getPlan().setLatexFile(dialog.getFileLatex());

					}
				}
				
				
				
				//consoleViewPlan.saveFile();
				
				
				
				
				File dir=getPlan().getDirectory();
				if(dialog.isIsdomainLoad() ) {
					try {

						String cmd1="cd "+dir.getAbsolutePath();
						
						//dialog.getFileName();
						String cmd2="pdflatex "+dialog.getFileLatex().getName()+" -synctex=1 -interaction=nonstopmode";
						//String cmd3="xdg-open LatexPlan.pdf";
								
						Process process = Runtime.getRuntime().exec(new String[] { "bash", "-c",
								cmd1+" && "+cmd2});


						BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
						StringBuilder builder = new StringBuilder();
						String line = null;
						while ((line = reader.readLine()) != null) {
							builder.append(line);
							builder.append(System.getProperty("line.separator"));
						}
						String result = builder.toString();
						System.out.println(result);
						
						//process.waitFor();

					}
					
					
					
					catch (IOException  e) {
						// TODO Auto-generated catch block
						e.printStackTrace();

					}                       
				
					String pdfFile=getPlan().getLatexFile().getName();
					pdfFile=pdfFile.substring(0, pdfFile.length()-3);
					pdfFile=pdfFile+"pdf";
					PdfConverter pdfConverter=new PdfConverter(dir+"/"+pdfFile);
					pdfConverter.execute();
					
					pdfFile=getPlan().getLatexFile().getName();
					pdfFile=pdfFile.substring(0, pdfFile.length()-3);
					pdfFile=pdfFile+"png";
					pdfView.draw(dir+"/"+pdfFile);
					
					if(pdfView.getParent() instanceof CTabFolder) {
						CTabFolder folder=(CTabFolder)pdfView.getParent();
						for(CTabItem c:folder.getItems()) {
							if(c.getControl() instanceof PdfView) {
								folder.setSelection(c);
							}
						}
					}
					
					
				}else {
					
				}
					
				
			
				
				
			}
		});
		
		ToolItem i = new ToolItem(t, SWT.PUSH);
		i.setToolTipText("Add a New Plan");
		 icon = new Image(getDisplay(),ResourceLoader.load("img/add-documents.png") );
		i.setImage(icon);
		i.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent var1) {

				CTabItem item2 = new CTabItem(getPlanView(), SWT.CLOSE);
				PlanContent contentAction = new PlanContent(getPlanView(), SWT.ALL);
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

		setTopRight(t, SWT.RIGHT);
		setTabHeight(Math.max(t.computeSize(SWT.DEFAULT, SWT.DEFAULT).y, getTabHeight()));

	}
	
	public boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	
	public PlanView getPlanView() {
		return this;
	}
	
	public void setPdfPreView(ConsoleView consoleView) {
		this.consoleView=consoleView;
	}
	
	public ArrayList<PlanContent> getAllPlan() {
		ArrayList<PlanContent> child=new ArrayList<>();
		Control[] control=getChildren();
		for(Control c:control) {
			if(c instanceof PlanContent) {
				child.add((PlanContent)c);
				
			}
		}
		return child;
	}
	
	public PlanContent getPlan() {
		PlanContent planContent=(PlanContent) getSelection().getControl();
		return planContent;
		
	}

	
	

	public DomainView getDomainView() {
		return domainView;
	}

	@Override
	protected void checkSubclass() {
		
	}
	
	
	public void setPdfView(PdfView pdfView) {
		this.pdfView = pdfView;
	}

	public boolean isShowConditionSelecte() {
		return showCondition.getSelection();
	}
	
}
