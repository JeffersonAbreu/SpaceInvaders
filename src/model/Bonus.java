package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import tool.Settings;

public class Bonus {
	private Color bgColor;
	private String msg;
	private int x, y;
	private int timer = 0;
	private boolean signalAdd = false;

	public Bonus(GameObject obj, int qtd, String msg, boolean add) {
		this.x = obj.x;
		this.y = (int) obj.getCenterY();
		String signal;
		this.signalAdd = add;
		if (this.signalAdd) {
			signal = "+";
			this.bgColor = Color.WHITE;
		} else {
			signal = "-";
			this.bgColor = Color.RED;
			this.y = (int) obj.getCenterY() + Settings.PLAYER_HEIGHT / 2 + 15;
		}
		this.msg = signal + " " + qtd + " " + msg;
	}

	public String getMsg() {
		return this.msg;
	}
	public boolean isBonification() {
		return this.signalAdd;
	}
	public void render(Graphics g) {
		g.setColor(this.bgColor);
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.drawString(this.msg, this.x, this.y);
	}

	public void update() {
		this.timer++;
	}

	public int getTimer() {
		return this.timer;
	}
}
