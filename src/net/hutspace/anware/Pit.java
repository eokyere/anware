package net.hutspace.anware;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.view.View;

public class Pit extends View {
	private float width;
	private float radius;


	public Pit(Context context) {
		super(context);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		width = w;
		//radius = 5/12 * width;
		super.onSizeChanged(w, h, oldw, oldh);
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		Path path = new Path();
		path.addCircle(50, 50, 40, Direction.CW);
	}
}
