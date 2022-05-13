package control;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import app.Game;
import model.BoxBullet;
import model.BoxClosed;
import model.BoxLife;
import model.Bullet;
import model.BulletEnemy;
import model.BulletPlayer;
import model.Collectable;
import model.Destructible;
import model.Enemy;
import model.Fragment;
import model.GameObject;
import model.Meteor;
import sounds.Sounds;
import model.Bonus;
import tool.Settings;

public class Manager {
	private int count = 5, timer = 0, so = 0;
	private static List<Bullet> BULLETS_PLAYER = new ArrayList<Bullet>();
	private static List<Bullet> BULLETS_ENEMY = new ArrayList<Bullet>();
	private static List<Bonus> MSG_BONUS = new ArrayList<Bonus>();
	private List<Enemy> enemys = new ArrayList<Enemy>();
	private List<Fragment> fragments = new ArrayList<Fragment>();
	private List<Meteor> meteors = new ArrayList<Meteor>();
	private List<Collectable> collets = new ArrayList<Collectable>();
	private boolean onOff = true;

	public static void addBulletPlayer(int x, int y) {
		BULLETS_PLAYER.add(new BulletPlayer(x, y));
		Sounds.play(Sounds.BUSTER);
	}

	public static void addBulletEnemy(int x, int y) {
		BULLETS_ENEMY.add(new BulletEnemy(x, y));
		//Sounds.play(Sounds.ENEMY_SHOOT);
	}

	public static void addMsgBonus(Bonus bonus) {
		MSG_BONUS.add(bonus);
		if (bonus.isBonification()) {
			Sounds.play(Sounds.BONUS);
		} else {
			Sounds.play(Sounds.POINT_CONTR);
		}
	}

	public void update() {
		timer++;
		if (timer % 120 == 0) {
			if (onOff) {
				enemys.add(new Enemy(new Random().nextInt(Settings.DEFAULT_WINDOW_WIDTH - Settings.PLAYER_WIDTH)));
			} else {
				if (count == 5) {
					if (so == 1) {
						collets.add(new BoxBullet(
								new Random().nextInt(Settings.DEFAULT_WINDOW_WIDTH - Settings.PLAYER_WIDTH)));
					} else {
						if (so == 2) {
							collets.add(new BoxLife(
									new Random().nextInt(Settings.DEFAULT_WINDOW_WIDTH - Settings.PLAYER_WIDTH)));
						} else {
							collets.add(new BoxClosed(
									new Random().nextInt(Settings.DEFAULT_WINDOW_WIDTH - Settings.PLAYER_WIDTH)));
							so = 0;
						}
					}
					so++;
					count = 0;
				} else {
					meteors.add(
							new Meteor(new Random().nextInt(Settings.DEFAULT_WINDOW_WIDTH - Settings.PLAYER_WIDTH)));
					count++;
				}
			}
			onOff = !onOff;
		}
		for (int i = 0; i < enemys.size(); i++) {
			Enemy currentEnemy = enemys.get(i);
			currentEnemy.update();
			if (currentEnemy.y >= Game.PLAYER.y - Settings.PLAYER_HEIGHT) {
				// PLAYER x ENEMY
				if (currentEnemy.intersects(Game.PLAYER)) {
					collision(currentEnemy);
					enemys.remove(currentEnemy);
				} else {
					if (currentEnemy.y > Settings.DEFAULT_WINDOW_HEIGHT) {
						enemys.remove(currentEnemy);
					}
				}
			}
		}

		// METEOR
		for (int j = 0; j < meteors.size(); j++) {
			Meteor meteor = meteors.get(j);
			meteor.update();
			if (meteor.y >= Game.PLAYER.y - Settings.PLAYER_HEIGHT) {
				// PLAYER x METEOR
				if (meteor.intersects(Game.PLAYER)) {
					collision(meteor);
					meteors.remove(meteor);
				} else {
					if (meteor.y > Settings.DEFAULT_WINDOW_HEIGHT) {
						meteors.remove(meteor);
					}
				}
			}
		}
		for (int i = 0; i < collets.size(); i++) {
			Collectable collet = collets.get(i);
			collet.update();
			if (collet.y >= Game.PLAYER.y - Settings.PLAYER_HEIGHT) {
				// PLAYER x COLLECTABLE
				if (collet.intersects(Game.PLAYER)) {
					collet.impact(Settings.DAMAGE_COLLIDE);
					if (collet.isDeadth()) {
						collets.remove(collet);
					}
				} else {
					if (collet.y > Settings.DEFAULT_WINDOW_HEIGHT) {
						collets.remove(collet);
					}
				}
			}
		}

		for (int i = 0; i < BULLETS_PLAYER.size(); i++) {
			Bullet currentBulletPlayer = BULLETS_PLAYER.get(i);
			currentBulletPlayer.update();
			if (currentBulletPlayer.y < 0) {
				BULLETS_PLAYER.remove(currentBulletPlayer);
			} else {
				boolean impact = false;
				// ENEMY
				for (int j = 0; j < enemys.size(); j++) {
					Enemy currentEnemy = enemys.get(j);
					if (currentBulletPlayer.collide(currentEnemy)) {
						impact = true;
						currentEnemy.impact(Settings.DAMAGE_BULLET);
						BULLETS_PLAYER.remove(currentBulletPlayer);
						if (currentEnemy.isDeadth()) {
							explosion(currentEnemy);
							enemys.remove(currentEnemy);
						}
						break;
					}
				}
				if (!impact) {
					// METEOR
					for (int j = 0; j < meteors.size(); j++) {
						Meteor meteor = meteors.get(j);
						if (currentBulletPlayer.collide(meteor)) {
							impact = true;
							BULLETS_PLAYER.remove(currentBulletPlayer);
							meteor.impact(Settings.DAMAGE_BULLET);
							if (meteor.isDeadth()) {
								explosion(meteor);
								meteors.remove(meteor);
							}
							break;
						}
					}
				}
				if (impact)
					bonification(impact);

				for (Collectable collect : collets) {
					if (currentBulletPlayer.collide(collect)) {
						BULLETS_PLAYER.remove(currentBulletPlayer);
						collect.impact(Settings.DAMAGE_BULLET);
						if (collect.isDeadth()) {
							collets.remove(collect);
						}
						break;
					}
				}

			}
		}
		for (int i = 0; i < BULLETS_ENEMY.size(); i++) {
			Bullet bulletEnemy = BULLETS_ENEMY.get(i);
			bulletEnemy.update();
			// Area do Player
			if (bulletEnemy.collide(Game.PLAYER)) {
				Game.PLAYER.impact(Settings.DAMAGE_BULLET);
				BULLETS_ENEMY.remove(bulletEnemy);
				refreshINFOs();
				Sounds.play(Sounds.ENERGY_LESS);
			} else {
				if (bulletEnemy.y > Settings.DEFAULT_WINDOW_HEIGHT) {
					BULLETS_ENEMY.remove(bulletEnemy);
				}
			}

		}
		for (int i = 0; i < fragments.size(); i++) {
			Fragment part = fragments.get(i);
			part.update();
			if (part.timer >= 60) {
				fragments.remove(part);
			}
		}

		for (int i = 0; i < MSG_BONUS.size(); i++) {
			Bonus bonus = MSG_BONUS.get(i);
			bonus.update();
			if (bonus.getTimer() >= 60) {
				MSG_BONUS.remove(bonus);
			}
		}

	}

	private void collision(Destructible destruct) {
		int qtd = destruct.getShild() + destruct.getLife();
		if (Game.PLAYER.getShild() >= Settings.DAMAGE_COLLIDE) {
			Manager.addMsgBonus(new Bonus(Game.PLAYER, Settings.DAMAGE_COLLIDE, "Shild", false));
		} else if (Game.PLAYER.getShild() > 0) {
			Manager.addMsgBonus(
					new Bonus(Game.PLAYER, Settings.DAMAGE_COLLIDE - Game.PLAYER.getShild(), "Life", false));
		} else {
			Manager.addMsgBonus(new Bonus(Game.PLAYER, Settings.DAMAGE_COLLIDE, "Life", false));
		}
		Game.PLAYER.impact(qtd);
		refreshINFOs();
		Sounds.play(Sounds.FIRE_STORM1);
		explosion(destruct);
	}

	private void bonification(boolean destroy) {
		if (destroy) {
			Game.PONTUACAO += 5;
			Game.PLAYER.addLife(2);
			Game.PLAYER.addShild(1);
			Game.PLAYER.addMunition(1);
		} else {
			Game.PONTUACAO += 1;
			Game.PLAYER.addLife(1);
		}
		refreshINFOs();
	}

	public static void refreshINFOs() {
		Game.SHILD = Game.PLAYER.getShild();
		Game.LIFE = Game.PLAYER.getLife();
		Game.FIRE = Game.PLAYER.getMunition();
	}

	private void explosion(GameObject obj) {
		Sounds.play(Sounds.EXPLOSION);
		for (int j = 0; j < 50; j++) {
			this.fragments.add(new Fragment((int) obj.getCenterX(), (int) obj.getCenterY(), Settings.FRAGMENT_SIZE,
					Settings.FRAGMENT_SIZE, Settings.FRAGMENT_COLOR));
		}
	}

	public void render(Graphics g) {

		for (int i = 0; i < MSG_BONUS.size(); i++) {
			Bonus bonus = MSG_BONUS.get(i);
			bonus.render(g);
		}

		for (Bullet current : BULLETS_PLAYER) {
			current.render(g);
		}

		for (Bullet current : BULLETS_ENEMY) {
			current.render(g);
		}

		for (Enemy current : enemys) {
			current.render(g);
		}

		for (int i = 0; i < fragments.size(); i++) {
			fragments.get(i).render(g);
		}

		for (Meteor meteor : meteors) {
			meteor.render(g);
		}

		for (Collectable collec : collets) {
			collec.render(g);
		}
	}
}
