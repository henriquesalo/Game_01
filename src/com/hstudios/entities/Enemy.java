package com.hstudios.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.hstudios.main.Game;
import com.hstudios.world.Camera;
import com.hstudios.world.World;

public class Enemy extends Entity{
	
	private double speed = 1;
	
	private int frames = 0,maxFrames = 18,index = 0, maxIndex = 2;
	
	private BufferedImage[] sprites;
	
	private int life = 4;
	
	private boolean isDamaged = false;
	private int damageFrames = 10,damageCurrent = 0;

	public Enemy(int x, int y, int width, int heigth, BufferedImage sprite) {
		super(x, y, width, heigth, null);
		sprites = new BufferedImage[3];
		sprites[0] = Game.spritesheet.getSprite(96,16,16,16); 
		sprites[1] = Game.spritesheet.getSprite(112,16,16,16);
		sprites[2] = Game.spritesheet.getSprite(128,16,16,16);
	}
	
	public void tick() {
		if(this.isColiddingWhithPlayer() == false) {
		if((int)x < Game.player.getX() && World.isFree((int)(x+speed),this.getY())
				&& !isColidding((int)(x+speed),this.getY())) {
			x+=speed;
		}
		else if((int)x > Game.player.getX() && World.isFree((int)(x-speed),this.getY())
				&& !isColidding((int)(x-speed),this.getY())) {
			x-=speed;
		}
		else if((int)y < Game.player.getY() && World.isFree(this.getX(),(int)(y+speed))
				&& !isColidding(this.getX(),(int)(y+speed))) {
			y+=speed;
		}
		else if((int)y > Game.player.getY() && World.isFree(this.getX(),(int)(y-speed))
				&& !isColidding(this.getX(),(int)(y-speed))) {
			y-=speed;
		}
		
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) {
					index= 0;
				}
			}
		}else {
			//Estamos colidindo
			if(Game.rand.nextInt(100) < 10) {
			Game.player.life-=Game.rand.nextInt(3);
			Game.player.isDamaged = true;
			//System.out.println("Vida: "+Game.player.life);
			}
		}
		
		collidingBullet();
		
		if(life <= 0) {
			destroySelf();
			return;
		}
		
		if(isDamaged) {
			this.damageCurrent++;
			if(this.damageCurrent == this.damageFrames) {
				this.damageCurrent = 0;
				this.isDamaged = false;
			}
		}
		
	}
	
	public void destroySelf() {
		Game.enemies.remove(this);
		Game.entities.remove(this);
	}
	
	public void collidingBullet() {
		for(int i = 0; i < Game.bullets.size(); i++) {
			Entity e = Game.bullets.get(i);
			if(e instanceof BulletShoot) {	
				if(Entity.isColidding(this, e)) {
					isDamaged = true;
					life--;
					Game.bullets.remove(i);
					return;
				}
			}
		}
		
	}
		
	public boolean isColiddingWhithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX(),this.getY(), World.TILE_SIZE, World.TILE_SIZE);
		Rectangle player = new Rectangle(Game.player.getX(),Game.player.getY(),16,16);
		
		return enemyCurrent.intersects(player);
	}
	
	public boolean isColidding(int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(xnext, ynext, World.TILE_SIZE, World.TILE_SIZE);
		for(int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(e == this)
				continue;
			Rectangle targetEnemy = new Rectangle(e.getX(),e.getY(), World.TILE_SIZE, World.TILE_SIZE);
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void render(Graphics g) {
		if(!isDamaged)
			g.drawImage(sprites[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
		else
			g.drawImage(Entity.ENEMY_FEEDBACK,this.getX() - Camera.x,this.getY() - Camera.y,null);
	}

}
