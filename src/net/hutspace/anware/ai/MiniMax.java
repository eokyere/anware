package net.hutspace.anware.ai;

import java.util.List;

import net.hutspace.anware.core.Game;
import net.hutspace.anware.core.IllegalMove;

public class MiniMax extends AI {
	@Override
	public int move(Game game) {
		int pit = -1;
		final List<Integer> pits = game.validMoves();

		if (pits.size() >= 1) {
			final boolean max = isMax(game);
			int score = max ? Integer.MIN_VALUE : Integer.MAX_VALUE;

			for (int p : pits) {
				final Game g = game.clone();
				try {
					g.move(p);
					final int val = minimax(g, 3);
					if (val > score) {
						score = val;
						pit = p;
					}
				} catch (IllegalMove e) {
					throw new RuntimeException();
				}
			}
		}
		
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
				g.move(moves.get(i));
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
		return game.getWinner() != -1;
	}
	
	private boolean isMax(Game game) {
		return game.turn() == Game.PLAYER_TWO;
	}
}
