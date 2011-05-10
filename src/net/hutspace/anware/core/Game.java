package net.hutspace.anware.core;

import android.util.Log;


public abstract class Game {
	public static final int PLAYER_ONE = 0;
	public static final int PLAYER_TWO = 1;
	int[] pits;
	int[] stores;
	int who;
	
	public void setGameListener(GameListener gameListener) {
		this.gameListener = gameListener;
	}

	GameListener gameListener;
	
	Game() {
		pits = new int[12];
		stores = new int[2];
		who = 0;
	}
	
	/**
	 * If the move starting in the specified pit index is legal,
	 * this method adjusts the game state else it raises an Exception.
	 * 
	 * @throws IllegalMove 
	 */
	public void move(int i) throws IllegalMove {
		if (valid(i)){
			play(i);
			who = next();
		} else 
			throw new IllegalMove();
	}

	/**
	 * Who is next
	 * 
	 * @return Returns the index of the player who is next to play.
	 */
	public int next() {
		return (turn() + 1) % 2;
	}

	/**
	 * Whose turn is it?
	 * 
	 * @return Returns the index of the player whose turn it is.
	 */
	public int turn() {
		return who;
	}
	
	/**
	 * How many seeds are in a pit?
	 * 
	 * @param i Index of the pit to check
	 * @return Returns the number of seeds in a pit.
	 */
	public int pit(int i) {
		return pits[i];
	}

	/**
	 * How many seeds are in a store?
	 * 
	 * @param i Index of the store to check.
	 * @return Returns the number of seeds in a store.
	 */
	public int store(int i) {
		return stores[i];
	}
	
	/**
	 * Scoop the contents of the specified pit, and update board state.
	 * 
	 * @param i Index of the pit to scoop
	 * @return Returns the number of seeds scooped.
	 */
	public int scoop(int i) {
		int seeds = pit(i);
		pits[i] = 0;
		if (gameListener!= null)
			gameListener.scoop(i, seeds);
		Log.d("Game", String.format("scoop(%s) = %s", i, seeds));
		return seeds;
	}

	/**
	 * Checks validity of a move.
	 * 
	 * @param i The pit index to test for move validity
	 * @return Returns true if it is valid to move from the specified index.
	 */
	public abstract boolean valid(int i);
	
	abstract int sow(int from, int seeds);
	abstract int pos(int n);
	
	void play(int i) {
		i = sow(i, scoop(i));
		if (pit(i) > 1)
			play(i);
	}
}
