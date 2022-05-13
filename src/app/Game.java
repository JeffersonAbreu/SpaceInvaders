package app;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import control.Manager;
import control.UserControls;
import model.Player;
import tool.Settings;

public class Game extends Canvas implements Runnable, KeyListener {
	private static final long serialVersionUID = 1L;
	public static int WINDOW_WIDTH = Settings.DEFAULT_WINDOW_WIDTH;
	public static int WINDOW_HEIGHT = Settings.DEFAULT_WINDOW_HEIGHT;
	public static int WINDOW_SCALE = 1;
	public static int LIFE = Settings.PLAYER_DEFAULT_LIFE;
	public static int SHILD = Settings.PLAYER_DEFAULT_SHILD;
	public static int PONTUACAO = 0;
	public static int LEVEL = 1, FIRE = Settings.PLAYER_DEFAULT_MUNITION;
	private boolean gameOver = false;
	private int up = 1000;

	public BufferedImage layer = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);

	public static Player PLAYER;
	public static Manager MANAGER;

	public Game() {
		Dimension dimension = new Dimension(WINDOW_WIDTH * WINDOW_SCALE, WINDOW_HEIGHT * WINDOW_SCALE);
		// Preferencias de Tela
		this.setPreferredSize(dimension);

		// Add Actions the Keys
		this.addKeyListener(this);

		// Add Player
		PLAYER = new Player();
		// Add all
		MANAGER = new Manager();
	}

	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame("Nave");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(game);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		new Thread(game).start();
	}

	public void update() {
		if (gameOver == false) {
			PLAYER.update();
			if (PONTUACAO >= up) {
				if (PONTUACAO % up == 0) {
					PLAYER.upSpeed();
					LEVEL++;
					up += 1000;
				}
			}
			MANAGER.update();

			if (LIFE <= 0) {
				gameOver = true;
			}
		}
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		if (gameOver == false) {
			MANAGER.render(g);
			PLAYER.render(g);
			g = panelScore(g);
		} else {
			String msgGameOver = "Game Over!";
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 30));
			g.drawString(msgGameOver, (WINDOW_WIDTH / 2) - 80, WINDOW_HEIGHT / 2 - 40);
			msgGameOver = ">> Click <SPACE> Play Again <<";
			g.drawString("Score: " + PONTUACAO, WINDOW_WIDTH / 2 - 60, WINDOW_HEIGHT / 2 + 40);
			g.drawString(msgGameOver, WINDOW_WIDTH / 2 - 210, WINDOW_HEIGHT / 2 + 80);
		}
		bs.show();
	}

	private Graphics panelScore(Graphics g) {
		String msg = "POINTS: " + PONTUACAO;
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.drawString(msg, 10, 20);
		msg = "  FIRE: " + FIRE;
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.drawString(msg, 20, 40);
		// SHILD
		g.setColor(Color.BLUE);
		// LARGURA / 2 - OBJETO / 2 para posicionar ao centro exato
		g.fillRect(WINDOW_WIDTH / 2 - 150, 10, SHILD * 3, 5);

		// FUNDO BRANCO
		g.setColor(Color.WHITE);
		// LARGURA / 2 - OBJETO / 2 para posicionar ao centro exato
		g.drawRect(WINDOW_WIDTH / 2 - 150, 10, 300, 5);

		// LIFE
		g.setColor(Color.GREEN);
		// LARGURA / 2 - OBJETO / 2 para posicionar ao centro exato
		g.fillRect(WINDOW_WIDTH / 2 - 150, 20, LIFE * 3, 20);

		// FUNDO BRANCO
		g.setColor(Color.WHITE);
		// LARGURA / 2 - OBJETO / 2 para posicionar ao centro exato
		g.drawRect(WINDOW_WIDTH / 2 - 150, 20, 300, 20);

		return g;
	}

	@Override
	public void run() {
		while (true) {
			update();
			render();
			try {
				Thread.sleep(1000 / 60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// init();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_RIGHT:
			UserControls.setRight(true);
			break;
		case KeyEvent.VK_LEFT:
			UserControls.setLeft(true);
			break;
		case KeyEvent.VK_UP:
			UserControls.setUp(true);
			break;
		case KeyEvent.VK_DOWN:
			UserControls.setDown(true);
			break;
		case KeyEvent.VK_SPACE:
			UserControls.setShot(true);
			if (gameOver == true) {
				resetGame();
			}
			break;
		}
	}

	private void resetGame() {
		gameOver = false;
		LIFE = Settings.PLAYER_DEFAULT_LIFE;
		SHILD = Settings.PLAYER_DEFAULT_SHILD;
		PONTUACAO = 0;
		FIRE = Settings.PLAYER_DEFAULT_MUNITION;
		PLAYER = new Player();
		// Add all
		MANAGER = new Manager();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_RIGHT:
			UserControls.setRight(false);
			break;
		case KeyEvent.VK_LEFT:
			UserControls.setLeft(false);
			break;
		case KeyEvent.VK_UP:
			UserControls.setUp(false);
			break;
		case KeyEvent.VK_DOWN:
			UserControls.setDown(false);
			break;
		case KeyEvent.VK_SPACE:
			UserControls.setShot(false);
			break;
		}
	}
}
