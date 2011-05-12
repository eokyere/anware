package net.hutspace.anware;

import android.content.Context;

public class Store extends Pit {
	public Store(Context context) {
		super(context);
	}

	@Override
	protected int seeds() {
		return board.store(getId()/1000 - 1);
	}
}
