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
	private int speed;

	public GameLoop(Board board, Game game) {
		super();
		this.game = game;
		this.board = board;
		speed = board.getSpeed();
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
			Log.d(TAG, String.format("running - turn: %s", game.turn()));

			if (game.aiToPlay() && game.currentMove == null) {
				if (game.getWinner() == -1)
				{
					Log.d("Board", "AI thinking ...");
					board.move(ai.move(game));
				}
			}
			
			game.update();
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
