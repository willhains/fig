package com.willhains.fig;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

interface FigDirectory
{
	Iterable<FigFile> filesWithExtension(String ext);
	Optional<FigFile> findFile(String filePath);
	Optional<FigDirectory> findDir(String dirPath);
	
	default FigData load()
	{
		// Load env.fig and set default values for missing keys
		final Optional<FigFile> envFig = findFile("env.fig");
		final Map<String, String> config = new HashMap<>();
		envFig.map(FigFile::read).ifPresent(config::putAll);
		final String env = config.computeIfAbsent("env", $ -> "dev");
		final String figDirPath = config.computeIfAbsent("env.fig-dir", $ -> "fig");
		final String figExt = config.computeIfAbsent("env.fig-ext", $ -> "fig");
		
		// Find fig directory
		findDir(figDirPath).ifPresent(figDir ->
		{
			// Load .fig files in fig directory
			figDir.filesWithExtension(figExt).forEach(file -> config.putAll(file.read()));
			
			// Find directory for environment (specified by `env` in env.fig)
			figDir.findDir(env).ifPresent(envFigDir ->
			{
				// Override config with environment-specific values
				envFigDir.filesWithExtension(figExt).forEach(file -> config.putAll(file.read()));
			});
		});
		
		// Finalise configuration
		return new FigData(config);
	}
}
