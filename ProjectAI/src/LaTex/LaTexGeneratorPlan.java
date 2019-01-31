package LaTex;



public class LaTexGeneratorPlan {

	public LaTexGeneratorPlan() {
		// TODO Auto-generated constructor stub
	}
	
	public String getLatexIntro() {
		StringBuilder sb = new StringBuilder();
		sb.append("\\documentclass{beamer}");
		
		sb.append("\\usepackage[T1]{fontenc}"+"\n");
		
		sb.append("\\usepackage{lmodern}"+"\n");
		
		sb.append("\\usepackage{tikz}"+"\n");
		
		sb.append("\\usetikzlibrary{aiplans}"+"\n");

		sb.append("\\usetikzlibrary{domain}"+"\n");

		
		sb.append("\\tikzset{\n" + 
				"  every picture/.style = {font issue=\\tiny},\n" + 
				"  font issue/.style = {execute at begin picture={#1\\selectfont}}\n" + 
				"}"+"\n");

		sb.append("\\begin{tikzpicture}"+"\n");

	
		return sb.toString();
	}
	
	public String getLatexEnd() {
		StringBuilder sb = new StringBuilder();
		sb.append("\\end{tikzpicture}");
		sb.append("\\end{document}");
		sb.append("\n");
		return sb.toString();
	}
	
	
}
