package com.willhains.fig;

/** Indicates that a config key was requested without an associated default value, but the config key was not found. */
public final class MandatoryKeyNotFoundException extends FigException
{
	/** @param key the config key that could not be found. */
	public MandatoryKeyNotFoundException(final String key)
	{
		super("Key \"" + key + "\" not found in config files.");
	}
}
