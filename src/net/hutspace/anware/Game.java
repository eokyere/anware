package net.hutspace.anware;

public abstract class Game {
	static final int PLAYER_ONE = 0;
	static final int PLAYER_TWO = 1;
	int[] pits;
	int[] stores;
	int turn;
	
	static int rows = 2;
	static int cols = 6;
	
	Game() {
		pits = new int[12];
		stores = new int[2];
		turn = 0;
	}
	
	/**
	 * If the move starting in the specified pit index is legal,
	 * this method adjusts the game state accordingly.  
	 * Otherwise, it raises an Exception.
	 */
	public void move(int i) {
		if (isValidMove(i))
			play(i);
		turn = (turn + 1) % 2;
	}
	
	public abstract boolean isValidMove(int i);
	abstract int sow(int from, int seeds);
	abstract int pos(int n);
	
	void play(int i) {
		i = sow(i, scoop(i));
		if (pit(i) > 1)
			play(i);
	}
	
	public int pit(int i) {
		return pits[i];
	}

	public int store(int i) {
		return stores[i];
	}
	
	public int scoop(int i) {
		int seeds = pit(i);
		pits[i] = 0;
		System.out.println("Game.scoop(" + i + ") = " + seeds);
		return seeds;
	}
}
