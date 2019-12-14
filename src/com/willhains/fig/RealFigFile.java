package com.willhains.fig;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

final class RealFigFile implements FigFile
{
	private final File _file;
	private final String _prefix;
	
	RealFigFile(final File file)
	{
		_file = file;
		if(!_file.exists()) throw new IllegalArgumentException("'" + _file + "' does not exist");
		if(!_file.isFile()) throw new IllegalArgumentException("'" + file + "' is not a file");
		// TODO: Assert filename ends with .fig extension (configurable?), and has something before the extension.
		final String filename = _file.getName();
		_prefix = filename.substring(0, filename.indexOf(".fig"));
	}
	
	@Override public Map<String, String> read()
	{
		final Map<String, String> values = new HashMap<>();
		try(final BufferedReader reader = new BufferedReader(new FileReader(_file)))
		{
			reader.lines()
				.map(String::trim) // ignore extraneous whitespace
				.filter(line -> !line.startsWith("#")) // ignore comments
				.filter(line -> !line.isEmpty()) // ignore empty lines
				.forEach(line ->
				{
					// Split into key and value
					final int equalsIndex = line.indexOf('=');
					final String key = line.substring(0, equalsIndex).trim();
					final String value = line.substring(equalsIndex + 1).trim();
					
					// Add to values with prefix
					if(key.equals(_prefix)) values.put(key, value);
					else values.put(_prefix + "." + key, value);
				});
		}
		catch(final IOException e)
		{
			e.printStackTrace(); // TODO
		}
		return values;
	}
}
