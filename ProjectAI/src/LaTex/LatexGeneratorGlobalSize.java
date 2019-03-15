package LaTex;

import Action.GlobalValue;

public class LatexGeneratorGlobalSize {
	
	public LatexGeneratorGlobalSize() {
	}
		
	public String getLatex() {
		StringBuilder sb = new StringBuilder();
		
		
		sb.append("\\newcommand{\\LengthsOfEmptyTasks}   "+"{"+GlobalValue.lengthsOfEmptyTasks+"cm}");
		sb.append("\n");

	
		
		
		if(GlobalValue.isWidthOfAction) {
			sb.append("\\newcommand{\\WidthOfActions}   "+"{"+GlobalValue.widthOfAction+"cm}");
			sb.append("\n");

		}
		
		if(GlobalValue.isHeightOfAction) {
			sb.append("\\newcommand{\\HeightOfActions}   "+"{"+GlobalValue.heightOfAction+"cm}");
			sb.append("\n");

		}

		if(GlobalValue.isLengthsOfPrecs) {
			sb.append("\\newcommand{\\LengthsOfPrecs}   "+"{"+GlobalValue.lengthsOfPrecs+"cm}");
			sb.append("\n");

		}
		
		if(GlobalValue.isLengthsOfEffs) {
			sb.append("\\newcommand{\\LengthsOfEffs}   "+"{"+GlobalValue.lengthsOfEffs+"cm}");
			sb.append("\n");

		}
		
		if(GlobalValue.isLengthsOfConds) {
			sb.append("\\newcommand{\\LengthsOfConds}   "+"{"+GlobalValue.lengthsOfConds+"cm}");
			sb.append("\n");

		}
		
		
		return sb.toString();
	}

}
