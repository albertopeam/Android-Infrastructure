# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [0.0.4-alpha] - 2017-08-20
### Added
- HandledException for recover Exception
- NotHandledException to crash the client if any Exception is not
handled
- CollisionException to crash the client if more than one
ExceptionDelegate handles a Exception
- Test for: CollisionException

### Changed
- ExceptionControllerImpl now throws NotHandledException if cannot
handle an Exception
- ExceptionControllerImpl now throws CollisionException if there are
more than one delegate that handles the same exception for the same
scope
- Tests for: ExceptionControllerImpl
- UseCase Callback method name from onError to onException

### Removed
- DelegatesCollisionError
- Error
- NotHandledError
- NotRecoverableError
- Unused strings