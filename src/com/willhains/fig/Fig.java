package com.willhains.fig;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Read values from config files.
 *
 * @author willhains
 */
public interface Fig
{
	FigData _REAL = RealFigDirectory.WORKING.load();
	
	/**
	 * @param key the config key, including a prefix from the name of the config file that stores it.
	 * @return the trimmed, raw string value for the specified key.
	 * @throws MandatoryKeyNotFoundException if the specified key could not be found in config files.
	 */
	static String str(final String key) throws MandatoryKeyNotFoundException
	{
		return _REAL._str(key);
	}
	
	/**
	 * @param key the config key, including a prefix from the name of the config file that stores it.
	 * @param defaultValue a default value to be used in the case {@code key} is not found in the config files.
	 * @return the trimmed, raw string value for the specified key, or {@code defaultValue}.
	 * @throws NullPointerException if the specified default value is null.
	 */
	static String str(final String key, final String defaultValue)
	{
		return _REAL._str(key, defaultValue);
	}
	
	/**
	 * @param key the config key, including a prefix from the name of the config file that stores it.
	 * @return an {@code int} value parsed from the specified config key.
	 * @throws MandatoryKeyNotFoundException if the specified key could not be found in config files.
	 * @throws NumberFormatException if the value found in config files cannot be converted to an integer.
	 */
	static int num(final String key) throws MandatoryKeyNotFoundException
	{
		return _REAL._num(key);
	}
	
	/**
	 * @param key the config key, including a prefix from the name of the config file that stores it.
	 * @param defaultValue a default value to be used in the case {@code key} is not found in the config files.
	 * @return an {@code int} value parsed from the specified config key.
	 * @throws NumberFormatException if the value found in config files cannot be converted to an integer.
	 */
	static int num(final String key, final int defaultValue)
	{
		return _REAL._num(key, defaultValue);
	}
	
	/**
	 * @param key the config key, including a prefix from the name of the config file that stores it.
	 * @return a {@code boolean} value parsed from the specified config key, allowing {@code true}/{@code false} and
	 *         {@code yes}/{@code no}.
	 * @throws BooleanFormatException if the value found in config files cannot be converted to a boolean.
	 */
	static boolean bool(final String key) throws MandatoryKeyNotFoundException
	{
		return _REAL._bool(key);
	}
	
	/**
	 * @param key the config key, including a prefix from the name of the config file that stores it.
	 * @param defaultValue a default value to be used in the case {@code key} is not found in the config files.
	 * @return a {@code boolean} value parsed from the specified config key, allowing {@code true}/{@code false} and
	 *         {@code yes}/{@code no}.
	 * @throws BooleanFormatException if the value found in config files cannot be converted to a boolean.
	 */
	static boolean bool(final String key, final boolean defaultValue)
	{
		return _REAL._bool(key, defaultValue);
	}
	
	/**
	 * @param key the config key, including a prefix from the name of the config file that stores it.
	 * @param factory a method reference to either a constructor or a factory, that takes a single {@link String}
	 *                argument, and returns an object of the specified type.
	 * @param <Target> any object type that can be constructed from a {@link String} value.
	 * @return an object built from the string value found at the specified config key, using {@code factory}.
	 */
	static <Target> Target obj(final String key, final Function<String, Target> factory) throws MandatoryKeyNotFoundException
	{
		return _REAL._obj(key, factory);
	}
	
	/**
	 * @param key the config key, including a prefix from the name of the config file that stores it.
	 * @param factory a method reference to either a constructor or a factory, that takes a single {@link String}
	 *                argument, and returns an object of the specified type.
	 * @param defaultValue a default value to be used in the case {@code key} is not found in the config files.
	 * @param <Target> any object type that can be constructed from a {@link String} value.
	 * @return an object built from the string value found at the specified config key, using {@code factory}.
	 */
	static <Target> Target obj(final String key, final Function<String, Target> factory, final Target defaultValue)
	{
		return _REAL._obj(key, factory, defaultValue);
	}
	
	// TODO: Javadoc.
	static Iterable<String> list(final String key)
	{
		return _REAL._list(key);
	}
	
	// TODO: Javadoc.
	static Iterable<String> list(final String key, final String delim)
	{
		return _REAL._list(key);
	}
	
	// TODO: Javadoc.
	static <Target> Iterable<Target> list(final String key, final Function<String, Target> factory)
	{
		return _REAL._list(key, factory);
	}
	
	// TODO: Javadoc.
	static <Target> Iterable<Target> list(final String key, final Function<String, Target> factory, final String delim)
	{
		return _REAL._list(key, factory, delim);
	}
	
	/**
	 * @param wildcardKey a pattern to match config keys, containing exactly one wildcard character ({@code *}).
	 * @return a {@link Map} containing all matching config values, indexed by the substrings the wildcard character
	 *         matched.
	 */
	static Map<String, String> map(final String wildcardKey)
	{
		return _REAL._map(wildcardKey);
	}
	
	/**
	 * @param wildcardKey a pattern to match config keys, containing exactly one wildcard character ({@code *}).
	 * @param factory a method reference to either a constructor or a factory, that takes a single {@link String}
	 *                argument, and returns an object of the specified type.
	 * @param <Target> any object type that can be constructed from a {@link String} value.
	 * @return a {@link Map} containing all matching config values, indexed by the substrings the wildcard character
	 *         matched.
	 */
	static <Target> Map<String, Target> map(final String wildcardKey, final Function<String, Target> factory)
	{
		return _REAL._map(wildcardKey, factory);
	}
}
