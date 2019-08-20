package command;
/**
 * interface which defines the possible commands.
 * @author nadir
 * */
public interface ICommand {
		
	public default boolean canExecute( Object object) {
		return this.canExecute(object, null);
	}

	default public boolean canExecute() {
		return this.canExecute(null, null);
	}

	public boolean canExecute(Object var1,  Object var2);

	default public void execute( Object object) {
		this.execute(object, null);
	}

	default public void execute() {
		this.execute(null, null);

	}

	public void execute( Object var1,  Object var2);


	public String getName();

	public static ICommand createCommand() {
		return ICommand.createCommand(null);
	}

	public static ICommand createCommand(String string) {
		return ICommand.createCommand((String) string);
	}
}

