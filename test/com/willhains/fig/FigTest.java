package com.willhains.fig;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.function.Function;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FigTest
{
	@Mock private FigFileSource _fileSource;
	
	@Before public void createMocks() { MockitoAnnotations.initMocks(this); }
	
	@Test
	public void shouldReadAbsoluteEnvFigPathFromSystemProperty()
	{
		System.setProperty("env.fig.path", "~/env.conf");
		final Fig fig = new Fig(_fileSource);
		verify(_fileSource.file("~/env.conf"));
	}
	
	@Test
	public void shouldReadRelativeEnvFigPathFromSystemProperty()
	{
		System.setProperty("env.fig.path", ".env/env.fig");
		when(_fileSource.file("./.env/env.fig")).thenReturn(Optional.empty());
		final Fig fig = new Fig(_fileSource);
		verify(_fileSource.file("../.env/env.fig"));
	}
	
	@Test
	public void shouldDefaultFigExt()
	{
		System.setProperty("env.fig.path", "env.fig");
		final FigFile envFig = mock(FigFile.class);
		when(_fileSource.file("./env.fig")).thenReturn(Optional.of(envFig));
	}
}