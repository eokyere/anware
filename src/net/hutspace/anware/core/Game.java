package net.hutspace.anware.core;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;


public abstract class Game {
	public static final int PLAYER_ONE = 0;
	public static final int PLAYER_TWO = 1;
	
	int[] pits;
	int[] stores;
	int[] owner;
	int who;

	GameListener listener;
	
	private List<Position> history;
	private List<Integer> moves;
	private int index;
	
	
	private static class Position {
		public Position(int[] pits, int[] stores, int who) {
			this.pits = pits;
			this.stores = stores;
			this.who = who;
		}
		
		int[] pits;
		int[] stores;
		int who;
	}
	
	public void setListener(GameListener listener) {
		this.listener = listener;
	}

	
	Game() {
		this(PLAYER_ONE);
	}


	Game(final int startingPlayer) {
		this(startingPlayer, new int[12], new int[2]);
	}
	
	Game(final int who, final int[] pits, final int[] stores, int[] owner) {
		this.who = who;
		this.pits = pits;
		this.stores = stores;
		index = 0;
		moves = new ArrayList<Integer>();
		history = new ArrayList<Position>();
		
		if (null == owner) {
			owner = new int[12];
			for (int i = 0; i < 6; ++i)
				owner[i] = PLAYER_ONE;
			for (int i = 6; i < 12; ++i)
				owner[i] = PLAYER_TWO;
		}
		this.owner = owner;
	}
	
	public Game(int who, int[] pits, int[] stores) {
		this(who, pits, stores, null);
	}


	@Override
	public abstract Game clone();
	
	/**
	 * If the move starting in the specified pit index is legal,
	 * this method adjusts the game state else it raises an Exception.
	 * 
	 * @throws IllegalMove 
	 */
	public void move(int i) throws IllegalMove {
		if (valid(i)){
			final int size = moves.size();
			if (index < size) {
				history.subList(index + 1, history.size()).clear();
				moves.subList(index, size).clear();
			}
			
			play(i);
			moves.add(i);
			++index;
			who = next();
			if (listener != null)
				listener.onNext();
			snap();
		} else 
			throw new IllegalMove();
	}

	/**
	 * Take a snapshot of the current game position
	 */
	void snap() {
		history.add(new Position(pits.clone(), stores.clone(), who));
	}

	public void undo() {
		if (index > 0) {
			restore(--index);
			if (listener != null)
				listener.onUndo();
		}
	}


	public void redo() {
		if (index < moves.size()) {
			restore(++index);
			if (listener != null)
				listener.onRedo();
		}
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
		if (listener!= null)
			listener.onScoop(i, seeds);
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
	
	private void restore(final int index) {
		Position p = history.get(index);
		pits = p.pits.clone();
		stores = p.stores.clone();
		who = p.who;
	}


	public int getWinner() {
		if (totalSeeds() == stores[PLAYER_ONE] + stores[PLAYER_TWO])
			return stores[PLAYER_ONE] > stores[PLAYER_TWO] ? PLAYER_ONE : PLAYER_TWO;
		return -1;
	}


	protected int totalSeeds() {
		return 48;
	}


	public List<Integer> validMoves() {
		final List<Integer> xs = new ArrayList<Integer>();
		for (int i = 0; i < pits.length; ++i)
			if (valid(i))
				xs.add(i);
		return xs;
	}


	public int[] getPits() {
		return pits;
	}


	public int[] getStores() {
		return stores;
	}


	public int[] getOwners() {
		return owner;
	}
}
