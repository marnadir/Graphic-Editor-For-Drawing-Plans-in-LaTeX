import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessCommand {
	public static void main(String[] args) {
		Process process;
//		ProcessBuilder pb = new ProcessBuilder("cd ");
//		pb.directory(new File("/home/nadir/Desktop/"));
//
//		process = pb.start();
//		process.waitFor();
		
		try {


			String[] cmd1 =new String[] {"pdflatex test.tex"};
			process=Runtime.getRuntime().exec(cmd1, null, new File("/home/nadir/Desktop/Test"));
			process.waitFor();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
				builder.append(System.getProperty("line.separator"));
			}
			String result = builder.toString();
			System.out.println(result);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
