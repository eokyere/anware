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
	private static final String TAG = "Pit";
	static Paint paint = new Paint();
	public static final float W = 29.0f;
	public static final float H = 50.0f;
	protected Board board;
	
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
		final int id = getId();
		Log.d(TAG, String.format("onDraw(id=%s)", id));
		final float cx = getWidth() / 2;
		final float cy = getHeight() / 2;
		final float r = Math.min(cx, cy) - 2;
		canvas.drawText("" + seeds(), cx, 10 , paint);
		canvas.drawCircle(cx, cy, r, paint);
	}
	
	protected int seeds() {
		return board.pit(getId() - 1);
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
		board.move(getId() - 1);
		return true;
	}
	
	@Override
	protected void onMeasure(int w, int h) {
		super.onMeasure(w, h);
		setMeasuredDimension((int) px(W), (int) px(H));
	}

	private float px(final float val) {
		return val / getResources().getDisplayMetrics().density;
	}
	
	private void init(Context context) {
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.STROKE);
	}
}
