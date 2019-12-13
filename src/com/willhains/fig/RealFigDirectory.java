package com.willhains.fig;

import java.util.Optional;

final class RealFigDirectory implements FigDirectory
{
	static final RealFigDirectory WORKING = new RealFigDirectory(".");
	
	RealFigDirectory(final String path)
	{
		throw new UnsupportedOperationException("Not yet implemented.");
	}
	
	@Override
	public Iterable<FigFile> filesWithExtension(final String ext)
	{
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	@Override
	public Optional<FigFile> findFile(final String envFigPath)
	{
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	@Override
	public Optional<FigDirectory> findDir(final String dirPath)
	{
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
