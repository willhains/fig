package com.willhains.fig;

import java.util.function.Supplier;

/** Indicates that a config key was requested without an associated default value, but the config key was not found. */
public final class MandatoryKeyNotFoundException extends FigException
{
	/** @param key the config key that could not be found. */
	private MandatoryKeyNotFoundException(final String key)
	{
		super("Key \"" + key + "\" not found in config files.");
	}
	
	public static Supplier<MandatoryKeyNotFoundException> forKey(final String key)
	{
		return () -> new MandatoryKeyNotFoundException(key);
	}
}
