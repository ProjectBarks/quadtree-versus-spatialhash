package org.dasd.ee.misc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;

public class ProgressReader extends FileReader {

	private ProgressBar progress;

	public ProgressReader(File file) throws FileNotFoundException {
		this("", file);
	}

	public ProgressReader(String prefix, File file) throws FileNotFoundException {
		super(file);
		progress = new ProgressBar(prefix, file.length());
	}

	@Override
	public int read() throws IOException {
		return updateProgress(super.read());
	}

	@Override
	public int read(char[] cbuf, int offset, int length) throws IOException {
		return updateProgress(super.read(cbuf, offset, length));
	}

	@Override
	public int read(char[] cbuf) throws IOException {
		return updateProgress(super.read(cbuf));
	}

	@Override
	public int read(CharBuffer target) throws IOException {
		return updateProgress(super.read(target));
	}

	@Override
	public long skip(long n) throws IOException {
		return updateProgress((int) super.skip(n));
	}

	private int updateProgress(int bytesRead) {
		if (bytesRead > 0) progress.increment(bytesRead);

		return bytesRead;
	}
}