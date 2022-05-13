package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import tool.Settings;

public abstract class Bullet extends Rectangle {
	private Color bgColor;
	private int speed = 0;
	private int rotation = 0;
	private boolean directionDown = true;

	public Bullet(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = Settings.BULLET_WIDTH;
		this.height = Settings.BULLET_HEIGTH;
		bgColor = Color.MAGENTA;
		speed = Settings.DEFAULT_SPEED * 2;
	}

	protected void reverseDirection() {
		this.directionDown = !directionDown;
	}

	protected void setColorRED() {
		this.bgColor = Color.RED;
	}

	public void update() {
		if (directionDown) {
			this.y += speed;
		} else {
			this.y -= speed;
		}

		rotation += 4;
		if (rotation == 360) {
			rotation = 0;
		}
	}

	public boolean collide(GameObject obj) {
		int rigthObj = obj.x + obj.width;
		int rigthBullet = this.x + this.width;
		if (directionDown) {
			if ((this.y + this.height) >= obj.y) {
				//System.out.println("mesma linha");
				if (rigthBullet <= rigthObj) {
					//System.out.printf("%d [BULLET] %d\n", this.x, rigthBullet);
					//System.out.println(obj.x + " [  OBJ ] " + rigthObj);
					if (this.x >= obj.x) {
						return true;
					}
				}
			}
		} else {
			if (this.y <= obj.y + obj.height) {
				//System.out.println("mesma linha");
				if (rigthBullet <= rigthObj) {
					//System.out.printf("%d [BULLET] %d\n", this.x, rigthBullet);
					//System.out.println(obj.x + " [  OBJ ] " + rigthObj);
					if (this.x >= obj.x) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.rotate(Math.toRadians(this.rotation), this.x + this.width / 2, this.y + this.height / 2);
		g2.setColor(this.bgColor);
		g2.fillRect(this.x, this.y, this.width, this.height);
		g2.rotate(Math.toRadians(-this.rotation), this.x + this.width / 2, this.y + this.height / 2);
	}

}
