# ProxyFox Debug

A Quilt mod targeting Kotlin and the JVM,
adding features useful for debugging applications in production.

## Features
- Kotlin `Result` is modified to print exceptions across suspend fun calls
- Adds a utility for sanitizing strings of sensitive things (Tokens, IPs, etc)
  - Custom entrypoint to register these `proxyfox-debug`
- Replaces STDOUT/STDERR with a proxy that applies string sanitizations
