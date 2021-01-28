package com.mygdx.escapefromlannioncity.desktop;


import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglGraphics;
import com.mygdx.escapefromlannioncity.EscapeFromLannionCity;
import org.lwjgl.Sys;

import java.awt.*;
import java.util.Locale;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Escape From Lannion City";
		String OS = System.getProperty("os.name").toLowerCase();
		boolean IS_MAC = (OS.indexOf("mac") >= 0);
		//booléens untiles si on on a besoin de traitements particuliers
		//boolean IS_WINDOWS = (OS.indexOf("win") >= 0);
		boolean IS_UNIX = (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
		if (IS_MAC || IS_UNIX){
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			config.width = screenSize.width;
			config.height = screenSize.height-54;
			config.resizable = false;
			config.fullscreen = false;
			if(IS_UNIX){
				config.addIcon("icon32.png", Files.FileType.Internal);
			}
		} else {
			config.width = 256;
			config.height = 144;
			config.fullscreen = true;
		}

		new LwjglApplication(new EscapeFromLannionCity(), config);
	}
}
