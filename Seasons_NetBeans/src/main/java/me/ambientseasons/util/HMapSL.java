package me.ambientseasons.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class HMapSL {

	/**
	 * Saves an object to a file.
	 * @param obj - Object to save
	 * @param path - Path of the object
	 */
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

	/**
	 * Returns an object from a file
	 * @param path - Path of the object
	 * @return - Object from the file
	 */
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
