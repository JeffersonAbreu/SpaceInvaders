package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import tool.Settings;

public class Fragment extends Rectangle {
	private static final long serialVersionUID = 1L;
	public Color color;
	public int speed = 0;
	public int rotation = 0;
	public double ax, ay;
	public double dx, dy;

	public int timer = 0;

	public Fragment(int x, int y, int width, int height, Color color) {
		super(x, y, width, height);
		ax = x;
		ay = y;
		Random random = new Random();
		dx = random.nextGaussian();
		dy = random.nextGaussian();
		speed = Settings.FRAGMENT_SPEED;
		this.color = color;

	}

	public void update() {
		this.ax += speed * dx;
		this.ay += speed * dy;

		timer++;
	}

	public void render(Graphics g) {
		g.setColor(this.color);
		g.fillRect((int) ax, (int) ay, width, height);
	}
}
