package net.hutspace.anware.gui;

import android.content.Context;

public class WoodenStore extends WoodenPit {
	public WoodenStore(Context context) {
		super(context);
	}

	@Override
	protected int seeds() {
		return board.store(getId()/1000 - 1);
	}
}
