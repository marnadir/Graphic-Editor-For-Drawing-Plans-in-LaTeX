package PlanPart;

import java.util.ArrayList;
/**
 * Used to store all ovals of the plan.
 * @author nadir
 * */


public class OvalCounter {

	ArrayList<Oval> listOval=new ArrayList<>();
	
	public OvalCounter() {
		// TODO Auto-generated constructor stub
	}

	
	/*add oval to the list and represent it,if it isnt drawn*/
	public void addA(Oval o) {
		for (int i = 0; i < listOval.size(); i++) {
			if ((listOval.get(i).getCond().equals(o.getCond()))) {

				/*
				 * i don't now why every oval is added two time, so i need to take care to it
				 */

				/* that means that belongs to action */
				if (listOval.get(i).getNode() != null) {
					if (listOval.get(i).getNode().getID().equals(o.getNode().getID())) {
						return;
					}

					/* that means that belongs to state */

				}

			}

		}

		listOval.add(o);
		o.drawOval();
	}
	
	
	public void addSt(Oval o) {
		for (int i = 0; i < listOval.size(); i++) {
			if ((listOval.get(i).getCond().equals(o.getCond()))) {

				/*
				 * i don't now why every oval is added two time, so i need to take care to it
				 */

				if (listOval.get(i).getStateCanvas() != null) {
					if (listOval.get(i).getStateCanvas().getState().getName().equals(o.getStateCanvas().getState().getName())) {
						return;
					}
				}

			}

		}

		listOval.add(o);
		o.drawOval();
		
	}
	
	
	
	
	

	public void setListOval(ArrayList<Oval> listOval) {
		this.listOval = listOval;
	}

	public ArrayList<Oval> getListOval() {
		return listOval;
	}
	
	
}
