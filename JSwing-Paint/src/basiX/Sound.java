package basiX;

import java.io.BufferedInputStream;
import java.io.File; //import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class Sound {
	Clip clip;
	AudioInputStream audioInputStream;
	BufferedInputStream bis;
	AudioFormat audioFormat;
	int size;
	byte[] audio;
	DataLine.Info info;
	SourceDataLine line;
	private String pfad;

	public Sound() {
	}

	public Sound(String pfad) {
		this.setzeSound(pfad);
	}

	public void setzeSound(String pfad) {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(getClass()
					.getResource(pfad));
			bis = new BufferedInputStream(audioInputStream);
			audioFormat = audioInputStream.getFormat();
			size = (int) (audioFormat.getFrameSize() * audioInputStream
					.getFrameLength());
			audio = new byte[size];
			info = new DataLine.Info(Clip.class, audioFormat, size);
			bis.read(audio, 0, size);

		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// audioInputStream = null;
		// try {
		// audioInputStream = AudioSystem.getAudioInputStream(getClass()
		// .getResource(pfad));
		// } catch (Exception e) {
		//
		// e.printStackTrace();
		// }
		// audioFormat = audioInputStream.getFormat();
		// line = null;
		// info = new DataLine.Info(SourceDataLine.class, audioFormat);
		// try {
		// line = (SourceDataLine) AudioSystem.getLine(info);
		// line.open(audioFormat);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

	}

	public void play(String pfad) {
		this.pfad = pfad;
		new Thread() {
			public void run() {
				Sound.this.setzeSound(Sound.this.pfad);
				line.start();
				int nBytesRead = 0;
				byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
				while (nBytesRead != -1) {
					try {
						nBytesRead = audioInputStream.read(abData, 0,
								abData.length);
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (nBytesRead >= 0) {
						line.write(abData, 0, nBytesRead);
					}
				}
				line.drain();
				line.close();
			}
		}.start();

	}

	// public void play() {
	// new Thread() {
	// public void run() {
	// line.start();
	// int nBytesRead = 0;
	// byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
	// while (nBytesRead != -1) {
	// try {
	// nBytesRead = audioInputStream.read(abData, 0,
	// abData.length);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// if (nBytesRead >= 0) {
	// line.write(abData, 0, nBytesRead);
	// }
	// }
	// line.drain();
	// line.close();
	// }
	// }.start();
	//
	// }

	public void play() {
		new Thread() {
			public void run() {
				try {
					if (clip==null || !clip.isActive()){
					clip = (Clip) AudioSystem.getLine(info);
					clip.open(audioFormat, audio, 0, size);
					clip.start();}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public static void example() {
		Sound s = new Sound();
		s.setzeSound("/sound/Windows XP-Startvorgang.wav");
		s.play();
	}

	private int EXTERNAL_BUFFER_SIZE = 128000;

}
