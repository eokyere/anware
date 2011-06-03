package net.hutspace.anware.core;

import net.hutspace.anware.Board;
import net.hutspace.anware.GameActivity;
import android.graphics.Canvas;
import android.util.Log;

public class GameLoop extends Thread {
	private static final String TAG = "GameLoop";
	// flag to hold game state
	private boolean running;
	private Game game;
	private Board board;

	public GameLoop(Board board, Game game) {
		super();
		this.game = game;
		this.board = board;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
	
	@Override
	public void run() {
		Canvas canvas;
		long ticks = 0L;
		Log.d(TAG, "Game loop started");
		while (running) {
			
			canvas = null;
			++ticks;
			// update game state
			if (game.update())
				// render state to the screen
				board.invalidate();
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Log.d(TAG, "Game loop executed " + ticks + " times");

	}
}
