package tetris2048.model;

public interface GameCommandListener {

	void command(GameCommand gameCommand) throws GameOverException;
}
