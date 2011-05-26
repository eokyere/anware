package net.hutspace.anware.gui;

import net.hutspace.anware.Pit;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

public class WoodenPit extends Pit {
	private static Drawable bg = new PitBackground();
	
	public WoodenPit(Context context) {
		super(context);
		init();
	}

	public WoodenPit(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public WoodenPit(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	@Override
	protected void onDraw(Canvas canvs) {
	}
	
	@Override
	protected void onMeasure(int w, int h) {
		super.onMeasure(w, h);
		final float dpi = getResources().getDisplayMetrics().density;
		final int x = (int)(W * dpi);
		setMeasuredDimension(x, x);
	}
	
	private void init() {
		//d = (GradientDrawable) getResources().getDrawable(R.drawable.rounded_rect);
		setBackgroundDrawable(bg);
	}
	
	private static class PitBackground extends Drawable {
		private int c(float pct) {
			return (int) ((pct/100) * 255);
		}

		@Override
		public void draw(Canvas canvas) {
			final int w = (int) W;
			final float cx = w / 2;
			final float r = cx - 1;
			
			
			final int[] colors = new int[4];
			colors[3] = Color.argb(255, 0, 0 ,0);
			colors[2] = Color.argb(255, 0, 0, 0);
			colors[1] = Color.argb(150, 0, 0, 0);
			colors[0] = Color.argb(0, 255, 255, 255);
			
			final float[] positions = new float[4];
			positions[3] = 1 - 0.00f;
			positions[2] = 1 - 0.08f;
			positions[1] = 1 - 0.20f;
			positions[0] = 1 - 1.0f;
			
			final Point p = new Point((int) cx, (int) cx);
			final RadialGradient shader = new RadialGradient(p.x, p.y, r,
					colors, positions, TileMode.CLAMP);
			
			final RectF rect = new RectF(p.x - r, p.y - r, p.x + r, p.y + r);
			
			final Paint paint = new Paint(); 
			paint.setShader(shader);
			paint.setAlpha(c(50));
			paint.setAntiAlias(true);
			canvas.drawOval(rect, paint);
		}

		@Override
		public int getOpacity() {
			return 0;
		}

		@Override
		public void setAlpha(int alpha) {
		}

		@Override
		public void setColorFilter(ColorFilter cf) {
		}
	}
}
