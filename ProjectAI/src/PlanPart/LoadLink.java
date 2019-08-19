package PlanPart;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Point;
/**
 * load  the saved link.
 * @author nadir
 * */
public class LoadLink {

	PlanContent planContent;
	String path;
	ArrayList<Object> data;
	
	public LoadLink(PlanContent planContent) {
		this.planContent=planContent;
	}
	public void draw() {
		loadLinkStored();
	}

	private void loadLinkStored() {
		
		ArrayList<Object> arraylink=planContent.getLinkStored();
		ArrayList<Object> info;
		for(int j=0;j<arraylink.size();j++) {
			info=(ArrayList<Object>) arraylink.get(j);
			if(info.size()>1) {
				Oval oval1=searchOvalCond((ArrayList<String>) info.get(0));
				Oval oval2=searchOvalCond((ArrayList<String>) info.get(1));
				
				LinkCanvas link=new LinkCanvas(planContent);
				link.setOval1(oval1);
				link.setPrec((ArrayList<String>) info.get(0));
				link.setEff((ArrayList<String>) info.get(1));
				link.setOval2(oval2);
				link.drawLine();
				planContent.getLink().add(link);
			}
			
		}
		
	}
	
	
	private Oval searchOvalCond(ArrayList<String> cond) {
		Oval o = null;
		ArrayList<Oval> ovalList=planContent.getOvalCounter().getListOval();
		for(Oval oval:ovalList){
			if(oval.getNode()!=null) {
				if(cond.get(0).equals(oval.getNode().getAction().getName()) && cond.get(1).equals(oval.getCond())) {
					return oval;
				}
			}else {
				if(cond.get(0).equals(oval.getStateCanvas().getState().getName()) && cond.get(1).equals(oval.getCond())) {
					return oval;
				}
			}
			
			
			
			
			
		}
		
		return o;
	}
	
	
	
	
}
