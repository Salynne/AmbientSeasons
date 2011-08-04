package me.olloth.lordned.seasons.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class HMapSL {

	public static void save(Object obj, String path) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
			oos.writeObject(obj);
			oos.flush();
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Object load(String path) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
			Object result = ois.readObject();
			ois.close();
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
