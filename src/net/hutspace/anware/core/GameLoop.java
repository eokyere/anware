package net.hutspace.anware.core;

import net.hutspace.anware.Board;
import net.hutspace.anware.ai.AI;
import net.hutspace.anware.ai.MiniMax;
import android.util.Log;

public class GameLoop extends Thread {
	private static final String TAG = "GameLoop";
	private boolean running;
	private Game game;
	private static AI ai = new MiniMax();

	private Board board;
//	private int interval;

	public GameLoop(Board board, Game game) {
		super();
		this.game = game;
		this.board = board;
//		interval = board.getSpeed();
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
	
	@Override
	public void start() {
		setRunning(true);
		super.start();
	}
	
	@Override
	public void run() {
		while (running) {
			if (game.isAwareToPlay() && game.currentMove == null)
				if (game.getWinner() == Game.NO_WINNER)
					board.move(ai.move(game));
			
			game.update();
			
			try {
				Thread.sleep(board.getSpeed());
				if (game.getWinner() != Game.NO_WINNER) {
					setRunning(false);
					Thread.sleep(4000);
					game.setEnded(true);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
