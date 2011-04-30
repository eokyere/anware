package net.hutspace.anware.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class GameActivity extends Activity {
	private static final String TAG = "Anware";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		Board board = new Board(this);
		setContentView(board);
		board.requestFocus();
	}	
}
