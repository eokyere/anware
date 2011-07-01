package net.hutspace.anware.ai;

import java.util.List;

import net.hutspace.anware.core.Game;
import net.hutspace.anware.core.IllegalMove;
import android.util.Log;

public class MiniMax implements AI {
	@Override
	public int move(final Game game) {
		int pit = -1;
		final List<Integer> pits = game.validMoves();

		if (pits.size() >= 1) {
			final boolean max = isMax(game);
			int score = max ? Integer.MIN_VALUE : Integer.MAX_VALUE;

			final int depth = game.getDifficulty();

			for (int p : pits) {
				final Game g = game.clone();
				try {
					g.testMove(p);
					final int val = minimax(g, depth);
					if (val > score) {
						score = val;
						pit = p;
					}
				} catch (IllegalMove e) {
					//throw new RuntimeException();
					Log.e("MiniMax", String.format("Illegal Move tried: [%s]", p));
				}
			}
		}
		Log.d("MiniMax", String.format("Best AI move pit is: [%s]", pit));
		return pit;
	}

	private int minimax(final Game game, final int depth) {
		if (leaf(game) || 0 == depth) {
			return eval(game);
		}
		
		final boolean max = isMax(game);
		final List<Integer> moves = game.validMoves();
		
		int score = max ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		
		for (int i = 0; i < moves.size(); ++i) {
			final Game g = game.clone();
			try {
				g.testMove(moves.get(i));
				final int result = minimax(g, depth - 1);
				score = max ? Math.max(score, result) : Math.min(score, result);
			} catch (IllegalMove e) {
				throw new RuntimeException();
			}
		}
		
		return score;
	}
	
	private static int eval(Game game) {
		int seeds = 0;
		for (int i = 0; i < 6; ++i)
			seeds -= game.pit(i);
		for (int i = 6; i < 12; ++i)
			seeds += game.pit(i);

		return (game.store(Game.PLAYER_TWO) - game.store(Game.PLAYER_ONE)) * 1000 + seeds;
		
	}
	
	private boolean leaf(Game game) {
		return game.getWinner() != Game.NO_WINNER;
	}
	
	private boolean isMax(Game game) {
		return game.isAwareToPlay();
	}
}
