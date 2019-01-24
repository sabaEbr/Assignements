package com.GO.game.util;

import java.awt.Font.*;
import java.awt.Color.*;
import java.awt.Graphics.*;
import java.awt.*;
import com.GO.game.entity.Position;

public class StringWriter {

	public static void writeString(Graphics2D g, String s, Font font, Color color, int x, int y) {
		
		g.setFont(font);
		g.setColor(color);
		g.drawString(s, x, y); 
	
	}
	
	public static void writeString(Graphics2D g, String s, Font font, Color color, Position position) {
	
		writeString(g, s, font, color, position.getX(), position.getY());
	}
	
	public static void writeString(Graphics2D g, String s, Position position) {
		
		writeString(g, s, new Font("TimesRoman", Font.BOLD,14), Color.BLACK, position.getX(), position.getY());
	
	}

}
