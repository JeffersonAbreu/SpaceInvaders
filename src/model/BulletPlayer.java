package model;

public class BulletPlayer extends Bullet {

	public BulletPlayer(int x, int y) {
		super(x, y);
		this.reverseDirection();
		this.setColorRED();
	}

}
