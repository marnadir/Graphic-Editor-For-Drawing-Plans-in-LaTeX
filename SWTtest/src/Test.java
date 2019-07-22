import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Test {

	public static void main(String[] args) {
		Runnable helloRunnable = new Runnable() {
		    public void run() {
		        System.out.println("Hello world");
		    }
		};

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(helloRunnable, 0, 3, TimeUnit.SECONDS);
	}
}
