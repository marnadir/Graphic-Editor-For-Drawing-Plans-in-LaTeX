package logic;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class InitialState {

	ArrayList<String> precPos;
//	ArrayList<String> precNeg;
	
	public InitialState(ArrayList<String> pos) {
		this.precPos=new ArrayList<>(pos);
//		this.precNeg=new ArrayList<>(neg);
	}
	
	//TODO pay attention for contraction, example we have A, can't be added notA
	public ArrayList<String> getPrec() {
		return precPos;
		
	}
	 
	//TODO method allow to draw the initial state
	
	public void draw(Composite composite) {
		Canvas canvas=new Canvas(composite, SWT.ALL);
		canvas.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				// TODO Auto-generated method stub
				Rectangle rect=new Rectangle(50, 50, 60, 30);
				
				e.gc.drawRectangle(rect);
			}
		});
		System.out.println("disegnato");
		
	}
	

	
	//TODO method that write the latex code 
}
