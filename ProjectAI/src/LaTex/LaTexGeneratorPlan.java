package LaTex;



public class LaTexGeneratorPlan {

	public LaTexGeneratorPlan() {
		// TODO Auto-generated constructor stub
	}
	
	public String getLatexIntro() {
		StringBuilder sb = new StringBuilder();
		sb.append("//beginn{tikzpicture}");
		sb.append("\n");
		return sb.toString();
	}
	
	public String getLatexEnd() {
		StringBuilder sb = new StringBuilder();
		sb.append("//end{tikzpicture}");
		sb.append("\n");
		return sb.toString();
	}
}
