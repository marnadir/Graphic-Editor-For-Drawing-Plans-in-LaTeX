
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;



public class SaveObjectIntoFile {

	public static class Person implements Serializable{
		String name;
		int age;
		String sex;
		
		public Person(String name,int age,String sex) {
			this.name=name;
			this.age=age;
			this.sex=sex;
		}
	}

	
	
	public static void main(String[] args) {

		Person p1 = new Person("John", 30, "Male");
		Person p2 = new Person("Rachel", 25, "Female");

		try {
			String path= System.getProperty("user.home")+"/Desktop";
			File file=new File(path,"myObjects.txt");
			FileOutputStream f = new FileOutputStream(file);
			ObjectOutputStream o = new ObjectOutputStream(f);

			// Write objects to file
			o.writeObject(p1);
			o.writeObject(p2);

			o.close();
			f.close();

			FileInputStream fi = new FileInputStream(file);
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read objects
			Person pr1 = (Person) oi.readObject();
			Person pr2 = (Person) oi.readObject();

			System.out.println(pr1.toString());
			System.out.println(pr2.toString());

			oi.close();
			fi.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
