package net.hutspace.anware;

import java.util.Arrays;

import net.hutspace.anware.core.Game;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class GameSettings extends Activity implements OnClickListener {
	public static final int RESPONSE_PLAY = 100;
	public static final int RESPONSE_CANCEL = 101;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_settings);
		for (Integer id : Arrays.asList(R.id.player_one_button,
				   						R.id.player_two_button,
				   						R.id.difficulty_button, 
				   						R.id.play_button,
				   						R.id.cancel_button))
			try {
				findViewById(id).setOnClickListener(this);
			} catch (Exception e) {
				//System.out.println("oops: " + e.getMessage());
			}
			
    }

	@Override
	public void onClick(View v) {
        // player 1 drop down
        // player 2 drop down
        // if player 1 or 2 is ai, show level else hide
        // play
        // cancel
		
		switch (v.getId()) {
			case R.id.player_one_button:
			case R.id.player_two_button:
//				final Context context = this;
//				
//				new AlertDialog.Builder(this).
//					setTitle("Select").
//					setItems(R.array.startingPlayer, 
//						new DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialog, 
//									final int which) {
//								final Intent intent = new Intent(context, Anware.class);
//								intent.putExtra(Anware.STARTING_PLAYER_KEY, which);
//								startActivity(intent);
//							}
//						}).show();			
				//startActivity(new Intent(this, GameActivity.class));
				break;
			case R.id.play_button:
				Intent data = new Intent();
				data.putExtra(Anware.STARTING_PLAYER_KEY, Game.PLAYER_ONE);
				setResult(RESPONSE_PLAY, data);
				break;
			case R.id.cancel_button:
				setResult(RESPONSE_CANCEL);
		}
		finish();
	}
}