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
public final class Fig
{
	private static final Fig _FIG = new Fig(new RealFigFileSource());
	
	private final Map<String, String> _config;
	
	Fig(final FigFileSource fileSource)
	{
		throw new UnsupportedOperationException("Not yet implemented.");
	}
	
	Optional<String> _opt(final String key)
	{
		return Optional.ofNullable(_config.get(key));
	}
	
	Map<String, String> _map(final String wildcardKey)
	{
		throw new UnsupportedOperationException("Not yet implemented.");
	}
	
	/**
	 * @param key the config key, including a prefix from the name of the config file that stores it.
	 * @return the trimmed, raw string value for the specified key.
	 * @throws MandatoryKeyNotFoundException if the specified key could not be found in config files.
	 */
	public static String str(final String key)
	{
		return opt(key).orElseThrow(() -> new MandatoryKeyNotFoundException(key));
	}
	
	/**
	 * @param key the config key, including a prefix from the name of the config file that stores it.
	 * @param defaultValue a default value to be used in the case {@code key} is not found in the config files.
	 * @return the trimmed, raw string value for the specified key, or {@code defaultValue}.
	 * @throws NullPointerException if the specified default value is null.
	 */
	public static String str(final String key, final String defaultValue)
	{
		return opt(key).orElse(defaultValue);
	}
	
	/**
	 * @param key the config key, including a prefix from the name of the config file that stores it.
	 * @return an {@code int} value parsed from the specified config key.
	 * @throws MandatoryKeyNotFoundException if the specified key could not be found in config files.
	 * @throws NumberFormatException if the value found in config files cannot be converted to an integer.
	 */
	public static int num(final String key)
	{
		return Integer.parseInt(str(key));
	}
	
	/**
	 * @param key the config key, including a prefix from the name of the config file that stores it.
	 * @param defaultValue a default value to be used in the case {@code key} is not found in the config files.
	 * @return an {@code int} value parsed from the specified config key.
	 * @throws NumberFormatException if the value found in config files cannot be converted to an integer.
	 */
	public static int num(final String key, final int defaultValue)
	{
		return opt(key).map(Integer::parseInt).orElse(defaultValue);
	}
	
	// true = "true", "yes"
	// false = "false", "no"
	// case-insensitive
	private static boolean _parseBoolean(final String fromString)
	{
		throw new UnsupportedOperationException("Not yet implemented.");
	}
	
	/**
	 * @param key the config key, including a prefix from the name of the config file that stores it.
	 * @return a {@code boolean} value parsed from the specified config key, allowing {@code true}/{@code false} and
	 *         {@code yes}/{@code no}.
	 * @throws BooleanFormatException if the value found in config files cannot be converted to a boolean.
	 */
	public static boolean bool(final String key)
	{
		return _parseBoolean(str(key));
	}
	
	/**
	 * @param key the config key, including a prefix from the name of the config file that stores it.
	 * @param defaultValue a default value to be used in the case {@code key} is not found in the config files.
	 * @return a {@code boolean} value parsed from the specified config key, allowing {@code true}/{@code false} and
	 *         {@code yes}/{@code no}.
	 * @throws BooleanFormatException if the value found in config files cannot be converted to a boolean.
	 */
	public static boolean bool(final String key, final boolean defaultValue)
	{
		return opt(key).map(Fig::_parseBoolean).orElse(defaultValue);
	}
	
	/**
	 * @param key the config key, including a prefix from the name of the config file that stores it.
	 * @param factory a method reference to either a constructor or a factory, that takes a single {@link String}
	 *                argument, and returns an object of the specified type.
	 * @param <Target> any object type that can be constructed from a {@link String} value.
	 * @return an object built from the string value found at the specified config key, using {@code factory}.
	 */
	public static <Target> Target obj(final String key, final Function<String, Target> factory)
	{
		return factory.apply(str(key));
	}
	
	/**
	 * @param key the config key, including a prefix from the name of the config file that stores it.
	 * @param defaultValue a default value to be used in the case {@code key} is not found in the config files.
	 * @param factory a method reference to either a constructor or a factory, that takes a single {@link String}
	 *                argument, and returns an object of the specified type.
	 * @param <Target> any object type that can be constructed from a {@link String} value.
	 * @return an object built from the string value found at the specified config key, using {@code factory}.
	 */
	public static <Target> Target obj(
		final String key,
		final Target defaultValue,
	    final Function<String, Target> factory)
	{
		return opt(key).map(factory).orElse(defaultValue);
	}
	
	/**
	 * @param key the config key, including a prefix from the name of the config file that stores it.
	 * @return an {@link Optional} containing the value found in config files, or {@link Optional#empty()} if not found.
	 */
	public static Optional<String> opt(final String key)
	{
		return _FIG._opt(key);
	}
	
	/**
	 * @param key the config key, including a prefix from the name of the config file that stores it.
	 * @param factory a method reference to either a constructor or a factory, that takes a single {@link String}
	 *                argument, and returns an object of the specified type.
	 * @param <Target> any object type that can be constructed from a {@link String} value.
	 * @return an {@link Optional} containing the value found in config files, or {@link Optional#empty()} if not found.
	 */
	public static <Target> Optional<Target> opt(final String key, final Function<String, Target> factory)
	{
		return opt(key).map(factory);
	}
	
	/**
	 * @param wildcardKey a pattern to match config keys, containing exactly one wildcard character ({@code *}).
	 * @return a {@link Map} containing all matching config values, indexed by the substrings the wildcard character
	 *         matched.
	 */
	public static Map<String, String> map(final String wildcardKey)
	{
		return _FIG._map(wildcardKey);
	}
	
	/**
	 * @param wildcardKey a pattern to match config keys, containing exactly one wildcard character ({@code *}).
	 * @param factory a method reference to either a constructor or a factory, that takes a single {@link String}
	 *                argument, and returns an object of the specified type.
	 * @param <Target> any object type that can be constructed from a {@link String} value.
	 * @return a {@link Map} containing all matching config values, indexed by the substrings the wildcard character
	 *         matched.
	 */
	public static <Target> Map<String, Target> map(final String wildcardKey, final Function<String, Target> factory)
	{
		final Map<String, String> stringMap = map(wildcardKey);
		final Map<String, Target> targetMap = new HashMap<>(stringMap.size());
		stringMap.forEach((key, value) -> targetMap.put(key, factory.apply(value)));
		return targetMap;
	}
	
	private static final class RealFigFileSource implements FigFileSource
	{
		@Override public Optional<FigDirectory> dir(String atPath) { return Optional.of(new RealFigDirectory(atPath)); }
		@Override public Optional<FigFile> file(String atPath) { return Optional.of(new RealFigFile(atPath)); }
	}

	private static final class RealFigDirectory implements FigDirectory
	{
		RealFigDirectory(final String path)
		{
			throw new UnsupportedOperationException("Not yet implemented.");
		}
	}
	
	private static final class RealFigFile implements FigFile
	{
		RealFigFile(final String path)
		{
			throw new UnsupportedOperationException("Not yet implemented.");
		}
	}
}

interface FigFileSource
{
	Optional<FigDirectory> dir(final String atPath);
	Optional<FigFile> file(final String atPath);
}

interface FigDirectory
{
	
}

interface FigFile
{
	
}
