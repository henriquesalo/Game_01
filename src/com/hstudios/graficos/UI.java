package com.hstudios.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.hstudios.entities.Player;
import com.hstudios.main.Game;

public class UI {

	
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(10,4,50,8);
		g.setColor(Color.green);
		g.fillRect(10,4,(int)((Game.player.life/Game.player.maxLife)*50),8);
		g.setColor(Color.white);
		g.setFont(new Font("arial",Font.BOLD,8));
		g.drawString((int)Game.player.life+"/"+(int)Game.player.maxLife, 21,11);
	}
	
}
