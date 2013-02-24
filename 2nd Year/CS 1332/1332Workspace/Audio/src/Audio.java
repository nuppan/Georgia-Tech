// Ames Audio Utilities
// Spring 2011

import java.io.*;
import javax.sound.sampled.*;

/**
 * Class that can read a wav file, and play it. It also makes the samples
 * available in the form of two arrays: left and right. These arrays may be
 * manipulated externally.
 * 
 * @author William Ames
 * @version Fall 2011
 */
public class Audio {
	// private Clip clip = null;
	private Thread playBackground = null;
	private float[] right = null, left = null;
	private float sampleRate = AudioSystem.NOT_SPECIFIED; // initially unknown
	private AudioFormat desiredFormat = null; // to use during play

	/**
	 * Creates a new sound. The file specified is read.
	 * 
	 * @param fileName
	 *            The name of the .wav file to be read.
	 */
	public Audio(String fileName) {
		File fileIn = new File(fileName);
		try {
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(fileIn);
			AudioFormat format = audioInputStream.getFormat();
			int bytesPerFrame = format.getFrameSize();
			int bitsPerSample = format.getSampleSizeInBits();
			sampleRate = format.getSampleRate();
			setupDesiredAudioFormat(sampleRate);

			if (format.getChannels() != 2) {
				System.out
						.println("Error: can only read 2-channel (stereo) audio files");
				System.exit(1);
			}
			if (bitsPerSample != 16 && bitsPerSample != 8) {
				System.out.println("Error: can only read audio files with "
						+ "8 or 16 bits per sample, found " + bitsPerSample);
				System.exit(1);
			}

			long numBytes = audioInputStream.getFrameLength() * bytesPerFrame;
			if (numBytes > Integer.MAX_VALUE) {
				System.out.println("Error: audio file too large. Sorry.");
				System.exit(1);
			}
			byte[] audioBytes = new byte[(int) numBytes];
			int numBytesRead = 0;
			numBytesRead = audioInputStream.read(audioBytes);
			if (numBytesRead != numBytes)
				System.out.println("Warning: Unable to read entire file.  Proceeding with partial file.");
			convertToFloat(audioBytes, format);
			// dumpFloats("floats.csv"); // debug only
			// dumpBytes ("bytes.csv"); // debug only
			audioInputStream.close();
		} catch (Exception e) {
			System.out.println("Error encountered: " + e);
			System.exit(1);
		}
	}

	private void setupDesiredAudioFormat(float sampleRate) {
		desiredFormat = new AudioFormat( // to use during play
				sampleRate, // sampleRate,
				16, // sampleSizeInBits,
				2, // channels,
				true, // signed
				false // bigEndian
		);
	}

	// back to byte stream, 2 channel, 4 bytes/frame, big endian, signed
	// If left, right different sizes, use smaller
	private byte[] convertToBytes() {
		checkMaximum();
		byte[] audioBytes = new byte[left.length * 2 * 2];
		int bytePos = 0;
		if (left.length != right.length) {
			System.out
					.println("Warning! Left and Right channels are different sizes.");
			System.out.println("Left size: " + left.length + ",  Right size: "
					+ right.length);
		}
		int size = Math.min(left.length, right.length);
		for (int i = 0; i < size; ++i) {
			short leftVal = (short) (left[i] * 32767);
			short rightVal = (short) (right[i] * 32767);
			if (desiredFormat.isBigEndian()) {
				audioBytes[bytePos++] = (byte) (leftVal >> 8); // msb
				audioBytes[bytePos++] = (byte) (leftVal & 0xFF); // lsb
				audioBytes[bytePos++] = (byte) (rightVal >> 8); // msb
				audioBytes[bytePos++] = (byte) (rightVal & 0xFF); // lsb
			} else { // littleEndian
				audioBytes[bytePos++] = (byte) (leftVal & 0xFF); // lsb
				audioBytes[bytePos++] = (byte) (leftVal >> 8); // msb
				audioBytes[bytePos++] = (byte) (rightVal & 0xFF); // lsb
				audioBytes[bytePos++] = (byte) (rightVal >> 8); // msb
			}
		}
		return audioBytes;
	}

	// to excel csv format
	@SuppressWarnings("unused")
	private void dumpFloats(String filename) {
		System.out.println("Writing samples to file " + filename);
		try {
			PrintStream writer = new PrintStream(filename);
			for (int i = 0; i < 65536; ++i)
				writer.println(i + "," + left[i] + "," + right[i]);
			writer.close();
		} catch (IOException e) {
			System.out.println("File writing failed to file: " + filename);
		}
	}

	// to excel csv format
	@SuppressWarnings("unused")
	private void dumpBytes(String filename) {
		System.out.println("Writing samples to file " + filename);
		try {
			PrintStream writer = new PrintStream(filename);
			byte[] bytes = convertToBytes();
			for (int i = 0; i < 65536 * 4; i += 4) {
				short leftSample = (short) (bytes[i] << 8 | bytes[i + 1]);
				short rightSample = (short) (bytes[i + 2] << 8 | bytes[i + 3]);
				writer.println(i / 4 + "," + leftSample + "," + rightSample);
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("File writing failed to file: " + filename);
		}
	}

	/**
	 * Saves the current sound into a wav file. The format will be 44100 samples per
	 * second, 16 bits per sample, 2 channels, PCM Signed, big endian.
	 * 
	 * @param fileName
	 *            The name of the file to be written, should end with ".wav" suffix
	 */
	public void save(String fileName) {
		File fileOut = new File(fileName);
		byte[] bytes = convertToBytes();
		ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
		AudioFormat format = desiredFormat;
		AudioInputStream stream = new AudioInputStream(byteStream, format,
				bytes.length / 2 / 2);
		try {
			@SuppressWarnings("unused")
			int bytesWritten = AudioSystem.write(stream,
					AudioFileFormat.Type.WAVE, fileOut);
		} catch (IOException e) {
			System.out.println("Unable to write to output file " + fileName
					+ ", " + e);
		}
	}

	/*
	 * This version was removed, because "clip"'s have a maximum size that is
	 * too small, especially on a mac.
	 */ 
/*	//Plays the current sound. 
    public void play() { // Unfortunately, a "clip" seems to have a limited
                         // size, and is especially small on a Macintosh.
        stop();
        try {
            clip = AudioSystem.getClip();
            System.gc();
            byte[] bytes = convertToBytes();
            clip.open(desiredFormat, bytes, 0, bytes.length);
            clip.start();
        } catch (LineUnavailableException e) {
            System.out.println("Cannot play: " + e);
            return;
        }
    }
    // Stops playing the current sound. If no sound is playing, the stop()
    // method returns without doing anything.
    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.flush();
            clip.close();
            clip = null;
        }
    }
*/

	/**
	 * Plays the current sound. Stops the sound that is currently playing, if
	 * any, then plays the current sound starting at the beginning.
	 */
	public void play() { // doesn't use clips, should have no length restrictions.
		stop();
		System.gc(); // about to use a lot of memory
		playBackground = new Thread() {
			private SourceDataLine sdl = null;

			@SuppressWarnings("synthetic-access")
			@Override
			public void run() {
				try {
					byte[] bytes = convertToBytes();
					sdl = AudioSystem.getSourceDataLine(desiredFormat);
					System.gc(); // in case old sdl can be cleaned up
					sdl.open(desiredFormat);
					// Info x = sdl.getLineInfo();
					// System.out.println(sdl.getLineInfo());
					sdl.start();
					sdl.write(bytes, 0, bytes.length); // this blocks until last
														// buffer
					synchronized (this) {
						sdl.drain();
						sdl.close();
					}
				} catch (LineUnavailableException e) {
					System.out.println("Cannot play: " + e);
				}
			}

			@Override
			public void interrupt() {
				if (sdl != null) { // shouldn't ever be null, just making sure.
					synchronized (this) {
						sdl.stop();
						sdl.flush();
						sdl.close();
					}
				}
			}
		};
		playBackground.setPriority(Thread.NORM_PRIORITY); // not event priority
		// SwingUtilities.invokeLater(playBackground); // seems to block !! ??
		playBackground.start();
	}

	/**
	 * Stops playing the current sound. If no sound is playing, the stop()
	 * method returns without doing anything.
	 */
	public void stop() {
		if (playBackground != null)
			playBackground.interrupt();
	}


	// This play/stop pair should probably be deleted, it's like the thread version but 
	// doesn't use threads so it's not interruptible. But, at least it doesn't use 
	// clips so there is no size restriction.
/*
    private SourceDataLine line = null;
    public void play() {
        try {
            AudioFormat format = desiredFormat;
            line = AudioSystem.getSourceDataLine(format);
            line.open(format);
            byte[] bytes = convertToBytes();
            line.start();
            line.write(bytes, 0, bytes.length);
            line.drain();
            line.close();
            line = null;
        } catch (Exception e) {
            System.out.println("Cannot play: " + e);
            return;
        }
    }
    public void stop() {
        if (line != null) {
            line.stop();
            line.close();
            line = null;
        }
    }
*/

	/**
	 * Retrieves the left channel of the current sound. The samples are in the
	 * range of [-1f ... 1f]
	 */
	public float[] getLeftChannel() {
		return left.clone();
	}

	/**
	 * Retrieves the right channel of the current sound. The samples are in the
	 * range of [-1f ... 1f]
	 */
	public float[] getRightChannel() {
		return right.clone();
	}

	/**
	 * Sets the left channel.
	 * 
	 * @param left
	 *            the array of samples to use for the left channel. Each sample
	 *            should be in the range of [-1 ... 1]
	 */
	public void setLeftChannel(float[] left) {
		this.left = null; // allow garbage collection before clone is finished
		this.left = left.clone();
	}

	/**
	 * Sets the right channel.
	 * 
	 * @param right
	 *            the array of samples to use for the right channel. Each sample
	 *            should be in the range of [-1 ... 1]
	 */
	public void setRightChannel(float[] right) {
		this.right = null; // allow garbage collection before clone is finished
		this.right = right.clone();
	}

	// make sure both left and right are in the range of [-1..1]
	private void checkMaximum() {
		for (int i = 0; i < left.length; ++i)
			if (left[i] > 1.0f || left[i] < -1.0f) {
				System.out.println("Error: left channel element " + i
						+ " contains value " + left[i]
						+ " which is outside of the range [-1 .. 1]");
				System.exit(1);
			}
		for (int i = 0; i < right.length; ++i)
			if (right[i] > 1.0f || right[i] < -1.0f) {
				System.out.println("Error: left channel element " + i
						+ " contains value " + right[i]
						+ " which is outside of the range [-1 .. 1]");
				System.exit(1);
			}
	}

	private void convertToFloat(byte[] raw, AudioFormat format) {
		int bitsPerSample = format.getSampleSizeInBits();
		if (bitsPerSample != 8 && bitsPerSample != 16) {
			System.out
					.println("Can only handle 8 or 16 bits per sample.  Found "
							+ bitsPerSample + ", sorry.");
			System.exit(1);
		}
		int bytesPerSample = bitsPerSample / 8;
		left = new float[raw.length / 2 / bytesPerSample]; // /2 is for 2
															// channels
		right = new float[raw.length / 2 / bytesPerSample];

		for (int i = 0, next = 0; i < raw.length; ++next) {
			byte left1, right1, left2 = 0, right2 = 0;
			left1 = raw[i++];
			if (bytesPerSample == 2)
				left2 = raw[i++];
			right1 = raw[i++];
			if (bytesPerSample == 2)
				right2 = raw[i++];
			if (bytesPerSample == 2 && !format.isBigEndian()) {
				byte temp = left1; // want left1 most significant, left2 least
									// significant
				left1 = left2;
				left2 = temp;
				temp = right1;
				right1 = right2;
				right2 = temp;
			}
			if (format.getEncoding() == AudioFormat.Encoding.PCM_SIGNED) {
				left[next] = signedByteToInt(left1, left2);
				right[next] = signedByteToInt(right1, right2);
			} else { // PCM_UNSIGNED
				left[next] = unsignedByteToInt(left1, left2);
				right[next] = unsignedByteToInt(right1, right2);
			}
		}
	}

	private static float signedByteToInt(byte b1, byte b2) {
		int sample = (b1 << 8) | (b2 & 0xFF);
		float result = sample / 32768.f;
		return result;
	}

	private static float unsignedByteToInt(byte b1, byte b2) {
		int sample = (b1 & 0xff) << 8 | (b2 & 0xff);
		float result = sample / 65535.f * 2 - 1;
		return result;
	}

	/**
	 * Retrieve the sample rate in use. Comes from the file given to the
	 * constructor.
	 * 
	 * @return the sample rate
	 */
	public float getSampleRate() {
		return sampleRate;
	}


	/*
	 * // This main is for testing only 
	 */
	 /*public static void main(String[] args) {
	  
	     for (short s = -32768; s<32767; ++s) { 
	         float ii = signedByteToInt((byte)(s>>>8), (byte)(s&0xff));  
	         System.out.println(s + " " + ii); 
	     }
	  
        float i1 = signedByteToInt((byte) 0x08, (byte) 0x07);
        float i2 = signedByteToInt((byte) 0xF0, (byte) 0x07);
        float i2b = signedByteToInt((byte) 0xFF, (byte) 0xFF);
        float i3 = signedByteToInt((byte) 0x08, (byte) 0xF0);
        float i4 = signedByteToInt((byte) 0xF0, (byte) 0xF0);
        float i5 = unsignedByteToInt((byte) 0x08, (byte) 0x07);
        float i6 = unsignedByteToInt((byte) 0xF0, (byte) 0x07);
        float i7 = unsignedByteToInt((byte) 0x08, (byte) 0xF0);
        float i8 = unsignedByteToInt((byte) 0xF0, (byte) 0xF0);
        Audio audio = new Audio("ManMan-10lbMoustacheIntro.wav");
        float[] left = audio.getLeftChannel();
        float[] right = audio.getRightChannel();
        audio.checkMaximum();
        audio.save("out.wav");

        audio.play();

        audio.play();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Stopping clip");
        audio.stop();
        System.out.println("Clip Stopped");
        audio.play();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("All done");
        audio.stop();
        audio.stop(); // should not crash
    }*/
	 
}