package tetris2048.view.swing.colors;

import java.awt.Color;

import tetris2048.model.CellType;

public class DarkColorScheme implements ColorScheme {

	private Color boundaryLineColor;
	
	private Color gameFieldGridColor;
	
	private Color tileCellBorderColor;
	private Color fieldCellBorderColor;
	
	private Color cell_0_BackgroundColor;
	private Color cell_2_BackgroundColor;
	private Color cell_4_BackgroundColor;
	private Color cell_8_BackgroundColor;
	private Color cell_16_BackgroundColor;
	private Color cell_32_BackgroundColor;
	private Color cell_64_BackgroundColor;
	private Color cell_128_BackgroundColor;
	private Color cell_256_BackgroundColor;
	private Color cell_512_BackgroundColor;
	private Color cell_1024_BackgroundColor;
	private Color cell_2048_BackgroundColor;
	private Color cell_4096_BackgroundColor;
	private Color cell_8192_BackgroundColor;
	
	private Color lightForegroundColor;
	private Color darkForegroundColor;
	
	private DarkColorScheme() {
		
		boundaryLineColor = Color.RED;
		
		gameFieldGridColor = new Color(64, 64, 64);
		
		tileCellBorderColor = Color.RED;
		fieldCellBorderColor = Color.DARK_GRAY;

		cell_0_BackgroundColor = new Color(109, 109, 109);
		cell_2_BackgroundColor = new Color(204, 255, 153);
		cell_4_BackgroundColor = new Color(255, 255, 153);
		cell_8_BackgroundColor = new Color(255, 204, 153);
		cell_16_BackgroundColor = new Color(255, 153, 153);
		cell_32_BackgroundColor = new Color(255, 153, 204);
		cell_64_BackgroundColor = new Color(255, 153, 255);
		cell_128_BackgroundColor = new Color(204, 204, 255);
		cell_256_BackgroundColor = new Color(153, 204, 255);
		cell_512_BackgroundColor = new Color(102, 204, 255);
		cell_1024_BackgroundColor = new Color(102, 255, 255);
		cell_2048_BackgroundColor = new Color(0, 255, 255);
		cell_4096_BackgroundColor = new Color(51, 204, 204);
		cell_8192_BackgroundColor = new Color(0, 153, 204);

		lightForegroundColor = new Color(192, 192, 192);
		darkForegroundColor = new Color(64, 64, 64);
	}
	
	private static ColorScheme colorScheme = null;
	
	public static ColorScheme getColorScheme() {
		if(colorScheme == null) {
			colorScheme = new DarkColorScheme();
		}
		return colorScheme;
	}
	
	@Override
	public Color getCellBackgroundColor(int cellNumber) {
		
		Color color = Color.WHITE;
		
		switch(cellNumber) {
		case 0:
			color = cell_0_BackgroundColor;
			break;
		case 2:
			color = cell_2_BackgroundColor;
			break;
		case 4:
			color = cell_4_BackgroundColor;
			break;
		case 8:
			color = cell_8_BackgroundColor;
			break;
		case 16:
			color = cell_16_BackgroundColor;
			break;
		case 32:
			color = cell_32_BackgroundColor;
			break;
		case 64:
			color = cell_64_BackgroundColor;
			break;
		case 128:
			color = cell_128_BackgroundColor;
			break;
		case 256:
			color = cell_256_BackgroundColor;
			break;
		case 512:
			color = cell_512_BackgroundColor;
			break;
		case 1024:
			color = cell_1024_BackgroundColor;
			break;
		case 2048:
			color = cell_2048_BackgroundColor;
			break;
		case 4096:
			color = cell_4096_BackgroundColor;
			break;
		case 8192:
		default:
			color = cell_8192_BackgroundColor;
			break;
		}
		
		return color;
	}

	@Override
	public Color getCellForegroundColor(int cellNumber) {
		switch(cellNumber) {
		case 0:
			return Color.BLACK;
			
		case 2:
		case 4:
		case 8:
		case 16:
		case 32:
		case 64:
		case 128:
		case 256:
		case 512:
		case 1024:
		case 2048:
			return darkForegroundColor;
			
		case 4096:
		case 8192:
		default:
			return lightForegroundColor;
		}
	}

	@Override
	public Color getCellBorderColor(CellType cellType) {
		switch(cellType) {

		case TILE_CELL:
			return tileCellBorderColor;
			
		case FIELD_CELL:
			return fieldCellBorderColor;
			
		default:
			return Color.BLACK;
		}
	}

	@Override
	public Color getGameFieldGridColor() {
		return gameFieldGridColor;
	}

	@Override
	public Color getBoundaryLineColor() {
		return boundaryLineColor;
	}

}
