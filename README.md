# Fig

Effortless config for your Java app.

[![Build Status][travis-status]][travis]

[travis-status]: https://travis-ci.org/willhains/fig.svg?branch=master
[travis]: https://travis-ci.org/willhains/fig

## Motivation

You are here because...

- You want to control your Java app via config files, but parsing files in start-up code is a pain.
- You need to configure your app for multiple environments, deployment sites, or whatever; and you don't want those details to clutter up your app's business logic.
- You've tried dependency injection frameworks for these things, but it was too heavy-handed for simple config properties.
- You have a large number of config properties, and you want to keep them organised.
- You need non-Latin characters in config values, but `Properties` can't do UTF-8 encoding.
- You have been bitten by the evils of [Stringly-typed][stringly] code one too many times, so you want your
 configurable values to be type-safe.

[stringly]: http://wiki.c2.com/?StringlyTyped

## Read Config Values

Getting config values couldn't be easier. Since your config files are read at class load time, they are instantly available anywhere in your code. For example, you can declare them as constants:

```java
static final String DB_URL = Fig.str("db.url");
```

If the specified config key is missing, startup will fail with a helpful error printed to the console. Or you can supply a default value, which is used when the config key is not found.

```java
static final int DB_PORT = Fig.num("db.port", 8324);
static final boolean DB_LONG_VALS = Fig.bool("db.long-values", true);
```

Aside from reading raw `String`s, Fig automatically converts `int` and `boolean` config values, and there's also a way to read any other type you want, by passing a constructor/factory method reference.

```java
static final UserID DB_USER = Fig.obj("db.user-id", UserID::new);
static final TimeUnit DB_TIMES = Fig.obj("db.time.precision", TimeUnit::valueOf, MILLISECOND);
```

You can use Fig to get other kinds of files from your config directory as well.

```java
static final File SCHEMA = Fig.file("schema.xml");
static final File LOGO = Fig.file("logo.png");
```

Fig can read a list of values, delimited by commas (default), or a custom delimiter.

```java
static final List<String> CLUSTER_HOSTS = Fig.list("db.cluster.hosts");
static final List<HostName> CLUSTER_HOSTNAMES = Fig.list("db.cluster.hosts", HostName::new);
static final List<UserID> BANNED_USERS = Fig.list("banned.user-ids", UserID::new, " ");
```

You can also read multiple config values as an immutable `Map`, using a *wildcard key*. The wildcard key must have exactly one wildcard character (`*`), the matches of which will be the keys of the returned map. If there are no matches, Fig returns an empty map.

```java
static final Map<String, String> NODE_NAMES = Fig.map("node.*.name");
static final Map<String, HostName> NODE_HOSTS = Fig.map("node.*.hostname", HostName::new);
```

<!-- TODO: See ["Using Fig"](docs/Using-Fig.md) for more information. -->

## Config Files

The first part of a config key is the filename of the config file (without the default file extension `.fig`). So, in the examples above, all the keys that start with `db.` come from a file named `db.fig`, located by default in a subdirectory `fig` under the working directory.

Inside the `fig/db.fig` file, the `db.` prefix can (optionally) be dropped. For example, `db.url=` can be written as just `url=`. One key with just the name of the file is allowed, for example, `db.fig` may contain `db=`.

```properties
# DB config file ./fig/db.fig
url = jdbc:oracle:thin:@localhost/mydb1
port = 1521
long-values = false
user-id = myapp
time.precision = MICROSECOND
```

Config file encoding is always UTF-8.

<!-- TODO: See ["Fig Files"](docs/Fig-Files.md) for more information. -->

<!-- TODO: ## Testing Classes that Use Fig

Since config is an input to your code, you'll want to control it easily from unit tests. Use `MockFig` to clear and set config values for your tests. The changes you make affect only the current thread, so multi-threaded parallel testing is supported.

```java
@Before public void initFig()
{
	MockFig.init(); // Clears all config values
	MockFig.set("flamingo.max-squawks", 1);
}
```

See ["Testing with Fig"](docs/Testing-with-Fig.md) for more information. -->

## Configuring Fig

By default, Fig will search for config files in a `fig` subdirectory of the current working directory. If it doesn't find a `fig` subdirectory there, it will search parent directories until it finds one.

This, and all of Fig's defaults, can be customised by placing an `env.fig` file in the current working directory or one of its parent directories. 

| `env.fig`     | Env Var   | Default | Purpose |
|---------------|-----------|---------|---------|
| `env`         | `FIG_ENV` | `dev`   | The name of the environment (see below), e.g. `qa`, `uat`, `prod`. |
| `env.fig-dir` | `FIG_DIR` | `fig`   | The name of the config directory, e.g. `conf`, `cfg`. |
| `env.fig-ext` | `FIX_EXT` | `fig`   | The filename extension of config files, e.g. `cfg`, `conf`, `properties`. |

<!-- TODO: See ["Customising Fig"](docs/Customising-Fig.md) for more information. -->

## Overriding Config

Your app's config values may need to differ from one environment to another. For example, a database connection URL , a thread pool size, or a debug mode setting. Fig makes it easy. Just add a sub-directory inside your `fig ` directory, with the name of the environment, containing `*.fig` files of overriding keys. Then, in that environment , set `env` in `env.fig`, or the `FIG_ENV` environment variable, to the name of your sub-directory.

```
./                          # current working directory
./fig/db.fig                # default DB settings, e.g. connect to Dev DB
./fig/processor.fig         # default settings for worker thread pool, etc.
./fig/uat/db.fig            # UAT database settings
./fig/prod/db.fig           # Prod database settings
./fig/prod/processor.fig    # Prod worker thread pool settings
```

In this example, config values in `./fig/prod/db.fig` will override properties of the same key in `./fig/db.fig`. When a key in `./fig/db.fig` is not found in `./fig/prod/db.fig`, then the value from `./fig/db.fig` will be used. And in the UAT environment, the default `./fig/processor.fig` settings will be used, while the production environment has its own specific settings in `./fig/prod/processor.fig`.

Config values can also be overridden by system properties (`-D` on the command line), and this will always take the highest precedence.

<!-- TODO: See ["Environment Structure"](docs/Environment-Structure.md) for more information. -->

## Development Status

Fig is currently is in an early development stage, but is based on a design that is already used in mission-critical systems of a large financial institution. (No guarantees of safety or quality are made or implied. Use at your own risk.) Comments and contributions are welcome and encouraged. Public APIs are unlikely to change, but may do so without notice.

## Contribution

1. Fork
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create new Pull Request
