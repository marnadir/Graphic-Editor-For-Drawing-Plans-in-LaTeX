package Hope;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

public class Controller {
	Point p1=null;
	Point p2=null;
	Composite parent;
	boolean lineIsCreated=false;
	
	public Controller(Composite parent) {
		this.parent=parent;
	}



	
	public boolean isLineIsCreated() {
		return lineIsCreated;
	}




	public void setLineIsCreated(boolean lineIsCreated) {
		this.lineIsCreated = lineIsCreated;
	}




	public void setectionPoint(int x,int y) {
		if(p1==null) {
			p1=new Point(x, y);
	
		}else if(p2==null){
			p2=new Point(x, y);
			
			
		}
	}
	
	public void deselectionPoint() {
		if(p2 != null) {
			p2=null;
		}else if(p1 != null){
			p1=null;
		}
		
	}
}
