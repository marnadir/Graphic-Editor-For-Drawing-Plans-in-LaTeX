package GraphPart;

import java.util.ArrayList;



public class OvalCounter {

	ArrayList<Oval> listOval=new ArrayList<>();
	
	public OvalCounter() {
		// TODO Auto-generated constructor stub
	}
	
	public void add(Oval o) {
		for (int i = 0; i < listOval.size(); i++) {
			if (listOval.get(i).getCond().equals(o.getCond())) {
//				if(listOval.get(i).getAction().getName().equals(o.getAction().getName())) {
//					return;
//				}
				
				/*per non so quale motivo viene chiamato due volte add(Oval o)
				 per cio devo inserlo solo una volta*/
				
				/*that means that belongs to action*/
				if(listOval.get(i).getNode().getAction()!=null) {
					if(listOval.get(i).getNode().getAction().getName().equals(o.getNode().getAction().getName())) {
						return;
					}
					
					/*that means that belongs to state*/

				}else if (listOval.get(i).getState()!=null){
					if(listOval.get(i).getState().getName().equals(o.getState().getName())) {
						return;
					}
				}
			
			}
					 
			}
		
		listOval.add(o);
	}

	public ArrayList<Oval> getListOval() {
		return listOval;
	}
	
	
}
