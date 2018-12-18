# Fig [![Build Status][travis-status]][travis]
[travis-status]: https://travis-ci.org/willhains/fig.svg?branch=master
[travis]: https://travis-ci.org/willhains/fig

Effortless config for your Java app.

## Motivation

You are here because...

- You want to control your Java app via config files, but dealing with the `Properties` API is a pain.
- You need to configure your app for multiple environments, deployment sites, or whatever; and you don't want those details to clutter up your app's business logic.
- You've tried dependency injection frameworks for these things, but it was too heavy-handed for simple config properties.
- You have a large number of config properties, and you want to keep them organised.

## Read Config Values

Getting config property values couldn't be easier. Since your config files are read at class load time, they are instantly available anywhere in your code; the preferred way is to declare them as constants:

```java
private static final String DB_URL = Fig.str("db.url");
```

You can supply a default value, which is used when the config property key is not found. (If you don't, and the key is missing, startup will fail with a helpful error printed to the console.)

```java
private static final int DB_PORT = Fig.num("db.port", 8324);
private static final boolean DB_LONG_VALS = Fig.bool("db.long-values", true);
```

Aside from reading raw `String`s, Fig automatically converts `int` and `boolean` config values, and there's also a way to read any other type you want, by passing a constructor/factory method reference.

```java
private static final UserID DB_USER = Fig.obj("db.user-id", UserID::new);
private static final TimeUnit DB_TIMES =
	Fig.obj("db.time.precision", TimeUnit::valueOf, TimeUnit.MILLISECOND);
```

## Config Files

The first part of a config property key is the filename of the config file (without the default file extension `.fig`). So, in the examples above, all the keys that start with `db.` come from a file named `db.fig`.

Inside the `db.fig` file, the `db.` prefix can be dropped. For example, `db.url` can be written as just `url=`.

```properties
# DB config file ./fig/db.fig
url = jdbc:oracle:thin:@localhost/mydb1
port = 1521
long-values = false
user-id = myapp
time.precision = MICROSECOND
```

## Configuring Fig

By default, Fig will search for config files in a `fig` subdirectory of the current working directory. If it doesn't find a `fig` subdirectory there, it will search parent directories until it finds one.

This, and all of Fig's defaults can be customised by placing an `env.fig` file in the current working directory or one of its parent directories.

| Key             | Default | Purpose |
|-----------------|---------|---------|
| `env.id`        | `dev`   | The name of the environment, e.g. `qa`, `uat`, `prod`. |
| `env.fig-dir`   | `fig`   | The relative or absolute path to the config files directory, e.g. `conf`, `cfg`, `.`, `~/config`. |
| `env.fig-ext`   | `fig`   | The filename extension of config files, e.g. `cfg`, `conf`, `properties`, `txt`. |
| `env.structure` | `id`    | Defines the overriding hierarchy structure of the config directory (see below), e.g. `id/site`, `id/site/dc`. |

## Development Status

Fig is currently is in an early development stage, but is based on a design that is already used in mission-critical systems of a large financial institution. (No guarantees of safety or quality are made or implied. Use at your own risk.) Comments and contributions are welcome and encouraged. Public APIs are unlikely to change, but may do so without notice.

## Contribution

1. Fork
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create new Pull Request
