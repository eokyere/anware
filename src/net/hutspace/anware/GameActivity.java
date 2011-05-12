package net.hutspace.anware;

import net.hutspace.anware.core.Game;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends Activity {
	Board board;
	TextView txtTurn;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.board);	
		
		board = (Board) findViewById(R.id.game_board);
		txtTurn = (TextView) findViewById(R.id.txt_turn);
		Button btnUndo = (Button) findViewById(R.id.btn_undo);
		Button btnRedo = (Button) findViewById(R.id.btn_redo);
		
		btnUndo.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				board.getGame().undo();
			}
		});
		
		btnRedo.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				board.getGame().redo();
			}
		});
		
		update(board.getGame());
	}

	public void update(Game game) {
		final int player = game.turn() == 0 ? 1 : 2;
		txtTurn.setText(String.format("Player %s to play", player));
	}
}
