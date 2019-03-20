package State;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import org.eclipse.swt.graphics.Point;

import PlanPart.LinkCanvas;
import PlanPart.Oval;
import PlanPart.PlanContent;

public class LoadLink {

	PlanContent planContent;
	String path;
	ArrayList<Object> data;
	
	public LoadLink(PlanContent planContent) {
		this.planContent=planContent;
	}
	public void draw() {
		
		System.out.println(planContent.getOvalCounter().getListOval().size());
		loadLinkStored();
		
		
		
	}

	private void loadLinkStored() {
		
		ArrayList<Object> arraylink=planContent.getLinkStored();
		ArrayList<Object> info;
		for(int j=0;j<arraylink.size();j++) {
			info=(ArrayList<Object>) arraylink.get(j);
			Point p1=(Point) info.get(0);
			Oval oval1=searchOval(p1);
			Point p2=(Point) info.get(1);
			Oval oval2=searchOval(p2);
			
			LinkCanvas link=new LinkCanvas(planContent);
			link.setOval1(oval1);
			link.setOval2(oval2);
			link.drawLine();
		}
		
	}
	
	
	private Oval searchOval(Point p1) {
		Oval o = null;
		ArrayList<Oval> ovalList=planContent.getOvalCounter().getListOval();
		for(Oval oval:ovalList){
			if(oval.getP().equals(p1)) {
				return oval;
			}
		}
		
		return o;
	}
}
