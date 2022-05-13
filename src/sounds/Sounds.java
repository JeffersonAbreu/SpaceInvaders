package sounds;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import javax.sound.sampled.*;

public class Sounds {
	public static final File ENEMY_DAMAGE = getFile("EnemyDamage.wav");
	public static final File POINT_CONTR = getFile("PointTally.wav");
	public static final File ENEMY_SHOOT = getFile("EnemyShoot.wav");

	public static final File ENERGY_LESS = getFile("EnergyFill.wav");
	
	public static final File FIRE_STORM2 = getFile("FireStorm2.wav");
	public static final File FIRE_STORM1 = getFile("FireStorm1.wav");
	public static final File EXPLOSION = getFile("Explosion.wav");
	public static final File ERR = getFile("Err.wav");
	public static final File UP = getFile("1up.wav");
	public static final File BUSTER = getFile("MegaBuster.wav");
	public static final File BONUS = getFile("BonusBall.wav");

	private static File getFile(String filename) {
		URL resource = Sounds.class.getResource(filename);
		File file = null;
		try {
			file = new File(resource.toURI());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * Plays a sound from a file.
	 *
	 * @param filename Path to the sound file
	 */
	public static void playSound(String filename) {
		URL resource = Sounds.class.getResource(filename);
		try {
			final Clip clip = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
			clip.addLineListener(event -> {
				if (event.getType() == LineEvent.Type.STOP) {
					clip.close();
				}
			});
			clip.open(AudioSystem.getAudioInputStream(resource));
			clip.start();
		} catch (Exception e) {
			System.out.println("Failed to play sound " + filename);
		}
	}

	public static void play(String filename) {
		URL resource = Sounds.class.getResource(filename);
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File(resource.toURI())));
			clip.start();
		} catch (Exception exc) {
			exc.printStackTrace(System.out);
		}
	}

	public static void play(File soundFile) {
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
			// Get a sound clip resource.
			Clip clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			clip.open(audioIn);
			clip.start();
		} catch (Exception exc) {
			exc.printStackTrace(System.out);
		}
	}
}
