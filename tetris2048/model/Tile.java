package tetris2048.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/*
 * Tiles are built of square segments.
 * Positions of the segments are defined by their coordinates:
 * i() for row, j() for column. Zero values of i and j are for the top-left corner position.
 * Each tile has an integer value v() assigned to it.
 * These values are used in the 2048 game logic.
 */

// Tiles are immutable, except rotation

public class Tile {
	
	public class TileSegment {
		private int i, j, v;
		private TileSegment() {}
		
		/** Segment row position */
		public int i() { return i; }
		/** Segment column position */
		public int j() { return j; }
		/** Segment value */
		public int v() { return v; }
	}
	
	private final TileSegment[] segments;

	// Tile height
	private int iMax;
	// Tile width
	private int jMax;

	public int length() { return segments.length; }
	
	public TileSegment segment(int n) { return segments[n]; }
	
	public Tile(int[] tile_ijv_code) {
		
		segments = new TileSegment[tile_ijv_code.length / 3];

		iMax = 0;
		jMax = 0;
		int i, j, v;
		for(int n = 0, k = 0; n < segments.length; n++) {
			segments[n] = new TileSegment();
			i = tile_ijv_code[k++];
			j = tile_ijv_code[k++];
			v = tile_ijv_code[k++];

			segments[n].i = i;
			segments[n].j = j;
			segments[n].v = v;
			if(i > iMax) iMax = i;
			if(j > jMax) jMax = j;
		}
	}
	
	public void rotateLeft() {
		int iTemp;
		for(int n = 0; n < segments.length; n++) {
			iTemp = segments[n].i;
			segments[n].i = jMax - segments[n].j;
			segments[n].j = iTemp;
		}
		iTemp = iMax;
		iMax = jMax;
		jMax = iTemp;
	}
	
	public void rotateRight() {
		int iTemp;
		for(int n = 0; n < segments.length; n++) {
			iTemp = segments[n].i;
			segments[n].i = segments[n].j;
			segments[n].j = iMax - iTemp;
		}
		iTemp = iMax;
		iMax = jMax;
		jMax = iTemp;
	}
	
	public static Tile loadFromFile(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		int[] tile_ijv_code = (int[]) ois.readObject();
		return new Tile(tile_ijv_code);
	}
	
	public void saveToFile(ObjectOutputStream oos) throws IOException {
		int[] tile_ijv_code = new int[segments.length * 3];
		for(int n = 0, k = 0; n < segments.length; n++) {
			tile_ijv_code[k++] = segments[n].i;
			tile_ijv_code[k++] = segments[n].j;
			tile_ijv_code[k++] = segments[n].v;
		}
		oos.writeObject(tile_ijv_code);
	}
}
