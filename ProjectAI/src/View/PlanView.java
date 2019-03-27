package View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import Action.Node;
import Dialog.SaveLAtexCode;
import Dialog.SavePlanDialog;
import PDFConverter.PdfConverter;
import PlanPart.PlanContent;
import State.LoadLink;

public class PlanView  extends CTabFolder{
	
	ConsoleView consoleView;
	DomainView domainView;
	PdfView pdfView;
	ToolItem showCondition;
	PlanContent contentPlan;
	
	public PlanView(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}
	
	public void setLayout() {
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		setSimple(false);
		setUnselectedImageVisible(false);
		setUnselectedCloseVisible(false);
	}
	
	public void createContent(DomainView domainView) {
		this.domainView=domainView;
		CTabItem item = new CTabItem(this, SWT.CLOSE);
		contentPlan = new PlanContent(this, SWT.ALL);
		contentPlan.addDndListener(domainView.getTreeAction());
		Button b1=new Button(contentPlan, SWT.PUSH);
		b1.setText("link");
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
		showCondition=new ToolItem(t, SWT.CHECK);
		Image icon = new Image(getDisplay(), "img/eye.png");
		showCondition.setImage(icon);
		showCondition.addListener(SWT.Selection,new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				
				
				


				ArrayList<Node> updateNodeList = contentPlan.getActionInPlan();
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

			
				
				
				if(showCondition.getSelection()) {
					if(contentPlan.getInitialStateCanvas()!=null) {
						contentPlan.getInitialStateCanvas().getState().setShownCond(true);
						contentPlan.getInitialStateCanvas().pack();
				

						
					}
					if(contentPlan.getGoalStateCanvas()!=null) {
						contentPlan.getGoalStateCanvas().getState().setShownCond(true);
						contentPlan.getGoalStateCanvas().pack();
					}
					
				}else {
					if(contentPlan.getInitialStateCanvas()!=null) {
						contentPlan.getInitialStateCanvas().getState().setShownCond(false);
						contentPlan.getInitialStateCanvas().pack();

					}
					if(contentPlan.getGoalStateCanvas()!=null) {
						contentPlan.getGoalStateCanvas().getState().setShownCond(false);
						contentPlan.getGoalStateCanvas().pack();
					}
				}
				
				
				

			}
		} );
		ToolItem savePlan=new ToolItem(t,SWT.PUSH);
		icon = new Image(getDisplay(), "img/save.ico");
		savePlan.setImage(icon);
		savePlan.setToolTipText("Save Plan");
		savePlan.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				
				SavePlanDialog dialog=new SavePlanDialog(getShell(), SWT.SAVE);
				dialog.setPlanContent(getPlan());
				dialog.createContent();
			}
		});
		
		
		
		
		
		ToolItem PDFPreview=new ToolItem(t,SWT.PUSH);
		icon = new Image(getDisplay(), "img/pdf.ico");
		PDFPreview.setImage(icon);
		
		PDFPreview.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				
				
				
	
				ConsoleViewPlan consoleViewPlan=consoleView.getConsoleViewPlan();
				//consoleViewPlan.createDirector();
				
				ConsoleViewDomain consoleViewDomain=consoleView.getConsoleViewDomain();
//				consoleViewDomain.saveFile();

				
				SaveLAtexCode dialog=new SaveLAtexCode(getShell(), SWT.SAVE);
				dialog.setConsoleViewPlan(consoleViewPlan);
				dialog.setConsoleViewDomain(consoleViewDomain);
				dialog.createContent();
				
				String pdfFile=dialog.getFileName();
				pdfFile=pdfFile.substring(0, pdfFile.length()-3);
				pdfFile=pdfFile+"pdf";
				PdfConverter pdfConverter=new PdfConverter(dialog.getFilterPath()+"/"+pdfFile);
				pdfConverter.execute();
				
				pdfFile=dialog.getFileName();
				pdfFile=pdfFile.substring(0, pdfFile.length()-3);
				pdfFile=pdfFile+"png";
				pdfView.draw(dialog.getFilterPath()+"/"+pdfFile);
				
				//consoleViewPlan.saveFile();
				
				
				
				
				
				
				try {

					String cmd1="cd "+dialog.getFilterPath();
					//dialog.getFileName();
					String cmd2="pdflatex "+dialog.getFileName()+" -synctex=1 -interaction=nonstopmode";
					String cmd3="xdg-open LatexPlan.pdf";
							
					Process process = Runtime.getRuntime().exec(new String[] { "bash", "-c",
							cmd1+" && "+cmd2+" && "+cmd3});


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
			
				
				
				
			}
		});
		
		ToolItem i = new ToolItem(t, SWT.PUSH);
		i.setToolTipText("add a new Plan");
		 icon = new Image(getDisplay(), "img/add-documents.png");
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
