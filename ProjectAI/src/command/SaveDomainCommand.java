package command;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Action.GlobalValue;
import View.DomainView;
/**
 * Command which allows to open the dialog for saving the current domain.
 * @author nadir
 * */
public class SaveDomainCommand  implements ICommand{

	private DomainView domainView;
	File dirDomain;
	File directory;
	File fileLog;
	
	@Override
	public boolean canExecute(Object var1, Object var2) {
		if(var1 instanceof String) {
			if(var2 instanceof DomainView) {
				return true;
			}
		}
		return false;
	}

	public void setDomainView(DomainView d) {
		this.domainView=d;
	}
	
	@Override
	public void execute(Object var1, Object var2) {
		
		String name = null;
		if(canExecute(var1, var2)) {
			name=(String)var1;
			domainView=(DomainView)var2;
			createFileLog(name);
			ArrayList<Object> data = new ArrayList<Object>();
			data.add(prepareGlobalValueArray());
			data.add(domainView.getTreeAction().getActionList());
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
		
		
	
	}

	public void copyFileDomain(String path,DomainView domainView) {
		createDirectorLog();
		execute("tempDomain.txt", domainView);
		path=path+"/tempDomain.txt";
		fileLog.renameTo(new File(path));
		
		
	}
	
	
	private ArrayList<Object> prepareGlobalValueArray(){
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
		
		arrayList.add(GlobalValue.formIsBlackPr);
		arrayList.add(GlobalValue.cornerIsSquarePr);
		arrayList.add(GlobalValue.borderIsFatPr);
		
		arrayList.add(GlobalValue.formIsBlackAbst);
		arrayList.add(GlobalValue.cornerIsSquareAbst);
		arrayList.add(GlobalValue.borderIsFatAbst);
		arrayList.add(GlobalValue.colorP);
		arrayList.add(GlobalValue.colorAbst);
		
		
		return arrayList;
	}



	public void createFileLog(String name) {
		String filepath = dirDomain.getAbsolutePath();
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
		dirDomain = new File(filepath + "/TDP" + "/dirDomain");

		// if the directory does n exist, create it
		if (!directory.exists()) {
			boolean result = false;

			try {
				directory.mkdir();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
			}
		}

		if (!dirDomain.exists()) {
			boolean result = false;

			try {
				dirDomain.mkdir();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
			}
		}

	}
	
	public void WriteObjectToFile(Object serObj) {

		try {
			FileOutputStream fileOut = new FileOutputStream(fileLog.getAbsolutePath());
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(serObj);
			objectOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
