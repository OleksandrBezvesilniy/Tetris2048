package tetris2048.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

public class BestScores {

	public static class ScoreRecord implements Serializable {
		private static final long serialVersionUID = -7354707468464073614L;
		
		private String playerName;
		private int score;
		private Date date;

		public String getPlayerName() {
			return playerName;
		}

		public int getScore() {
			return score;
		}

		public Date getDate() {
			return date;
		}
		
		public ScoreRecord(String playerName, int score) {
			if((playerName == null) || (playerName.length() == 0)) {
				this.playerName = "Unknown";
			} else {
				this.playerName = playerName;
			}
			this.score = score;
			this.date = new Date();
		}

		@Override
		public String toString() {
			return String.format("%7d   %s   %tF %<tR", score, playerName, date);
		}
	}

	private ScoreRecord[] scoreRecord;
	
	private File fileToStore;
	
	public BestScores(int numberOfRecordsToStore, File fileToStore) {
		scoreRecord = new ScoreRecord[numberOfRecordsToStore];
		for(int n = 0; n < scoreRecord.length; n++) {
			scoreRecord[n] = new ScoreRecord("Unknown", 0);
		}

		this.fileToStore = fileToStore;
		
		try {
			loadFromFile(fileToStore);
		} catch (IOException e) {
		//	System.out.println("Cannot load BestScores from file!");
		}
	}

	public int numberOfRecords() {
		return scoreRecord.length;
	}
	
	public ScoreRecord getScoreRecord(int n) {
		return scoreRecord[n];
	}
	
	public boolean isOneOfTheBest(int newScore) {
		if(newScore <= scoreRecord[scoreRecord.length-1].getScore()) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public int addScoreRecord(ScoreRecord newScoreRecord) {
		int newScore = newScoreRecord.getScore();
		int newScorePosition = -1;
		if(!isOneOfTheBest(newScore)) {
			return newScorePosition;
		}
		for(int n = 0; n < scoreRecord.length; n++) {
			if(newScore > scoreRecord[n].getScore()) {
				newScorePosition = n;
				for(int k = scoreRecord.length-1; k > n; k--) {
					scoreRecord[k] = scoreRecord[k-1];
				}
				scoreRecord[n] = newScoreRecord;
				break;
			}
		}

		try {
			saveToFile(fileToStore);
		} catch (IOException e) {
		//	System.out.println("Cannot save BestScores to file!");
		}

		return newScorePosition;
	}

	private void saveToFile(File file) throws IOException {

		try(FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			
			oos.writeObject(scoreRecord);
		}
	}
	
	private void loadFromFile(File file) throws IOException {

		try(FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis)) {
			
			scoreRecord = (ScoreRecord[]) ois.readObject();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
