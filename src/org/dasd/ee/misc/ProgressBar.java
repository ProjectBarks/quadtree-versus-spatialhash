package org.dasd.ee.misc;

public class ProgressBar {
	private long total, count, lastPercent;
	private String format;

	public ProgressBar(long total) {
		this("", total);
	}

	public ProgressBar(String prefix, long total) {
		this.format = "\r" + prefix + " %3d%% [%s]";
		this.total = total;
		this.count = 0;
		this.lastPercent = 0;
	}

	public void update(long done) {
		if (isFinished()) return;
		StringBuilder progress = new StringBuilder();
		this.count = done;

		long percent = (++done * 100) / total, filled = percent, unfilled = 100 - percent;

		if (lastPercent != percent) {
			lastPercent = percent;
			while (filled-- > 0) progress.append('#');
			while (unfilled-- > 0) progress.append('-');

			System.out.printf(this.format, percent, progress);
		}

		if (isFinished()) System.out.println("");
    }

	public void increment(long increment) {
		update(increment + count);
	}

	public void tick() {
		increment(1);
	}

	public boolean isFinished() {
		return this.lastPercent == 100;
	}
}