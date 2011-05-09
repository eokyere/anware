package net.hutspace.anware;

import android.content.Context;
import android.graphics.Canvas;

public class Store extends Pit {
	public Store(Context context) {
		super(context);
	}

	int seeds() {
		return board.store(getId()/1000 - 1);
	}
}
