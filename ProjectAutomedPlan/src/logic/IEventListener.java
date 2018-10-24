/**
 * 
 */
package logic;

public interface IEventListener<T extends IEventArgs> {
	public void onEvent(Object var1, T var2);

	
//	public static final class Delegate<T extends IEventArgs> extends AbstractDelegate<Delegate<T>, IEventListener<T>>
//			implements
//				IEventListener<T> {
//		public Delegate(Object object, Object object2, IEventListener<T> iEventListener) {
//			super(object, object2, iEventListener);
//		}
//
//		private Delegate(Delegate<T>[] arrdelegate) {
//			super((AbstractDelegate[]) arrdelegate);
//		}
//
//		protected Delegate<T>[] createDelegateArray(int n) {
//			return new Delegate[n];
//		}
//
//		protected IEventListener<T>[] createHandlerArray(int n) {
//			return new IEventListener[n];
//		}
//
//		protected Delegate<T> createDelegateImpl(Delegate<T>[] arrdelegate) {
//			return new Delegate<T>(arrdelegate);
//		}
//
//		@Internal
//		@Override
//		public void onEvent(Object object, T t) {
//			this.dynamicInvoke(new Object[]{object, t});
//		}
//
//		protected Object dynamicInvokeImpl(IEventListener<T> iEventListener, Object... arrobject) {
//			iEventListener.onEvent(arrobject[0], (IEventArgs) arrobject[1]);
//			return null;
//		}
//
//		@Internal
//		public static <T extends IEventArgs> Delegate<T> create(IEventListener<T> iEventListener) {
//			if (iEventListener instanceof Delegate || null == iEventListener) {
//				return (Delegate) iEventListener;
//			}
//			return new Delegate<T>(iEventListener, null, iEventListener);
//		}
//	}

}
