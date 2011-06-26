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
		try {
			Log.d("NamNam", String.format("Check validity of: [%s]", i));
			return who == owner[i] && pit(i) >= 1;
		} catch (Exception e) {
			return false;
		}
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

	public boolean update() {
		if (hand >= 1) {
			hand--;
			drop(spot, hand);
			Log.d("NamNam", String.format("dropped! [%s]", hand));
			if (hand == 0) {
				if (pit(spot) > 1)
					hand = scoop(spot);
				else {
					Log.d("NamNam", "Hand is empty; updating moves");
					updateMoves();
				}

			}
			spot = pos(++spot);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Sows the seeds starting from the (emptied + 1) pit and returns the
	 * index of the pit where the last seed is drop.
	 * 
	 * @param emptied the pit from which seeds are scooped to be sown
	 * @param seeds the seeds to sow
	 */
	@Override
	int sow(final int emptied, int seeds) {
		Log.d("NamNam", String.format("sow(%s, %s)", emptied, seeds));
		int x = emptied;
		
		while (seeds >= 1) {
			x = pos(x + 1);
			seeds--;
			drop(x, seeds);
		}
		
		return x;
	}

	private void drop(final int pit, final int hand) {
		pits[pit] += 1;
		if (listener != null)
			listener.onSow(pit);
		checkHarvest(pit, hand == 0 ? turn(): owner[pit]);
	}
	
	int pos(int n) {
		return n % 12;
	}

	/**
	 * 
	 * @param x the pit to check if we should harvest
	 * @param i 
	 */
	private void checkHarvest(int x, int who) {
		if (pits[x] == 4) {
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
		updateHistory();
	}
	
	private void setPits(final int val) {
		for (int i = 0; i < pits.length; ++i)
			pits[i] = val;
	}
}
