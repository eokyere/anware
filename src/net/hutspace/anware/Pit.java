package net.hutspace.anware;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class Pit extends View {
	static Paint paint = new Paint();
	public static final float W = 54.0f;
	private Board board;
	
	public Pit(Context context) {
		super(context);
		init(context);
	}

	public Pit(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public Pit(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		final float cx = getWidth() / 2;
		final float r = cx - 2;
		canvas.drawCircle(cx, cx, r, paint);
		final int id = getId();
		Log.d("Pit", String.format("onDraw (%s)", id));
		final int seeds = board.seeds(id);
		Log.d("Pit", String.format("onDraw (%s, %s)", id, seeds));
		canvas.drawText("" + seeds, cx, cx, paint);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		board = (Board) getParent();
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() != MotionEvent.ACTION_DOWN)
			return super.onTouchEvent(event);
		
		final int index = getId();
		board.move(index);
		Log.d("Pit", String.format("onTouchEvent (%s)", index));
		return true;
	}
	
	@Override
	protected void onMeasure(int w, int h) {
		super.onMeasure(w, h);
		final float dpi = getResources().getDisplayMetrics().density;
		final int x = (int)(W * dpi);
		setMeasuredDimension(x, x);
	}
	
	private void init(Context context) {
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.STROKE);
	}
}
