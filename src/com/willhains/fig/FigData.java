package com.willhains.fig;

import java.lang.annotation.Target;
import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

final class FigData
{
	private final Map<String, String> _data;
	
	FigData(final Map<String, String> data)
	{
		_data = Map.copyOf(data);
	}
	
	Optional<String> _get(final String key)
	{
		return Optional.ofNullable(_data.get(key));
	}
	
	String _str(final String key) throws MandatoryKeyNotFoundException
	{
		return _get(key).orElseThrow(MandatoryKeyNotFoundException.forKey(key));
	}
	
	String _str(final String key, final String defaultValue)
	{
		return _get(key).orElse(defaultValue);
	}
	
	int _num(final String key) throws MandatoryKeyNotFoundException
	{
		return _get(key).map(Integer::parseInt).orElseThrow(MandatoryKeyNotFoundException.forKey(key));
	}
	
	int _num(final String key, final int defaultValue)
	{
		return _get(key).map(Integer::parseInt).orElse(defaultValue);
	}
	
	boolean _bool(final String key) throws MandatoryKeyNotFoundException
	{
		return _get(key).map(_parseBoolean(key)).orElseThrow(MandatoryKeyNotFoundException.forKey(key));
	}
	
	boolean _bool(final String key, final boolean defaultValue)
	{
		return _get(key).map(_parseBoolean(key)).orElse(defaultValue);
	}
	
	<Target> Target _obj(final String key, final Function<String, Target> factory) throws MandatoryKeyNotFoundException
	{
		return _get(key).map(factory).orElseThrow(MandatoryKeyNotFoundException.forKey(key));
	}
	
	<Target> Target _obj(final String key, final Function<String, Target> factory, final Target defaultValue)
	{
		return _get(key).map(factory).orElse(defaultValue);
	}
	
	private static final String _DEFAULT_LIST_DELIM = ",";
	
	Iterable<String> _list(final String key)
	{
		return _list(key, _DEFAULT_LIST_DELIM);
	}
	
	Iterable<String> _list(final String key, final String delim)
	{
		return List.of(_get(key).orElse("").split(delim));
	}
	
	<Target> Iterable<Target> _list(final String key, final Function<String, Target> factory)
	{
		return _list(key, factory, _DEFAULT_LIST_DELIM);
	}
	
	<Target> Iterable<Target> _list(final String key, final Function<String, Target> factory, final String delim)
	{
		return _get(key).stream().flatMap($ -> Arrays.stream($.split(delim))).map(factory).collect(toList());
	}
	
	Map<String, String> _map(final String wildcardKey)
	{
		return _map(wildcardKey, Function.identity());
	}
	
	<Target> Map<String, Target> _map(final String wildcardKey, final Function<String, Target> factory)
	{
		// Parse wildcard key
		final String[] tokens = wildcardKey.split("\\*");
		if(tokens.length < 2) throw new IllegalArgumentException("Missing wildcard character in key: " + wildcardKey);
		if(tokens.length > 2) throw new IllegalArgumentException("Multiple wildcard characters in key: " + wildcardKey);
		final String prefix = tokens[0];
		final String suffix = tokens[1];
		
		// Match config keys to wildcard pattern
		final Map<String, Target> map = new HashMap<>();
		_data.forEach((key, value) ->
		{
			if(key.startsWith(prefix) && key.endsWith(suffix))
			{
				final int suffixIndex = key.indexOf(suffix);
				final String wildcardMatch = key.substring(prefix.length(), suffixIndex);
				map.put(wildcardMatch, factory.apply(value));
			}
		});
		return Collections.unmodifiableMap(map);
	}
	
	// true = "true", "yes"
	// false = "false", "no"
	// case-insensitive
	private static Function<String, Boolean> _parseBoolean(final String key)
	{
		return value ->
		{
			if(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes")) return true;
			if(value.equalsIgnoreCase("false") || value.equalsIgnoreCase("no")) return false;
			throw new BooleanFormatException(key, value);
		};
	}
}
