package net.hutspace.anware;

import net.hutspace.anware.core.Game;
import net.hutspace.anware.core.GameListener;
import net.hutspace.anware.core.IllegalMove;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

public class Board extends RelativeLayout implements GameListener {
	private static final String TAG = "Board";

	static final int below = RelativeLayout.BELOW;
	static final int leftOf = RelativeLayout.LEFT_OF;
	static final int rightOf = RelativeLayout.RIGHT_OF;
	static final int above = RelativeLayout.ABOVE;

	private Anware anware;
	private Game game;
	
	public Board(Context context) {
		super(context);
		init(context);
	}

	public Board(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public Board(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	public Game getGame() {
		return game;
	}
	
	public void setGame(Game g) {
		game = g;
		game.setListener(this);
		draw();
	}
	
	public int pit(final int i) {
		return game.pit(i);
	}
	
	public int store(final int i) {
		return game.store(i);
	}
	
	public void move(final int i) {
		new Thread(new Runnable()  {
			public void run() {
				try {
					game.move(i);
				} catch (IllegalMove e) {
					post(new Runnable() {
						public void run() {
							startAnimation(AnimationUtils.loadAnimation(anware, R.anim.shake));
						}
					});
				}
			}

		}).start();
	}
	
	public int getSpeed() {
		return Prefs.sleepInterval(getContext());
	}
	
	public void onScoop(final int id, int seeds) {
		Log.d(TAG, String.format("onScoop(%s, %s)", id, seeds));
		post(new Runnable() {
			public void run() {
				final int pitId = id + 1;
				findViewById(pitId).invalidate();				
			}
		});
	}

	public void onSow(final int id) {
		Log.d(TAG, String.format("onSow(%s)", id));
		post(new Runnable() {
			public void run() {
				final int pitId = id + 1;
				findViewById(pitId).invalidate();
			}
		});
	}

	public void onHarvest(final int id, final int who) {
		post(new Runnable() {
			public void run() {
				final int pitId = id + 1;
				final int storeId = (who + 1) * 1000;
				findViewById(pitId).invalidate();
				findViewById(storeId).invalidate();
			}
		});
	}
	
	public void onGameEnded() {
		post(new Runnable() {
			public void run() {
				anware.reset();
			}
		});
	}
	
	public void onNext() {
		updateContext();
	}
	
	public void onUndo() {
		Log.d(TAG, "onUndo");
		post(new Runnable() {
			public void run() {
				refresh();
			}

		});
	}

	public void onRedo() {
		Log.d(TAG, "onRedo");
		post(new Runnable() {
			public void run() {
				refresh();
			}
		});
	}
	
	private void refresh() {
		for (int pitId = 1; pitId < 13; ++pitId)
			findViewById(pitId).invalidate();
		for (int i = 1; i < 3; ++i) {
			final int storeId = i * 1000;
			findViewById(storeId).invalidate();
		}
		anware.update(game);
	}

	private void updateContext() {
		final Game g = game;
		updateContext(g);
	}

	private void updateContext(final Game g) {
		post(new Runnable() {
			public void run() {
				anware.update(g);
			}
		});
	}

	private void updateContext(final String info) {
		post(new Runnable() {
			public void run() {
				anware.update(info);
			}
		});
	}
	
	private void init(Context context) {
		Log.d(TAG, "init()");
		anware = (Anware) context;
		setGravity(Gravity.CENTER);
	}

    void draw() {
    	TView center = addCenter();
    	drawTopPits(center.getId());
    	drawBottomPits(center.getId());
    	drawStores();
    }
    
	RelativeLayout.LayoutParams cp() {
		final RelativeLayout.LayoutParams p = lp();
		p.addRule(RelativeLayout.CENTER_IN_PARENT);
		return p;
	}

	private void drawTopPits(final int cId) {
		addView(createPit(12, new int[][] {{above, cId}, {leftOf, 11}}));
		addView(createPit(11, new int[][] {{above, cId}, {leftOf, 10}}));
		addView(createPit(10, new int[][] {{above, cId}, {leftOf, cId}}));
		addView(createPit(9, new int[][] {{above, cId}, {rightOf, cId}}));
		addView(createPit(8, new int[][] {{above, cId}, {rightOf, 9}}));
		addView(createPit(7, new int[][] {{above, cId}, {rightOf, 8}}));
	}
	
	private void drawBottomPits(final int cId) {
		addView(createPit(1, new int[][] {{below, cId}, {leftOf, 2}}));
		addView(createPit(2, new int[][] {{below, cId}, {leftOf, 3}}));
		addView(createPit(3, new int[][] {{below, cId}, {leftOf, cId}}));
		addView(createPit(4, new int[][] {{below, cId}, {rightOf, cId}}));
		addView(createPit(5, new int[][] {{below, cId}, {rightOf, 4}}));
		addView(createPit(6, new int[][] {{below, cId}, {rightOf, 5}}));
	}

	private void drawStores() {
		addView(updatePit(createStore(1000), new int[][] {{rightOf, 6}}, cp()));
		addView(updatePit(createStore(2000), new int[][] {{leftOf, 12}}, cp()));
	}

	private TView addCenter() {
		TView center = new TView(getContext());
		center.setId(100);
		center.setLayoutParams(cp());
		addView(center);
		return center;
	}

	protected Pit createPit(final int id) {
		final Context context = getContext();
		final Pit pit = new Pit(context);
		pit.setId(id);
		return pit;
	}

	protected Pit createStore(final int id) {
		final Context context = getContext();
		final Pit pit = new Store(context);
		pit.setId(id);
		return pit;
	}
	
	Pit createPit(final int id, int[][] rules) {
		return updatePit(createPit(id), rules, lp());
	}

	Pit updatePit(final Pit pit, int[][] rules,
			final RelativeLayout.LayoutParams p) {
		for (int i = 0; i < rules.length; ++i)
			p.addRule(rules[i][0], rules[i][1]);
		pit.setLayoutParams(p);
		return pit;
	}

	private RelativeLayout.LayoutParams lp() {
		// float dpi = getResources().getDisplayMetrics().density;
		// return new RelativeLayout.LayoutParams((int)(Pit.W * dpi),
		// (int)(Pit.W * dpi));
		final int wc = LayoutParams.WRAP_CONTENT;
		return new RelativeLayout.LayoutParams(wc, wc);
	}
	
	private static class TView extends View {
		int width = 2;
		int height = 10;
		
		public TView(Context context) {
			super(context);
		}
		
		public TView(Context context, int w, int h) {
			super(context);
			width = w;
			height = h;
		}
		
		@Override
		public void onMeasure(int x, int y) {
			float dpi = getResources().getDisplayMetrics().density;
			setMeasuredDimension((int) (width * dpi), (int) (height * dpi));
		}

		@Override
		protected void onDraw(Canvas canvas) {
			paint.setColor(Color.DKGRAY);
			canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
		}

		private static Paint paint = new Paint();

	}
}

