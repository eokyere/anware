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
import android.util.AttributeSet;

public class WoodenPit extends Pit {
	private static Drawable bg = new Background();
	private static int[][] COLORS = new int[][] {
		new int[] { Color.rgb(0x66, 0x77, 0xee),
					Color.rgb(0x00, 0x22, 0x77) },
		new int[] { Color.rgb(0xdd, 0xbb, 0x66),
					Color.rgb(0x77, 0x33, 0x00) },
		new int[] { Color.rgb(0x66, 0xcc, 0xff),
					Color.rgb(0x11, 0x77, 0x77) },
		new int[] { Color.rgb(0xcc, 0x66, 0x66),
					Color.rgb(0x88, 0x00, 0x00) },
		new int[] { Color.rgb(0xcc, 0x66, 0xdd),
					Color.rgb(0x55, 0x00, 0x77) },
		new int[] { Color.rgb(0x77, 0xcc, 0x66),
					Color.rgb(0x22, 0x77, 0x00) } };
	final static int PEBBLE_SZ = 6;
	final static int K = 1;
	
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
	protected void onDraw(Canvas canvas) {
		// drawCircle(canvas);
		final int r = getWidth() / 2 - 2 - 7;
		
		for (int i = 0; i < seeds(); ++i) {
			final Point pt = getRandomPoint(r);
			drawPebbble(canvas, pt,
					COLORS[(int) Math.floor(Math.random() * COLORS.length)]);
		}
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

	private static void drawPebbble(Canvas canvas, final Point offset, final int[] colors) {
		drawPebble(canvas, offset.x, offset.y, colors);
	}

	private static void drawPebble(Canvas canvas, int xo, int yo, final int[] colors) {
		final int r = PEBBLE_SZ - K;
		final int x = xo + PEBBLE_SZ;
		final int y = yo + PEBBLE_SZ;

		drawShadow(canvas, x, 3 * r - 2 + yo, (int) 1.5 * r);
		drawPebble(canvas, x, y, r, colors);
	}

	private static void drawShadow(Canvas canvas, final int x, final int y, final int r) {
		final int[] colors = new int[2];
		colors[0] = Color.argb(127, 0, 0, 0);
		colors[1] = Color.argb(0, 0, 0, 0);

		final float[] offset = new float[2];
		offset[1] = 1.0f;
		offset[0] = 0.0f;

		final Paint brush = new Paint();
		brush.setAntiAlias(true);
		brush.setShader(new RadialGradient(x, y, r, colors, offset,
				TileMode.CLAMP));

		canvas.save();
		canvas.scale(1.0f, 0.5f);
		canvas.translate(0, y - r);
		canvas.drawOval(new RectF(x - r, y - r, x + r, y + r), brush);
		canvas.restore();
	}

	private static void drawPebble(Canvas canvas, final int x, final int y, final int r,
			final int[] colors) {
		final float[] offset = new float[2];
		offset[1] = 1.0f;
		offset[0] = 0.0f;

		final Paint brush = new Paint();
		brush.setAntiAlias(true);
		brush.setShader(new RadialGradient(x, y - r / 2, r, colors, offset,
				TileMode.CLAMP));

		canvas.drawOval(new RectF(x - r, y - r, x + r, y + r), brush);

		final Paint p = new Paint();
		p.setColor(Color.argb(127, 255, 255, 255));
		canvas.drawPoint(x - 1, y - r / 2, p);
	}

	private static Point getRandomPoint(float r) {
		double theta = Math.random() * 360;
		double s = Math.random() * r;
		return new Point((int) (Math.cos(theta) * s + r),
				(int) (Math.sin(theta) * s + r));
	}	
	
	private static class Background extends Drawable {
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
