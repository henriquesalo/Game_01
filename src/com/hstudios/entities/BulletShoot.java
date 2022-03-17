package com.hstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.hstudios.main.Game;
import com.hstudios.world.Camera;

public class BulletShoot extends Entity{

	private int dx;
	private int dy;
	private double spd = 4;
	
	private int life = 35,curLife = 0;
	
	public BulletShoot(int x, int y, int width, int heigth, BufferedImage sprite, int dx, int dy) {
		super(x, y, width, heigth, sprite);
		this.dx = dx;
		this.dy = dy;
	}
	
	public void tick() {
		x+=dx*spd;
		y+=dy*spd;
		curLife++;
		if(curLife == life) {
			Game.bullets.remove(this);
			return;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y,width,height);
	}
	
}
