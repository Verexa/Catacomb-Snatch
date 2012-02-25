package com.mojang.mojam;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;

public class Options {
	
    public static final String DRAW_FPS = "drawFps";
    public static final String FULLSCREEN = "fullscreen";
    public static final String GAME_SCALE = "scale";
    public static final String MUSIC = "music";
    public static final String VOLUME = "volume";

    public static final String VALUE_TRUE = "true";
    public static final String VALUE_FALSE = "false";
    
    public static final String CREATIVE = "creative";
    public static final String ALTERNATIVE = "alternative";
    
	private static Properties properties = new Properties();
	
    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static Boolean getAsBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }
    
    public static Boolean getAsBoolean(String key, String defaultValue) {
        return Boolean.parseBoolean(get(key, defaultValue));
    }

    public static float getAsFloat(String key) {
        return Float.parseFloat(get(key));
    }
    
    public static float getAsFloat(String key, String defaultValue) {
        return Float.parseFloat(get(key, defaultValue));
    }
    
	public static void set(String key, String value) {
		properties.setProperty(key, value);
	}
	
    public static void set(String key, boolean value) {
        properties.setProperty(key, String.valueOf(value));
    }

	public static void loadProperties() {
		BufferedInputStream stream;
		try {
			stream = new BufferedInputStream(new FileInputStream(MojamComponent.getMojamDir() + "/options.properties"));
			properties.load(stream);
			stream.close();
		} catch (FileNotFoundException e) {
			// having no properties file is OK
		} catch (IOException e) {
			// something went wrong with the stream
			e.printStackTrace();
		}
	}
	
	public static void saveProperties() {
		BufferedOutputStream stream;
		try {
			File file = new File(MojamComponent.getMojamDir() + "/options.properties");
			if ( !file.exists() ) {
				System.out.println("File not there");
				file.createNewFile();
			}
			stream = new BufferedOutputStream(new FileOutputStream(file));
			// TODO describe properties in comments
			String comments = "";
			properties.store(stream, comments);
		} catch (FileNotFoundException e) {
			// we checked this first so this shouldn't occurs
		} catch (IOException e) {
			// something went wrong with the stream
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the path the game is executed in
	 * @return the absolute path of the jar
	 */
	public static String getJarPath() {
		String path = MojamComponent.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String decodedPath = "";
		try {
			decodedPath = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return decodedPath.substring(0, decodedPath.lastIndexOf("/") + 1);
	}

}
