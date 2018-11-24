package logic;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class Action {

	String name;
	ArrayList<String> prec;
	ArrayList<String> effect;
	Canvas canvas;
	Composite contentCanvas;
	int max;

	
	public Action(String name,ArrayList<String> prec, ArrayList<String> eff) {
		this.name=name;
		this.prec=prec;
		this.effect=eff;
		
	}
	
	public void draw(Composite comp) {
		if (canvas != null) {
			canvas.redraw();
		} else {
			this.contentCanvas = comp;
			canvas = new Canvas(comp, SWT.ALL);
			// canvasSo.setLayout(new FillLayout());
//			canvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			//canvas.setSize(comp.getSize().x/2,comp.getSize().y/2);
			canvas.layout();

		}
		canvas.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {

				int num= comp.getChildren().length;
			
				
				int numPrec = prec.size();
				int numEff=effect.size();

				
				
				int startX = canvas.getLocation().x ;
				int startY = canvas.getLocation().y ;
				
				System.out.println(startX);
				System.out.println(startY);
				System.out.println(num);


				
				int heightRect=40;
				max=numPrec;
				if(numEff>numPrec) {
					max=numEff;
				}
				
				if(max>1) {
					heightRect=30+max*10;
				}
				
				int sizeString=name.length()*12;
				
				Rectangle rect=new Rectangle(startX, startY, sizeString,heightRect);
				
				
				e.gc.drawRectangle(rect);
				
				int l=rect.x+rect.width/6;
				e.gc.drawString(name, l, rect.y+rect.height/3);
				

				int posY = rect.y + 10;
				for (int i = 0; i < numPrec; i++) {

					e.gc.drawLine(rect.x, posY, rect.x - 35, posY);
					String string = prec.get(i);
					e.gc.drawString(string, rect.x - (5+string.length()*10), posY - 20, false);

					posY = posY + 30;

				}
				
		
			
				
				
				
				posY = rect.y + 10;
				for (int i = 0; i < numEff; i++) {
					
					int x=rect.x+rect.width;
					e.gc.drawLine(x, posY, x+30, posY);
					String string = effect.get(i);
					e.gc.drawString(string, x + 10, posY - 20, false);

					posY = posY + 30;

				}
				
				

				// e.gc.drawRectangle(r);
			}
		});
		
		int sizeString=name.length()*12+120;
		canvas.setSize(sizeString, max+120);
		canvas.setBackground(comp.getDisplay().getSystemColor(SWT.COLOR_RED));
		
		
     
		
		
	}
	
	public String getName() {
		
		return name;
	}
	
	public void elimanate() {
//		canvasSo.redraw();
//		canvasSo.layout();
		canvas.dispose();
		
	}


}
