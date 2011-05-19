package net.hutspace.anware.gui;

import android.content.Context;
import android.util.AttributeSet;
import net.hutspace.anware.Board;
import net.hutspace.anware.Pit;
import net.hutspace.anware.R;
import net.hutspace.anware.Store;

public class WoodenBoard extends Board {

	public WoodenBoard(Context context) {
		super(context);
		setBackgroundResource(R.drawable.wood);
	}

	public WoodenBoard(Context context, AttributeSet attrs) {
		super(context, attrs);
		setBackgroundResource(R.drawable.wood);
	}

	public WoodenBoard(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setBackgroundResource(R.drawable.wood);
	}

	@Override
	protected Pit createPit(final int id) {
		final Context context = getContext();
		final Pit pit = new WoodenPit(context);
		pit.setId(id);
		return pit;
	}

	@Override
	protected Pit createStore(final int id) {
		final Context context = getContext();
		final Pit pit = new Store(context);
		pit.setId(id);
		return pit;
	}
}
