package com.willhains.fig;

public final class BooleanFormatException extends FigException
{
	public BooleanFormatException(final String key, final String valueFromConfigFile)
	{
		super("Could not convert key \"" + key + "\" with value \"" + valueFromConfigFile + "\" into a boolean");
	}
}
