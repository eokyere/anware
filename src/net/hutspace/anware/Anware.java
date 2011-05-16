package net.hutspace.anware;

import net.hutspace.anware.core.Game;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class Anware extends Activity implements OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        findViewById(R.id.about_button).setOnClickListener(this);
        findViewById(R.id.exit_button).setOnClickListener(this);
        findViewById(R.id.new_game_button).setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.new_game_button:
			final Context context = this;
			new AlertDialog.Builder(this).setTitle(R.string.new_game_label)
			.setItems(R.array.startingPlayer, 
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							final Intent intent = new Intent(context, GameActivity.class);
							intent.putExtra(GameActivity.STARTING_PLAYER_KEY, which);
							startActivity(intent);
						}
					}).show();			
			//startActivity(new Intent(this, GameActivity.class));
			break;
		case R.id.about_button:
			startActivity(new Intent(this, About.class));
			break;
		case R.id.exit_button:
			finish();
		}
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
}