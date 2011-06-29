package net.hutspace.anware;

import java.util.ArrayList;
import java.util.List;

import net.hutspace.anware.core.Game;
import net.hutspace.anware.core.GameLoop;
import net.hutspace.anware.core.NamNamGame;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends Activity {
	public static final String STARTING_PLAYER_KEY = "net.hutspace.anware.startingPlayer";

	private Board board;
	private Game game;
	private TextView txtInfo;
	private List<View> pits;

	private GameLoop loop;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.board);	
		
		board = (Board) findViewById(R.id.game_board);
		txtInfo = (TextView) findViewById(R.id.txt_turn);
		Button btnUndo = (Button) findViewById(R.id.btn_undo);
		Button btnRedo = (Button) findViewById(R.id.btn_redo);
		
		
		btnUndo.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				game.undo();
			}
		});
		
		btnRedo.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				game.redo();
			}
		});
		
		int startingPlayer = getIntent().getIntExtra(STARTING_PLAYER_KEY, 0);
		if (startingPlayer == 2) {
			startingPlayer = (int) (Math.floor(Math.random() * 10000)) % 2; 
		}
		
		game = new NamNamGame(startingPlayer);
		
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

	public void update(Game game) {
		final int pId = game.getWinner();
		if (pId != Game.NO_WINNER) {
			String info;
			if (Game.DRAW == pId)
				info = "It is a draw!";
			else
				info = String.format("%s has won!!", playerName(pId));
			setInfo(info);
		} else {
			setInfo(String.format("%s to play", playerName(game.turn())));
		}
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
    	super.onSaveInstanceState(outState);
    	//outState.putBundle("game", saveState());
    }
	
	private void setInfo(final String s) {
		txtInfo.setText(s);
	}

	public View getPit(int i) {
		return pits.get(i);
	}
}
