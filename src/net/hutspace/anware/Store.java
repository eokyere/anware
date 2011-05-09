package net.hutspace.anware;

import android.content.Context;
import android.graphics.Canvas;

public class Store extends Pit {
	public Store(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		final float cx = getWidth() / 2;
		final float r = cx - 2;
		canvas.drawCircle(cx, cx, r, paint);
		canvas.drawText("" + getId(), cx, cx, paint);
	}
}
