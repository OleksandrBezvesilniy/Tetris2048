package tetris2048.model;

import java.util.Random;

public class TileFactory {

	private static final int[][] tile_ij_codes = {
		{0, 0}, 
		{0, 0, 0, 1},
		{0, 0, 0, 1, 1, 0},
		{0, 0, 0, 1, 0, 2},
		{0, 0, 0, 1, 0, 2, 0, 3},
		{0, 0, 0, 1, 1, 0, 1, 1},
		{0, 0, 0, 1, 0, 2, 1, 0},
		{0, 0, 0, 1, 0, 2, 1, 2},
		{0, 0, 0, 1, 0, 2, 1, 1},
		{0, 1, 0, 2, 1, 0, 1, 1},
		{0, 0, 0, 1, 1, 1, 1, 2}
	};
	
	public enum TileSizeDifficulty { Tiles_of_1_to_3_Segments, Tiles_of_1_to_4_Segments, Tiles_of_4_Segments }
	public enum SegmentValueDifficulty { Segment_Values_2_4, Segment_Values_2_4_8 }

	private TileSizeDifficulty tileSizeDifficulty = TileSizeDifficulty.Tiles_of_1_to_4_Segments;

	public TileSizeDifficulty getTileSizeDifficulty() {
		return tileSizeDifficulty;
	}
	public void setTileSizeDifficulty(TileSizeDifficulty sizeDifficulty) {
		this.tileSizeDifficulty = sizeDifficulty;
	}

	private SegmentValueDifficulty segmentValueDifficulty = SegmentValueDifficulty.Segment_Values_2_4_8;

	public SegmentValueDifficulty getSegmentValueDifficulty() {
		return segmentValueDifficulty;
	}
	public void setSegmentValueDifficulty(SegmentValueDifficulty segmentValueDifficulty) {
		this.segmentValueDifficulty = segmentValueDifficulty;
	}
	
	private Random rand = new Random();

	private int[] getRandom_ij_code() {

		if(tileSizeDifficulty == TileSizeDifficulty.Tiles_of_1_to_3_Segments) {
			return tile_ij_codes[rand.nextInt(4)];
		}
		else if(tileSizeDifficulty == TileSizeDifficulty.Tiles_of_1_to_4_Segments) {
			return tile_ij_codes[rand.nextInt(11)];
		}
		else { // TileSizeDifficulty.Tiles_of_4_Segments
			return tile_ij_codes[4 + rand.nextInt(7)];
		}
	}

	// Random distribution of segment values:
	//  4/7 of all segments will get value 2
	//  2/7 of all segments will get value 4
	//  1/7 of all segments will get value 8
	private final int[] segment_values = {2, 2, 2, 2, 4, 4, 8};

	private int getRandomSegmentValue() {
		if(segmentValueDifficulty == SegmentValueDifficulty.Segment_Values_2_4) {
			return segment_values[rand.nextInt(segment_values.length-1)];
		}
		else { // SegmentValueDifficulty.Segment_Values_2_4_8
			return segment_values[rand.nextInt(segment_values.length)];
		}
	}
	
	public Tile getNextTile() {
		
		// Get a random tile ij_code
		int[] ij_code = getRandom_ij_code();

		int[] tile_ijv_code = new int[(ij_code.length / 2) * 3];
		
		for(int n = 0, k = 0; n < tile_ijv_code.length; ) {
			tile_ijv_code[n++] = ij_code[k++];
			tile_ijv_code[n++] = ij_code[k++];
			tile_ijv_code[n++] = getRandomSegmentValue();
		}

		Tile tile = new Tile(tile_ijv_code);
		
		// Randomize the tile orientation 
		int numberOfRotations = rand.nextInt(4);  
		for(int r = 0; r < numberOfRotations; r++) {
			tile.rotateLeft();
		}
		
		return tile;
	}
}
