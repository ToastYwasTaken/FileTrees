package pgdp.filetree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtil {

	public static File toFileRepresentation(Path path) throws IOException{
		if(Files.isRegularFile(path)){	//is reg
			return new RegularFile(path);
		}
		else if(Files.isDirectory(path)){	//is dir
			List<File> newFileList = new LinkedList<>();	//assigning new list
			List<Path> newPathList = Files	// collecting all paths in a list
					.list(path)
					.collect(Collectors
							.toList());
			for(int i = 0; i < Files.list(path).count() ;i++){
				newFileList.add(toFileRepresentation(newPathList.get(i)));	//add to empty list
			}
			return new Directory(path, newFileList);	//returns new dir
		}
		return null;
	}

	public static void main(String[] args) throws IOException{
		//System.out.print(toFileRepresentation(Path.of("C:\\Users\\surfa\\Documents\\pgdp1920w12h01-ge38was")));
		Directory newdir = (Directory)toFileRepresentation((Path.of("C:\\TUM")));
		Iterator<File> newiter = newdir.iterator();
		while(newiter.hasNext()){
			System.out.println(newiter.next());
		}
		System.out.println(newdir.getHeight());


	}
}
