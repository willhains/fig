package com.willhains.fig;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class FigTest
{
	@Mock private FigFileSource _fileSource;
	@Mock private FigFile _envFig;
	@Mock private FigDirectory _figDir;
	
	@Before public void createMocks()
	{
		MockitoAnnotations.initMocks(this);
		when(_fileSource.file("./env.fig")).thenReturn(_envFig);
		when(_envFig.get(anyString())).thenReturn(Optional.empty());
		when(_fileSource.dir("./fig")).thenReturn(Optional.of(_figDir());
		when(_figDir.filesWithExt("fig")).thenReturn(emptyList());
	}
	
	@Before public void resetSystemProperties() { System.clearProperty("env.fig.path"); }
	
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
	public void shouldReadSingleExistingProperty()
	{
		final FigFile dbFig = mock(FigFile.class);
		when(dbFig.get("url")).thenReturn(Optional.of("jdbc:oracle:thin:@localhost/mydb1"));
		when(_figDir.filesWithExt("fig")).thenReturn(asList(dbFig));
		final Fig fig = new Fig(_fileSource);
		assertThat(fig._get("db.url").get(), is("jdbc:oracle:thin:@localhost/mydb1"));
	}
	
	@Test
	public void shouldReadSingleExistingPropertyWithPrefix()
	{
		final FigFile dbFig = mock(FigFile.class);
		when(dbFig.get("url")).thenReturn(Optional.empty());
		when(dbFig.get("db.url")).thenReturn(Optional.of("jdbc:oracle:thin:@localhost/mydb1"));
		when(_figDir.filesWithExt("fig")).thenReturn(asList(dbFig));
		final Fig fig = new Fig(_fileSource);
		assertThat(fig._get("db.url"), is("jdbc:oracle:thin:@localhost/mydb1"));
	}
	
	@Test
	public void shouldReadSingleMissingPropertyFromExistingFile()
	{
		
	}
	
	@Test
	public void shouldReadSinglePropertyFromMissingFile()
	{
		
	}
}