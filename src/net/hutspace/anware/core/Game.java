package net.hutspace.anware.core;

import java.util.logging.Logger;

public abstract class Game {
	public static final int PLAYER_ONE = 0;
	public static final int PLAYER_TWO = 1;
	int[] pits;
	int[] stores;
	int who;
	private static Logger log = Logger.getLogger("net.hutspace.anware");
	
	
	Game() {
		pits = new int[12];
		stores = new int[2];
		who = 0;
	}
	
	public void render() {
		log.fine("[" + store(PLAYER_ONE) + "][");
		for (int i = 0; i < pits.length; ++i)
			System.out.print(pits[i]);
		System.out.print("][" + store(PLAYER_TWO) + "]");
	}
	
	/**
	 * If the move starting in the specified pit index is legal,
	 * this method adjusts the game state accordingly.  
	 * Otherwise, it raises an Exception.
	 * @throws IllegalMove 
	 */
	public void move(int i) throws IllegalMove {
		if (valid(i)){
			play(i);
			who = next();
		} else 
			throw new IllegalMove();
	}

	public int next() {
		return (turn() + 1) % 2;
	}

	public int turn() {
		return who;
	}
	
	public abstract boolean valid(int i);
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
