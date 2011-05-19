package net.hutspace.anware.gui;

import net.hutspace.anware.Pit;
import net.hutspace.anware.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

public class WoodenPit extends Pit {
	static GradientDrawable d;
	
	public WoodenPit(Context context) {
		super(context);
		d = (GradientDrawable) getResources().getDrawable(R.drawable.rounded_rect);
		setBackgroundDrawable(d);
	}

	public WoodenPit(Context context, AttributeSet attrs) {
		super(context, attrs);
		d = (GradientDrawable) getResources().getDrawable(R.drawable.rounded_rect);
		setBackgroundDrawable(d);
	}

	public WoodenPit(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		d = (GradientDrawable) getResources().getDrawable(R.drawable.rounded_rect);
		setBackgroundDrawable(d);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		final float cx = getWidth() / 2;
		final float r = cx - 2;
		//Paint paint = new Paint();
//		GradientDrawable d = new GradientDrawable();
		//canvas.drawCircle(cx, cx, r, paint);
		//getContext().getResources()
	}

	@Override
	protected void onMeasure(int w, int h) {
		super.onMeasure(w, h);
		final float dpi = getResources().getDisplayMetrics().density;
		final int x = (int)(W * dpi);
		setMeasuredDimension(x, 2 * x - 10);
	}

}
