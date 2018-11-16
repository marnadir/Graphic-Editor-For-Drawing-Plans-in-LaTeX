package logic;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

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
			
				int numCond=precPos.size();
				e.gc.drawRectangle(composite.get, y, width, height);
			}
		});
		
		composite.pack();
	}
	

	
	//TODO method that write the latex code 
}
