package net.hutspace.anware;

import java.util.ArrayList;
import java.util.List;

import net.hutspace.anware.core.Game;
import net.hutspace.anware.core.GameLoop;
import net.hutspace.anware.core.NamNamGame;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Anware extends Activity {
	public static final String STARTING_PLAYER_KEY = "net.hutspace.anware.startingPlayer";

	public static final int REQUEST_START_DIALOG = 1;
	private static final int REQUEST_GAME_SETTINGS = 2;

	private Board board;
	private Game game;
//	private TextView txtInfo;
	private List<View> pits;

	private GameLoop loop;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.board);	
		
		board = (Board) findViewById(R.id.game_board);

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
		board.setGame(game);

		pits = new ArrayList<View>();
		for (int i = 1; i < 13; ++i)
			pits.add(findViewById(i));

		//		intent.putExtra(Anware.STARTING_PLAYER_KEY, which);
		showStartDialog();		
//		start();
	}

	private void showStartDialog() {
		startActivityForResult((new Intent(this, StartDialog.class)), 
								REQUEST_START_DIALOG);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.settings:
			startActivity(new Intent(this, Prefs.class));
			return true;
		}
		return false;
	}
	
	protected void onActivityResult(int req, int resp, Intent data) {
		switch (req) {
		case (REQUEST_START_DIALOG):
			switch (resp) {
			case StartDialog.RESPONSE_EXIT:
				exit();
				break;
			case StartDialog.RESPONSE_ABOUT:
				break;
			case StartDialog.RESPONSE_NEW_GAME:
				// do updates to game
				//start();
				startActivityForResult(new Intent(this, GameSettings.class), 	
									   REQUEST_GAME_SETTINGS);
			}
			break;
		case (REQUEST_GAME_SETTINGS):
			switch (resp) {
			case GameSettings.RESPONSE_PLAY:
				start();
				break;
			case GameSettings.RESPONSE_CANCEL:
				if (loop == null || !loop.isAlive())
					showStartDialog();
				break;
			}
		}
	}

	private void exit() {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			finish();
		}
	}
	

	private void start() {
		update(game);
		loop = new GameLoop(board, game);
		loop.setRunning(true);
		loop.start();
	}

	private Game getSavedGame(Bundle savedInstanceState) {
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
		return null;
	}

	private int getStartPlayer() {
		final int p = getIntent().getIntExtra(
				STARTING_PLAYER_KEY, Game.PLAYER_ONE);
		if (p == Game.PLAYER_ONE || p == Game.PLAYER_TWO)
			return p;
		return (int) (Math.floor(Math.random() * 10000)) % 2; 
	}

	public void update(Game game) {
		update(game.getUpdate());
	}

	public void update(final String info) {
		//txtInfo.setText(s);
		Toast.makeText(getApplicationContext(), 
					   info, 
					   Toast.LENGTH_SHORT).show();
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
	
	public View getPit(int i) {
		return pits.get(i);
	}
}
