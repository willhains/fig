package com.willhains.fig;

/** Indicates that Fig was unable to retrieve a config value. */
public abstract class FigException extends RuntimeException
{
	/** @param message a helpful message explaining why Fig was unable to retrieve a config value. */
	protected FigException(final String message) { super(message); }
}
