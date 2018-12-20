package GraphPart;

import java.util.ArrayList;



public class OvalCounter {

	ArrayList<Oval> listOval=new ArrayList<>();
	
	public OvalCounter() {
		// TODO Auto-generated constructor stub
	}
	
	public void add(Oval o) {
		for (int i = 0; i < listOval.size(); i++) {
			if (listOval.get(i).getCond().equals(o.getCond())
					&& listOval.get(i).getNameAction().equals(o.getNameAction())) {
				return;
			}
		}
		listOval.add(o);
	}

	public ArrayList<Oval> getListOval() {
		return listOval;
	}
	
	
}
