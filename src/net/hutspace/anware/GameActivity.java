package net.hutspace.anware;

import java.util.ArrayList;
import java.util.List;

import net.hutspace.anware.core.Game;
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
		board.setGame(game);

		pits = new ArrayList<View>();
		for (int i = 1; i < 13; ++i)
			pits.add(findViewById(i));
		
		
		update(game);
	}

	public void update(Game game) {
		if (game.getWinner() != -1) {
			final int player = game.getWinner() == 0 ? 1 : 2;
			setInfo(String.format("Player %s has won!!", player));
			return;
		}
		
		final int player = game.turn() == 0 ? 1 : 2;
		setInfo(String.format("Player %s to play", player));
	}

	private void setInfo(final String s) {
		txtInfo.setText(s);
	}

	public View getPit(int i) {
		return pits.get(i);
	}
}
