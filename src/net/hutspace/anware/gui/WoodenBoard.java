package net.hutspace.anware.gui;

import net.hutspace.anware.Board;
import net.hutspace.anware.Pit;
import net.hutspace.anware.R;
import android.content.Context;
import android.util.AttributeSet;

public class WoodenBoard extends Board {

	public WoodenBoard(Context context) {
		super(context);
		init();
	}

	public WoodenBoard(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public WoodenBoard(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	@Override
	protected Pit createPit(final int id) {
		final Pit pit = new WoodenPit(getContext());
		pit.setId(id);
		return pit;
	}

	@Override
	protected Pit createStore(final int id) {
		final Pit pit = new WoodenStore(getContext());
		pit.setId(id);
		return pit;
	}
	
	private void init() {
		setBackgroundResource(R.drawable.wood);
	}
}
