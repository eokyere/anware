package net.hutspace.anware;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class Pit extends View {
	private static Paint paint = new Paint();
	public static final float W = 54.0f;

	public Pit(Context context) {
		super(context);
	}

	public Pit(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public Pit(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int w, int h) {
		super.onMeasure(w, h);
		final float dpi = getResources().getDisplayMetrics().density;
        final int x = (int)(W * dpi);
		setMeasuredDimension(x, x);
    }
		
	@Override
	protected void onDraw(Canvas canvas) {
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.STROKE);
		final float cx = getWidth() / 2;
		final float r = cx - 2;
		canvas.drawCircle(cx, cx, r, paint);
		canvas.drawText("" + getId(), cx, cx, paint);
	}
	
}
