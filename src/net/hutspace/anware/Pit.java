package net.hutspace.anware;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class Pit extends View {
	private static final String TAG = "Pit";
	static Paint paint = new Paint();
	public static final float W = 54.0f;
	public static final float H = W;
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
		canvas.drawCircle(cx, cy, r, paint);
		canvas.drawText("" + seeds(), cx, 
						id <= 5 ? getHeight() + 2 : 1 , paint);
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
		setMeasuredDimension((int) dpi(W), (int) dpi(H));
	}

	private float dpi(final float val) {
		final float dpi = getResources().getDisplayMetrics().density;
		return val / dpi;
	}
	
	private void init(Context context) {
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.STROKE);
	}
}
