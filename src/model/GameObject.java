package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import tool.Settings;

public abstract class GameObject extends Rectangle implements Inter{

	private int horzSpeed = Settings.DEFAULT_SPEED;
	private int vertSpeed = Settings.DEFAULT_SPEED;
	private String sprite;
	protected static final Color BG_COLOR = new Color(Color.TRANSLUCENT, Color.TRANSLUCENT, Color.TRANSLUCENT);
	private boolean death = false;
	private int life = 0;

	public int getLife() {
		return this.life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public String getSprite() {
		return sprite;
	}

	public void setSprite(String sprite) {
		this.sprite = sprite;
	}

	public void setDeadth() {
		this.death = true;
	}

	public boolean isDeadth() {
		return this.death;
	}

	public int getSpeedHorz() {
		return this.horzSpeed;
	}

	public int getSpeedVert() {
		return this.vertSpeed;
	}

	protected void moverVert() {
		this.y += this.vertSpeed;
	}

	protected void setSpeedHorz(int speed) {
		this.horzSpeed += speed;
	}

	protected void setSpeedVert(int speed) {
		this.vertSpeed += speed;
	}

	public void update() {
		this.moverVert();
	}

	public abstract void impact(int dano);

	public void render(Graphics g) {
		g.fillRect(x, y, width, height);
		ImageIcon gameSprite = new ImageIcon(getClass().getResource("../img/" + this.getSprite()));
		g.drawImage(gameSprite.getImage(), x, y, width, height, BG_COLOR, null);
	}
}
