package com.GO.frame.ui.common_ui;

import com.GO.frame.util.Position;

import java.awt.*;

public class StringWriter {

	public static void writeString(Graphics2D g, String s, Font font, Color color, int x, int y) {

		g.setFont(font);
		g.setColor(color);
		g.drawString(s, x, y);

	}
	
	public static void writeString(Graphics2D g, String s, Font font, Color color, Position position) {
	
		writeString(g, s, font, color, position.getX(), position.getY());
	}

	public static void writeString(Graphics2D g, String s, int x, int y) {

		writeString(g, s, new Font("Osaka", Font.BOLD,12), Color.BLACK, x, y);

	}
	
	public static void writeString(Graphics2D g, String s, Position position) {
		
		writeString(g, s, new Font("Osaka", Font.PLAIN,12), Color.BLACK, position.getX(), position.getY());
	
	}

}
