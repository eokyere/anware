package net.hutspace.anware;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.view.View;

public class Board extends View {
	private float radius;
	private float width;
	private Path curr;
	
	public Board(Context context) {
		super(context);
		setFocusable(true);
		setFocusableInTouchMode(true);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		width = w/8f;
		radius = 5/12 * width;
		super.onSizeChanged(w, h, oldw, oldh);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// draw background
		// draw board
		// draw seeds (numbers)
		// draw hints
		// draw selection
	}
	
}
