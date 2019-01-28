package GraphPart;

import java.util.ArrayList;



public class OvalCounter {

	ArrayList<Oval> listOval=new ArrayList<>();
	
	public OvalCounter() {
		// TODO Auto-generated constructor stub
	}

	public void addA(Oval o) {
		for (int i = 0; i < listOval.size(); i++) {
			if ((listOval.get(i).getCond().equals(o.getCond()))) {

				/*
				 * per non so quale motivo viene chiamato due volte add(Oval o) per cio devo
				 * inserlo solo una volta
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
	}
	
	
	public void addSt(Oval o) {
		for (int i = 0; i < listOval.size(); i++) {
			if ((listOval.get(i).getCond().equals(o.getCond()))) {

				/*
				 * per non so quale motivo viene chiamato due volte add(Oval o) per cio devo
				 * inserlo solo una volta
				 */

				if (listOval.get(i).getStateCanvas() != null) {
					if (listOval.get(i).getStateCanvas().getName().equals(o.getStateCanvas().getName())) {
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
