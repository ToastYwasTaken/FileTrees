package pgdp.filetree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Directory extends File {
	Directory dir = this;
	private final List<File> files;
	private int counter;
	private boolean visited = false;

	public Directory(Path path, List<File> files) {
		super(path);
		this.files = files;
	}

	@Override
	public Iterator<File> iterator() {
		return new Iterator<File>() {
			List<File> recursiveList = recursive(new LinkedList<>(List.of(dir)));	//list of directories recursively accessed
			@Override
			public boolean hasNext() {
				return !recursiveList.isEmpty();	//doesn't have next if recursiveList is empty (next() deletes all elements after returning
			}

			@Override
			public File next() {
				if (hasNext()) {
					return recursiveList.remove(0);	// return current; remove current; next time return next
				}else{
					throw new NoSuchElementException("Fuck my Life if this doesn't work :)");
				}
			}
		};
	}

	public List<File> recursive(List<File> newlist){	//recursive method
		for (File file : files) {
			if (file.isRegularFile()) {	//is regFile
				newlist.add(file);	//add to newlist that finally contains all Files
			} else {	//is dir
				newlist.add(file);
				newlist = ((Directory)file).recursive(newlist); 	//searches until no more directories found
			}
		}
		return newlist;
	}

	@Override
	public int getHeight() {
		return recursiveTwo(new LinkedList<>(List.of(dir)));
	}

	public int recursiveTwo(List<File> newlist){
		for (File file : files) {
			if(!visited) {
				if (!file.isRegularFile()) {    //isDir
					counter++;
					visited = true;
					counter += ((Directory) file).recursiveTwo(newlist);
				}
			}
		}
		return counter;
	}

	@Override
	public boolean isRegularFile() {
		return false;
	}

	public List<File> getFiles() {
		return files;
	}
}
