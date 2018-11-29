package logic;

import java.util.ArrayList;

import org.eclipse.swt.SWT;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public abstract class IState {
	ArrayList<String> conds;
	Canvas canvasState;
	Composite contentCanvas;

	public IState(ArrayList<String> pos) {
		this.conds = new ArrayList<>(pos);

	}

	// TODO pay attention for contraction, example we have A, can't be added notA
	public ArrayList<String> getConds() {
		return conds;

	}
	
	public void draw(Composite composite) {
		if (canvasState != null) {
			canvasState.redraw();
		} else {
			this.contentCanvas = composite;
			canvasState = new Canvas(composite, SWT.ALL);
			// canvasSo.setLayout(new FillLayout());
			canvasState.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			canvasState.setSize(composite.getSize());

		}
	}
	


	public void update(ArrayList<String> pos) {
		this.conds = new ArrayList<>(pos);
	}

	public void elimanate() {
//		canvasSo.redraw();
//		canvasSo.layout();
		canvasState.dispose();
		conds.clear();
	}
}
