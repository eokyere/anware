package net.hutspace.anware.core;

import android.util.Log;


/**
 * Nam-Nam - A traditional Ghanaian variant of Oware
 * 
 * Nam-nam: A Twi word (language spoken amongst the Akans of Ghana) meaning, 
 * "to roam" which is descriptive of the nature of play in this version of the 
 * game. This version of Oware is also played all along the West African Coast 
 * and the Caribbean. It also has many names: Jerin-Jerin (Yoruba-Nigeria), 
 * Round & Round (Antigua) etc.
 * 
 * Arrangement of board at the beginning of each game or round:
 * Four seeds are placed in each house on a board that is made up of two rows of 
 * six houses making twelve in total. 
 * 
 * Each row of six houses is the farm of the player sitting nearest to 
 * them, with two end houses used as stores for harvested seeds.
 * 
 * Object of the game:
 * The object of the game is to harvest all the opponents, farm. This 
 * ultimate aim is usually achieved after at least several rounds.
 * 
 * Starting:
 * Each player takes it in turns to start. The player must then choose a house 
 * from their own farm, from which all the seeds are scooped.
 * 
 * Sowing seeds
 * The seeds are then sown in an anticlockwise direction placing one seed in 
 * each house as one travel, around the board. If the last seed drops in a 
 * house with seeds in it, all the seeds are scooped up and then sown until 
 * ones last seed lands in a house, which is empty. 
 * 
 * If at any point in the game one player does not have any seeds, the other 
 * player must sow seeds from a house that would provide the opponent with 
 * seeds to continue playing the game. If this is not possible then the game 
 * comes to an end, with the remaining seeds going the player who has the seeds 
 * on their farm.
 * 
 * 
 * End of round
 * When there are eight seeds left in play, since it is impossible to play for 
 * the last four seeds. The player who harvests the penultimate set of four 
 * seeds, also gains the last set of four on the board, this then ends the 
 * round. The winner of the first round is the one who has harvested the most 
 * seeds.
 * 
 * Second round
 * Upon completion of the first round each player places the seeds they have 
 * won back in the houses on their farm four seeds in each house. 
 * 
 * If each is able to fill the same number of houses then it is a draw. 
 * However if one player is able to fill more houses than that which they 
 * started with then those houses filled, on the opponents side now become 
 * part of their farm in the next round. The winner of each round is the 
 * one with the most farm. This capturing of farm can go on for 
 * quite a while as lost farm can always be reclaimed in subsequent rounds.
 * 
 * End of game
 * The game ends when one persons farm (six houses) is completely harvested.
 * 
 * @author Emmanuel Okyere <chief@hutspace.net>
 */
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
		final Game game = new NamNamGame();
		game.pits = pits.clone();
		game.stores = stores.clone();
		game.who = who;
		game.owner = owner.clone();
		game.difficulty = difficulty;
		
		return game;
	}

	public boolean update() {
		if (hand >= 1) {
			hand--;
			drop(spot, hand);
			if (hand == 0) {
				if (pit(spot) > 1)
					hand = scoop(spot);
				else
					updateMoves();
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
		int x = emptied;
		
		while (seeds >= 1) {
			x = pos(x + 1);
			seeds--;
			drop(x, seeds);
		}
		
		return x;
	}

	int pos(int n) {
		return n % 12;
	}

	private void drop(final int pit, final int hand) {
		pits[pit] += 1;
		if (listener != null)
			listener.onSow(pit);
		checkHarvest(pit, hand == 0 ? turn(): owner[pit]);
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
