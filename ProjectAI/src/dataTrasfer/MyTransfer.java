package dataTrasfer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import Action.Action;

/**
 * converts instance of object MyType into sequence of byte and vice versa
 * 
 * @see MyType
 * @author nadir
 * */

	
public class MyTransfer extends ByteArrayTransfer {

	private static final String MYTYPENAME = "name_for_my_type";

	private static final int MYTYPEID = registerType(MYTYPENAME);

	private static MyTransfer _instance = new MyTransfer();

	public static MyTransfer getInstance() {
		return _instance;
	}

	public void javaToNative(Object object, TransferData transferData) {
		if (!checkMyType(object) || !isSupportedType(transferData)) {
			DND.error(DND.ERROR_INVALID_DATA);
		}
		MyType[] myTypes = (MyType[]) object;
		try {
			// write data to a byte array and then ask super to convert to pMedium
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			DataOutputStream writeOut = new DataOutputStream(out);
			for (int i = 0, length = myTypes.length; i < length; i++) {
				byte[] buffer = myTypes[i].name.getBytes();
				writeOut.writeInt(buffer.length);
				writeOut.write(buffer);

				writeOut.writeInt(myTypes[i].prec.size());

				for (String element : myTypes[i].prec) {
					writeOut.writeUTF(element);
				}

				writeOut.writeInt(myTypes[i].eff.size());

				for (String element : myTypes[i].eff) {
					writeOut.writeUTF(element);
				}

			}
			byte[] buffer = out.toByteArray();
			writeOut.close();
			super.javaToNative(buffer, transferData);
		} catch (IOException e) {
		}
	}

	public Object nativeToJava(TransferData transferData) {
		if (isSupportedType(transferData)) {
			byte[] buffer = (byte[]) super.nativeToJava(transferData);
			if (buffer == null)
				return null;

			MyType[] myData = new MyType[0];
			try {
				ByteArrayInputStream in = new ByteArrayInputStream(buffer);
				DataInputStream readIn = new DataInputStream(in);
				while (readIn.available() > 0) {
					MyType action = new MyType();
					int size = readIn.readInt();
					byte[] name = new byte[size];
					readIn.read(name);
					action.name = new String(name);

					size = readIn.readInt();
					action.prec = new ArrayList<>();
					while (size > 0) {
						action.prec.add(readIn.readUTF());
						size--;
					}

					size = readIn.readInt();
					action.eff = new ArrayList<>();
					while (size > 0) {
						action.eff.add(readIn.readUTF());
						size--;
					}

					MyType[] newMyData = new MyType[myData.length + 1];
					System.arraycopy(myData, 0, newMyData, 0, myData.length);
					newMyData[myData.length] = action;
					myData = newMyData;
				}
				readIn.close();
			} catch (IOException ex) {
				return null;
			}
			return myData;
		}

		return null;
	}

	protected String[] getTypeNames() {
		return new String[] { MYTYPENAME };
	}

	protected int[] getTypeIds() {
		return new int[] { MYTYPEID };
	}

	boolean checkMyType(Object object) {
		if (object == null || !(object instanceof MyType[]) || ((MyType[]) object).length == 0) {
			return false;
		}
		MyType[] myTypes = (MyType[]) object;
		for (int i = 0; i < myTypes.length; i++) {
			if (myTypes[i] == null || myTypes[i].name == null || myTypes[i].name.length() == 0) {
				return false;
			}
		}
		return true;
	}

	protected boolean validate(Object object) {
		return checkMyType(object);
	}
}
	 

