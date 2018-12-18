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

## Development Status

Fig is currently is in an early development stage, but is based on a design that is already used in mission-critical systems of a large financial institution. (No guarantees of safety or quality are made or implied. Use at your own risk.) Comments and contributions are welcome and encouraged. Public APIs are unlikely to change, but may do so without notice.

## Contribution

1. Fork
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create new Pull Request
