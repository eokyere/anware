package net.hutspace.anware.core;

import android.util.Log;

public class NamNamGame extends Game {
	int[] owner;
	
	public NamNamGame() {
		super();
		
		for (int i = 0; i < 12; ++i)
			pits[i] = 4;
		for (int i = 0; i < 2; ++i)
			stores[i] = 0;
		
		owner = new int[12];
		for (int i = 0; i < 6; ++i)
			owner[i] = PLAYER_ONE;
		for (int i = 6; i < 12; ++i)
			owner[i] = PLAYER_TWO;
	}
	
	@Override
	public boolean valid(int i) {
		return who == owner[i] && pit(i) >= 1;
	}

	/**
	 * Sows the seeds starting from the (emptied + 1) pit and returns the
	 * index of the pit where the last seed is drop.
	 * 
	 * @param emptied the pit from which seeds are scooped to be sown
	 * @param seeds the seeds to sow
	 */
	int sow(int emptied, int seeds) {
		Log.d("NamNam", String.format("sow(%s, %s)", emptied, seeds));
		int x = emptied;
		for (int i = 0; i < seeds; ++i) {
			x = pos(1 + i + emptied);
			pits[x] += 1;
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
			checkGameEnd(who);
		}
	}

	private void checkGameEnd(int who) {
		if (44 == stores[PLAYER_ONE] + stores[PLAYER_TWO]) {
			stores[who] += 4;
			clear();
		}
	}

	private void clear() {
		for (int i = 0; i < pits.length; ++i)
			pits[i] = 0;
	}
}
