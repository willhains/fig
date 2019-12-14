package com.willhains.fig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

final class RealFigDirectory implements FigDirectory
{
	static final RealFigDirectory WORKING = new RealFigDirectory(new File("."));
	
	private File _dir;
	
	RealFigDirectory(final File dir)
	{
		_dir = dir;
		if(!_dir.exists()) throw new IllegalArgumentException("'" + _dir + "' does not exist");
		if(!_dir.isDirectory()) throw new IllegalArgumentException("'" + _dir + "' is not a directory");
	}
	
	@Override
	public Iterable<FigFile> filesWithExtension(final String ext)
	{
		final List<FigFile> files = new ArrayList<>();
		for(final File file: _dir.listFiles((dir, filename) -> filename.endsWith(ext)))
		{
			files.add(new RealFigFile(file));
		}
		return files;
	}
	
	@Override
	public Optional<FigFile> findFile(final String envFigPath)
	{
		return _find(_dir, envFigPath, false).map(RealFigFile::new);
	}
	
	@Override
	public Optional<FigDirectory> findDir(final String dirPath)
	{
		return _find(_dir, dirPath, true).map(RealFigDirectory::new);
	}
	
	private Optional<File> _find(final File dir, final String name, final boolean directory)
	{
		for(final File file: dir.listFiles(($, filename) -> filename.equals(name)))
		{
			if(file.isDirectory() == directory) return Optional.of(file);
		}
		final String parentDir = dir.getParent();
		if(parentDir == null) return Optional.empty();
		return _find(new File(parentDir), name, directory);
	}
}
