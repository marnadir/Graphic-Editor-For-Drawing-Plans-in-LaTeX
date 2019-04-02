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
import PlanPart.OrderConstrainCanvas;
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
		Image icon = new Image(getDisplay(), "img/refresh.png");
		updateTextPlan.setImage(icon);
		
	

		ToolItem saveLatex=new ToolItem(toolBarPlan,SWT.PUSH);
		saveLatex.setText("save");
		icon = new Image(getDisplay(), "img/save.ico");
		saveLatex.setImage(icon);
		
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
	
		
		saveLatex.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				updateView();
				saveFile();
				
				
			}
		});
		
		
		
		
		
		
	}
	
	public void saveFile() {
			createDirector();
			updateView();
			String filepath = dirPlan.getAbsolutePath();
			file = new File(filepath,"LatexPlan.tex");
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (file.isFile()) {
				WriteTextToFile(textPlan.getText());
			}
			
			updateView();

	}
	
	public void copyTikzLibrary()  {
		File file=new File("TikzLibrary/tikzlibraryaiplans.code.tex");
		File file2=new File(dirPlan.getAbsolutePath(), "tikzlibraryaiplans.code.tex");
		try {
			if(!file.exists()) {
				file2.createNewFile();

			}
			Files.copy(file.toPath(), file2.toPath(),StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		
		
	}
	
	
	
	public void createDirector() {
		String filepath = System.getProperty("user.home");
		directory = new File(filepath + "/TDP");
		File dirLatex = new File(filepath + "/TDP" + "/dirLatex");
		
		BasicFileAttributes attr;
		String text = null;
		try {
			attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
			FileTime fileTime = attr.lastModifiedTime();

			DateFormat df = new SimpleDateFormat("MM/dd/yy");
			String dateCreated = df.format(fileTime.toMillis());

			df = new SimpleDateFormat("HH:mm:ss");
			String dateCreated2 = df.format(fileTime.toMillis());

			text =  dateCreated + " " + dateCreated2;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		dirPlan=new File(dirLatex.getAbsolutePath()+"/"+planView.getSelection().getText()+text);
		copyTikzLibrary();
		

		// if the directory does not exist, create it
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

		
		if (!dirPlan.exists()) {
			System.out.println("creating directory: " + dirPlan.getName());
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
