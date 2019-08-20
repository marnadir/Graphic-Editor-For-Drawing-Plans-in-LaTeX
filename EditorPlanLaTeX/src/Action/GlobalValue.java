package Action;

import java.io.Serializable;
import java.util.ArrayList;

public class GlobalValue implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2623317763947702563L;
	public static boolean isHeightOfAction;
	public static String heightOfAction;
	public static boolean isWidthOfAction;
	public static String widthOfAction;
	public static boolean isLengthsOfEmptyTasks;
	public static String lengthsOfEmptyTasks=".35";	
	public static boolean isLengthsOfPrecs;
	public static String lengthsOfPrecs;
	public static boolean isLengthsOfEffs;
	public static String lengthsOfEffs;
	public static boolean isLengthsOfConds;
	public static String lengthsOfConds;
	public static boolean  formIsBlackPr=true;
	public static boolean cornerIsSquarePr=true;
	public static boolean borderIsFatPr=true;
	public static boolean  formIsBlackAbst=true;
	public static boolean cornerIsSquareAbst=false;
	public static boolean borderIsFatAbst=false;
	public static String colorP="None";
	public static String colorAbst="None";
	
	
	public static void setValue(ArrayList<Object> g) {
		isHeightOfAction = (boolean) g.get(0);
		heightOfAction = (String) g.get(1);
		isWidthOfAction =(boolean) g.get(2);
		widthOfAction = (String)g.get(3);
		isLengthsOfEmptyTasks =(boolean) g.get(4);
		lengthsOfEmptyTasks =(String)g.get(5);
		isLengthsOfPrecs = (boolean)g.get(6);
		lengthsOfPrecs = (String)g.get(7);
		isLengthsOfEffs =(boolean) g.get(8);
		lengthsOfEffs = (String)g.get(9);
		isLengthsOfConds =(boolean)g.get(10);
		lengthsOfConds =(String)g.get(11);
		formIsBlackPr=(boolean)g.get(12);
		cornerIsSquarePr=(boolean)g.get(13);
		borderIsFatPr=(boolean)g.get(14);
		formIsBlackAbst=(boolean)g.get(15);
		cornerIsSquareAbst=(boolean)g.get(16);
		borderIsFatAbst=(boolean)g.get(17);
		colorP=(String) (g.get(18));
		colorAbst=(String) (g.get(19));
		

	}
	

	
}
