package net.hutspace.anware.gui;

import java.util.ArrayList;
import java.util.List;

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
//	private static Drawable bg = new Background();
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
	
	private static final List<int[]> PEBBLES;
	static {
		PEBBLES = new ArrayList<int[]>();
		for (int i = 0; i < 48; ++i) {
			PEBBLES.add(COLORS[(int) Math.floor(Math.random() * COLORS.length)]);
		}
	}
	
	final static int PEBBLE_SZ = 6;
	final static int K = 1;
	
	private List<int[]> colors = new ArrayList<int[]>();
	private List<Point> points = new ArrayList<Point>();
	
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
		final int r2 = getHeight() / 2 - 2 - 7;
		
		int n = seeds();
		final int size = points.size();
		final int sz = getWidth() / 9;
		
		if (size > 0) {
			int i = 0;
			if (n < size) {
				points = new ArrayList<Point>();
				colors = new ArrayList<int[]>();
				for (; i < n; ++i) {
					final Point pt = getRandomPoint(r, r2);
					int[] cols = COLORS[(int) Math.floor(Math.random() * COLORS.length)];
					points.add(pt);
					colors.add(cols);
					drawPebbble(canvas, pt, cols, sz);
				}
			} else {
				for (; i < size; ++i)
					drawPebbble(canvas, points.get(i), colors.get(i), sz);
				if (n > size)
					for (; i < n; ++i)
					{
						final Point pt = getRandomPoint(r, r2);
						int[] cols = COLORS[(int) Math.floor(Math.random() * COLORS.length)];
						points.add(pt);
						colors.add(cols);
						drawPebbble(canvas, pt, cols, sz);
					}
			}
		} else {
			for (int i = 0; i < n; ++i) {
				final Point pt = getRandomPoint(r, r2);
				int[] cols = COLORS[(int) Math.floor(Math.random() * COLORS.length)];
				points.add(pt);
				colors.add(cols);
				drawPebbble(canvas, pt, cols, sz);
			}
		}
	}
	
	private void init() {
		//d = (GradientDrawable) getResources().getDrawable(R.drawable.rounded_rect);
		setBackgroundDrawable(new Background(this));
	}

	private static void drawPebbble(Canvas canvas, final Point offset, final int[] colors, int sz) {
		drawPebble(canvas, offset.x, offset.y, colors, sz);
	}

	private static void drawPebble(Canvas canvas, int xo, int yo, final int[] colors, int sz) {
		
		final int r = sz - K;
		final int x = xo + sz;
		final int y = yo + sz;

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

	private static Point getRandomPoint(float r, float r2) {
		double theta = Math.random() * 360;
		double s = Math.random() * r;
		return new Point((int) (Math.cos(theta) * s + r),
						 (int) (Math.sin(theta) * s + r2));
	}	
	
	private static class Background extends Drawable {
		private WoodenPit pit;

		public Background(WoodenPit pit) {
			this.pit = pit;
		}

		@Override
		public void draw(Canvas canvas) {
			final float cy = pit.getHeight() / 2;
			final float cx = pit.getWidth() / 2;
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
			
			final Point p = new Point((int) cx, (int) cy);
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

		private static int c(float pct) {
			return (int) ((pct/100) * 255);
		}
	}
}
