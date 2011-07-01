package net.hutspace.anware;

import java.util.Arrays;

import net.hutspace.anware.core.Game;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class GameSettings extends Activity implements OnClickListener {
	public static final int RESPONSE_PLAY = 100;
	public static final int RESPONSE_CANCEL = 101;
	private Spinner player_two;
	private Spinner player_one;
	private Spinner difficulty;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_settings);
		for (Integer id : Arrays.asList(R.id.play_button,
				   						R.id.cancel_button))
			findViewById(id).setOnClickListener(this);

		final Resources r = getResources();
		player_one = getSpinner(R.id.player_one_button);
		setValues(player_one,
				  new String[] {"Player One", "Anware"});
			
		player_two = getSpinner(R.id.player_two_button);
		setValues(player_two,
				 new String[] {"Player Two", "Anware"});
		difficulty = getSpinner(R.id.difficulty_button);
		setValues(difficulty, 
				  new String[] {r.getString(R.string.beginner),
								r.getString(R.string.intermediate),
								r.getString(R.string.advanced)});

		showOrHideDifficulty();
		
		player_one.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view,
					int arg2, long arg3) {
				int selected = adapterView.getSelectedItemPosition();
				final int selected2 = player_two.getSelectedItemPosition();
				if (selected == 1 && selected2 == 1)
					player_two.setSelection(0);
				showOrHideDifficulty();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		player_two.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view,
					int arg2, long arg3) {
				int selected = adapterView.getSelectedItemPosition();
				if (selected == 1 && player_one.getSelectedItemPosition() == 1)
					player_one.setSelection(0);
				showOrHideDifficulty();
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
    }

	private void showOrHideDifficulty() {
		difficulty.setVisibility(showDifficulty(player_one, player_two) ? 
								 View.VISIBLE: 
							     View.INVISIBLE);
	}

	private boolean showDifficulty(final Spinner player_one,
			final Spinner player_two) {
		return player_one.getSelectedItemPosition() + 
			player_two.getSelectedItemPosition() != 0;
	}
    
    private Spinner getSpinner(int id) {
    	return (Spinner)findViewById(id);
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setValues(Spinner spinner, final Object[] vals) {
		spinner.setAdapter(new ArrayAdapter(
							this,
							android.R.layout.simple_spinner_dropdown_item,
							vals));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.play_button:
				Intent data = new Intent();
				data.putExtra(Anware.PLAYER_ONE, 
						player_one.getSelectedItemPosition());
				data.putExtra(Anware.PLAYER_TWO, 
						player_two.getSelectedItemPosition());
				data.putExtra(Anware.STARTING_PLAYER_KEY, Game.PLAYER_ONE);
				data.putExtra(Anware.DIFFICULTY, 
						difficulty.getSelectedItemPosition() + 1);
				setResult(RESPONSE_PLAY, data);
				break;
			case R.id.cancel_button:
				setResult(RESPONSE_CANCEL);
		}
		finish();
	}
}