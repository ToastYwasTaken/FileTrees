package pgdp.filetree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RegularFile extends File {
	private RegularFile regf = this;

	public RegularFile(Path path) {
		super(path);
	}

	@Override
	public Iterator<File> iterator() {
		return new Iterator<File>() {
			private boolean wasUsed;
			@Override
			public boolean hasNext() {	// returns false if current element was used
				return !wasUsed;
			}

			@Override
			public File next() {
				if (hasNext()) {
					wasUsed = true;
					return regf;	//returns current regf
				} else {
					throw new NoSuchElementException("Iterator is empty.");
				}
			}
		};
	}

	@Override
	public int getHeight() {
		return 0;
	}

	@Override
	public boolean isRegularFile() {
		return true;
	}

}
