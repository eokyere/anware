package net.hutspace.anware;

import java.util.ArrayList;
import java.util.List;

import net.hutspace.anware.core.Game;
import net.hutspace.anware.core.GameLoop;
import net.hutspace.anware.core.NamNamGame;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Anware extends Activity {
	public static final String STARTING_PLAYER_KEY = "net.hutspace.anware.startingPlayer";

	private Board board;
	private Game game;
//	private TextView txtInfo;
	private List<View> pits;

	private GameLoop loop;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.board);	
		
		board = (Board) findViewById(R.id.gameBoard);
//		txtInfo = (TextView) findViewById(R.id.txt_turn);
//		Button btnUndo = (Button) findViewById(R.id.btn_undo);
//		Button btnRedo = (Button) findViewById(R.id.btn_redo);
		
		
//		btnUndo.setOnClickListener(new OnClickListener() {			
//			@Override
//			public void onClick(View v) {
//				game.undo();
//			}
//		});
//		
//		btnRedo.setOnClickListener(new OnClickListener() {			
//			@Override
//			public void onClick(View v) {
//				game.redo();
//			}
//		});
		
		game = new NamNamGame(getStartPlayer());
		
//		if (null == savedInstanceState) {
//		} else {
//			Bundle map = savedInstanceState.getBundle("game");
//			
//			int[] pits = map.getIntArray("pits");
//			int[] owner = map.getIntArray("owner");
//			int[] stores = map.getIntArray("stores");
//			int who = map.getInt("who");
//			
//			game = new NamNamGame(who, pits, stores, owner);
//		}
		board.setGame(game);

		pits = new ArrayList<View>();
		for (int i = 1; i < 13; ++i)
			pits.add(findViewById(i));
		
		update(game);
		loop = new GameLoop(board, game);
		loop.setRunning(true);
		loop.start();
	}

	private int getStartPlayer() {
		final int p = getIntent().getIntExtra(
				STARTING_PLAYER_KEY, Game.PLAYER_ONE);
		if (p == Game.PLAYER_ONE || p == Game.PLAYER_TWO)
			return p;
		return (int) (Math.floor(Math.random() * 10000)) % 2; 
	}

	public void update(Game game) {
		update(gameInfo(game));
	}

	public void update(final String info) {
		setInfo(info);
	}

	private String gameInfo(Game game) {
		final int pId = game.getWinner();
		if (pId != Game.NO_WINNER) {
			if (Game.DRAW == pId)
				return "It is a draw!";
			else
				return String.format("%s has won!!", playerName(pId));
		} else
			return String.format("%s to play", playerName(game.turn()));
	}

	private String playerName(final int pId) {
		return String.format("Player %s", pId == Game.PLAYER_ONE ? 1 : 2);
	}
	
	
	
	 /**
     * Save game state so that the user does not lose anything
     * if the game process is killed while we are in the 
     * background.
     * 
     * @return a Bundle with this view's state
     */
//    public Bundle saveState() {
//        Bundle map = new Bundle();
//        
//        map.putIntArray("pits", game.getPits());
//        map.putIntArray("stores", game.getStores());
//        map.putIntArray("owner", game.getOwners());
//        map.putInt("who", Integer.valueOf(game.turn()));
//
//        return map;
//    }	

    @Override
    public void onSaveInstanceState(Bundle outState) {
    	//outState.putBundle("game", saveState());
    }
	
	private void setInfo(final String s) {
		//txtInfo.setText(s);
		Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
	}

	public View getPit(int i) {
		return pits.get(i);
	}
}
