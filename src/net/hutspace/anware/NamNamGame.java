package net.hutspace.anware;

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
		return turn == owner[i] && pit(i) >= 1;
	}

	int sow(int from, int seeds) {
		System.out.println("Game.sow(" + from +", " + seeds + ")");
		int x = from;
		for (int i = 0; i < seeds; ++i) {
			x = pos(1 + i + from);
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
			int who = i == seeds - 1 ? turn: owner[x];
			stores[who] += scoop(x);
		}
	}
}
