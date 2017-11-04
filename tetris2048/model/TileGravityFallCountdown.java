package tetris2048.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TileGravityFallCountdown {

	public static enum GravityFallSpeed {
		
		SLOW(4), MEDIUM(2), FAST(1);
		
		private final int counterLimit;

		private GravityFallSpeed(int limit) {
			counterLimit = limit;
		}
	}

	private GravityFallSpeed gravityFallSpeed;

	public GravityFallSpeed getGravityFallSpeed() {
		return gravityFallSpeed;
	}
	
	public void setGravityFallSpeed(GravityFallSpeed gravityFallSpeed) {
		this.gravityFallSpeed = gravityFallSpeed;
	}
	
	private int counter;

	public void tick() {
		counter--;
	}

	public boolean isExpired() {
		return counter <= 0;
	}

	public void restart() {
		counter = gravityFallSpeed.counterLimit;
	}
	
	public TileGravityFallCountdown() {
		gravityFallSpeed = GravityFallSpeed.FAST;
		counter = gravityFallSpeed.counterLimit;
	}

	public void loadFromFile(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		gravityFallSpeed = (GravityFallSpeed) ois.readObject();
		counter = (Integer) ois.readObject();
	}
	
	public void saveToFile(ObjectOutputStream oos) throws IOException {
		oos.writeObject(gravityFallSpeed);
		oos.writeObject(new Integer(counter));
	}
}
