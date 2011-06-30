package net.hutspace.anware;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class StartDialog extends Activity implements OnClickListener {
	public static final int RESPONSE_EXIT = -1000;
	public static final int RESPONSE_NEW_GAME = 1000;
	public static final int RESPONSE_ABOUT = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_dialog);
        
        findViewById(R.id.new_game_button).setOnClickListener(this);
        findViewById(R.id.about_button).setOnClickListener(this);
        findViewById(R.id.exit_button).setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
			case R.id.new_game_button:
				setResult(RESPONSE_NEW_GAME);
				break;
			case R.id.about_button:
				setResult(RESPONSE_ABOUT);
				break;
			case R.id.exit_button:
				setResult(RESPONSE_EXIT);
		}
		finish();
	}

	private void aboutUs() {
		final Context context = this;
		new AlertDialog.Builder(this).
			setTitle(R.string.new_game_label).
			setItems(R.array.startingPlayer, 
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, 
							final int which) {
						final Intent intent = new Intent(context, Anware.class);
						intent.putExtra(Anware.STARTING_PLAYER_KEY, which);
						startActivity(intent);
					}
				}).show();			
		//startActivity(new Intent(this, GameActivity.class));
	}

	
    @Override
    public void onSaveInstanceState(Bundle outState) {
        //Store the game state
       // outState.putBundle("key", mSnakeView.saveState());
    }

	
}