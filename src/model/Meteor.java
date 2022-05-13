package model;

import java.util.Random;

import tool.Settings;

public class Meteor extends Destructible {
	public Meteor(int x) {
		int numSort = new Random().nextInt(1, 4);
		this.x = x;
		this.y = 0;
		this.width = Settings.PLAYER_WIDTH;
		this.height = Settings.PLAYER_HEIGHT;
		this.setSprite("meteor_" + numSort + ".png");
		this.addLife(12);
		this.addShild(numSort * 2);
	}
}
