package View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import Action.Node;
import LaTex.LaTexGeneratorPlan;
import LaTex.LaTexGeneratorStatePlan;
import PlanPart.PlanContent;
import resourceLoader.ResourceLoader;
import PlanPart.LinkCanvas;
import PlanPart.OrderConstrain;

public class ConsoleViewPlan extends Group {
	
	File directory;
	PlanContent contentAction;
	PlanView planView;
	File dirPlan;
	File file;
	Text textPlan;
	

	public ConsoleViewPlan(Composite parent, int style) {
		super(parent, style);
		setText("Plan");
		// TODO Auto-generated constructor stub
	}

	public void setLayout() {
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		setLayout(new GridLayout(1, true));

	}
	
	public void createContent(PlanView planView) {
		
		this.planView=planView;

		ToolBar toolBarPlan = new ToolBar(this, SWT.FLAT | SWT.WRAP | SWT.RIGHT);

		ToolItem updateTextPlan = new ToolItem(toolBarPlan, SWT.PUSH);
		updateTextPlan.setText("update");
		Image icon = new Image(getDisplay(),ResourceLoader.load("img/refresh.png") );
		updateTextPlan.setImage(icon);
		
	
//
//		ToolItem saveLatex=new ToolItem(toolBarPlan,SWT.PUSH);
//		saveLatex.setText("save");
//		icon = new Image(getDisplay(),ResourceLoader.load("img/save.ico") );
//		saveLatex.setImage(icon);
		
		textPlan = new Text(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
		textPlan.insert("update data...");
		textPlan.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		textPlan.pack();

		toolBarPlan.pack();
		/* Plan */
		updateTextPlan.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				updateView();
				
				

			}
		});
	
//		
//		saveLatex.addListener(SWT.Selection, new Listener() {
//			
//			@Override
//			public void handleEvent(Event event) {
//				updateView();
//				
//				
//			}
//		});
					
	}
	public void updateView() {
		textPlan.setText("");

		LaTexGeneratorPlan laTexGeneratorPlan=new LaTexGeneratorPlan();
		textPlan.insert(laTexGeneratorPlan.getLatexIntro());
		
		PlanContent contentAction = (PlanContent)planView.getSelection().getControl();
		
		
		LaTexGeneratorStatePlan generatorStatePlan=new LaTexGeneratorStatePlan();
		textPlan.insert(generatorStatePlan.getLatexPlanCode(contentAction));
		
		ArrayList<Node> updateNodeList = contentAction.getActionInPlan();
		for (int i = 0; i < updateNodeList.size(); i++) {
			updateNodeList.get(i).generateLatexCode(contentAction);
			textPlan.insert(updateNodeList.get(i).getLatexCode());
		}

		ArrayList<LinkCanvas> updateLinkList = contentAction.getLink();
		for (int i = 0; i < updateLinkList.size(); i++) {
			updateLinkList.get(i).generateLatexCode();
			textPlan.insert(updateLinkList.get(i).getLatexCode());
		}

		ArrayList<OrderConstrain> updateOrder = contentAction.getOrds();
		for (int i = 0; i < updateOrder.size(); i++) {
			updateOrder.get(i).generateLatexCode();
			textPlan.insert(updateOrder.get(i).getLatexCode());
		}
		textPlan.insert(laTexGeneratorPlan.getLatexEnd());
	}
	


	public Text getTextPlan() {
		return textPlan;
	}

	public File getDirPlan() {
		return dirPlan;
	}

	public File getFile() {
		return file;
	}

	@Override
	protected void checkSubclass() {
		
	}
}
