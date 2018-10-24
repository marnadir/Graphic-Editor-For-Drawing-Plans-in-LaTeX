/**
 * 
 */
package logic;



public interface ICommand {
	
	Commands commands= new Commands();
	public static final ICommand OPEN = commands.getOpenCommand();
	public static final ICommand SAVE=commands.getSaveCommand();
	public static final ICommand SAVEAS=commands.getSaveAsCommand();
	public static final ICommand EXIT=commands.getExitCommand();
	

	
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
