/**
 * 
 */
package logic;

/**
 * @author nadir
 *
 */
public interface IEventArgs {
	public static final IEventArgs EMPTY = new EmptyEventArgs();

	public static class EmptyEventArgs implements IEventArgs {
	}

}
