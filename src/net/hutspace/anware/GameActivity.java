package net.hutspace.anware;

import net.hutspace.anware.core.Game;
import net.hutspace.anware.core.NamNamGame;
import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity {
	private Game game;
	private Board board;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.board);
	}
	
}
