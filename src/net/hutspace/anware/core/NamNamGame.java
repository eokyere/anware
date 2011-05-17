package net.hutspace.anware.core;

import android.util.Log;

public class NamNamGame extends Game {
	public NamNamGame() {
		super();
		init();
	}

	public NamNamGame(final int who) {
		super(who);
		init();
	}
	
	public NamNamGame(int who, int[] pits, int[] stores, int[] owner) {
		super(who, pits, stores, owner);
		init();
	}

	@Override
	public boolean valid(int i) {
		return who == owner[i] && pit(i) >= 1;
	}

	@Override
	public Game clone() {
		final Game g = new NamNamGame();
		g.pits = pits.clone();
		g.stores = stores.clone();
		g.who = who;
		g.owner = owner.clone();
		
		return g;
	}

	/**
	 * Sows the seeds starting from the (emptied + 1) pit and returns the
	 * index of the pit where the last seed is drop.
	 * 
	 * @param emptied the pit from which seeds are scooped to be sown
	 * @param seeds the seeds to sow
	 */
	@Override
	int sow(int emptied, int seeds) {
		Log.d("NamNam", String.format("sow(%s, %s)", emptied, seeds));
		int x = emptied;
		for (int i = 0; i < seeds; ++i) {
			x = pos(1 + i + emptied);
			pits[x] += 1;
			if (listener != null)
				listener.onSow(x);
			checkHarvest(seeds, x, i);
		}
		
		return x;
	}
	
	int pos(int n) {
		return n % 12;
	}

	private void checkHarvest(int seeds, int x, int i) {
		if (pits[x] == 4) {
			int who = i == seeds - 1 ? turn(): owner[x];
			stores[who] += scoop(x);
			if (listener != null)
				listener.onHarvest(x, who);
			checkGameEnd(who);
		}
	}

	private void checkGameEnd(int who) {
		if (44 == stores[PLAYER_ONE] + stores[PLAYER_TWO]) {
			stores[who] += 4;
			clearPits();
		}
	}

	private void clearPits() {
		setPits(0);
	}

	private void init() {
		setPits(4);
		for (int i = 0; i < 2; ++i)
			stores[i] = 0;
		
		snap();
	}
	
	private void setPits(final int val) {
		for (int i = 0; i < pits.length; ++i)
			pits[i] = val;
	}
}
