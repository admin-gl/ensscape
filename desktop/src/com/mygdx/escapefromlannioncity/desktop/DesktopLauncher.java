package com.mygdx.escapefromlannioncity.desktop;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.escapefromlannioncity.EscapeFromLannionCity;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Escape From Lannion City";
		config.width = 1000;
		config.height = 666;
		new LwjglApplication(new EscapeFromLannionCity(), config);
	}
}
