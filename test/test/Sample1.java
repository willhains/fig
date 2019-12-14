package test;

import com.willhains.fig.Fig;

public class Sample1
{
	private static final String _FOO_BAR = Fig.str("foo.bar", "baz");
	
	public static void main(String[] args)
	{
		System.out.println("env=" + Fig.str("env"));
		System.out.println("fig-dir=" + Fig.str("env.fig-dir"));
		System.out.println("foo.bar=" + _FOO_BAR);
	}
}
