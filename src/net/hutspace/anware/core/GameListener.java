package net.hutspace.anware.core;

public interface GameListener {

	void scoop(final int i, int seeds);
	void sow(final int x);
	void harvest(final int x, final int who);
}
