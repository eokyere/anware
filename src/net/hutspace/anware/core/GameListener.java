package net.hutspace.anware.core;

public interface GameListener {
	void onScoop(final int i, int seeds);
	void onSow(final int x);
	void onHarvest(final int x, final int who);
	void onUndo();
	void onRedo();
	void onNext();
	void onGameEnded();
}
