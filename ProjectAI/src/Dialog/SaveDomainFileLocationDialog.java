package Dialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.text.PlainView;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

import Action.Action;
import Action.GlobalValue;
import View.DomainView;

public class SaveDomainFileLocationDialog extends FileDialog{
 
	
		String fileName;
		File fileLog;
		File directory;
		ArrayList<Action> updateActionListDomain;
		DomainView domainView;
		PlainView plainView;
		File dirLog;
		File dirPlan;
	 
		
		public SaveDomainFileLocationDialog(Shell parent, int style) {
			super(parent, style);
			// TODO Auto-generated constructor stub
		}
		
		public void createContent() {
			createDirectorLog();
			String [] filterNames = new String [] {"All Files (*)"};
			String [] filterExtensions = new String [] {"*.txt", "*"};
			String filterPath = System.getProperty("user.home")+"/TDP"+"/dirLog";
			String platform = SWT.getPlatform();
			if (platform.equals("win32")) {
				filterNames = new String [] {"Image Files", "All Files (*.*)"};
				filterExtensions = new String [] {"*.gif;*.png;*.bmp;*.jpg;*.jpeg;*.tiff", "*.*"};
				filterPath = "c:\\";
			}
			setFilterNames (filterNames);
			setFilterExtensions (filterExtensions);
			setFilterPath (filterPath);
			setFileName ("domain");
			System.out.println ("Save to: " +open ());	
			

			
			createFileLog(getFileName());
			ArrayList<Object> data = new ArrayList<Object>();
			data.add(prepareGlobalValueArray());
			data.add(updateActionListDomain);
			if (domainView.getInitialStateCanvas() != null) {
				data.add(domainView.getInitialStateCanvas().getState());
			} else {
				data.add(null);
			}
			if (domainView.getGoalStateCanvas() != null) {
				data.add(domainView.getGoalStateCanvas().getState());
			} else {
				data.add(null);
			}
			
			
			WriteObjectToFile(data);
			
		}

		public void setDomainView(DomainView domainView) {
			this.domainView = domainView;
		}
		
		
		public ArrayList<Object> prepareGlobalValueArray(){
			ArrayList<Object> arrayList=new ArrayList<>();
			
			arrayList.add(GlobalValue.isHeightOfAction);
			arrayList.add(GlobalValue.heightOfAction);
			
			
			arrayList.add(GlobalValue.isWidthOfAction);
			arrayList.add(GlobalValue.widthOfAction);
			
		
			
			arrayList.add(GlobalValue.isLengthsOfEmptyTasks);
			arrayList.add(GlobalValue.lengthsOfEmptyTasks);
			
			arrayList.add(GlobalValue.isLengthsOfPrecs);
			arrayList.add(GlobalValue.lengthsOfPrecs);
			
			arrayList.add(GlobalValue.isLengthsOfEffs);
			arrayList.add(GlobalValue.lengthsOfEffs);
			
			arrayList.add(GlobalValue.isLengthsOfConds);
			arrayList.add(GlobalValue.lengthsOfConds);
			return arrayList;
		}
	
		public void setUpdateActionListDomain(ArrayList<Action> updateActionListDomain) {
			this.updateActionListDomain = updateActionListDomain;
		}

		public void createFileLog(String name) {
			String filepath = dirLog.getAbsolutePath();
			fileLog = new File(filepath, name);
			
			if (!fileLog.exists()) {
				try {
					fileLog.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				
			}

		}
		
		public void createDirectorLog() {
			String filepath = System.getProperty("user.home");
			directory = new File(filepath + "/TDP");
			dirLog = new File(filepath + "/TDP" + "/dirLog");

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

		}
		
		public void WriteObjectToFile(Object serObj) {

			try {
				FileOutputStream fileOut = new FileOutputStream(fileLog.getAbsolutePath());
				ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
				objectOut.writeObject(serObj);
				objectOut.close();
				System.out.println("The Object  was succesfully written to a file");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		@Override
		protected void checkSubclass() {
			
		}
		
	@Override
	public String open() {

		String fileName = null;

		boolean done = false;

		while (!done) {

			fileName = super.open();
			if (fileName == null) {

				done = true;
			} else {

				File file = new File(fileName);
				if (file.exists()) {

					MessageBox mb = new MessageBox(super.getParent(), SWT.ICON_WARNING | SWT.YES | SWT.NO);

					mb.setMessage(fileName + " already exists. Do you want to replace it?");

					done = mb.open() == SWT.YES;
				} else {

					done = true;
				}
			}
		}
		return fileName;
	}

}
