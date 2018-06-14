# gradle-docker-appserver-plugin

Highly opinionated gradle plugin to start (Up), pause (Stop), and delete (Down) a dockerized application server.

## Status

| CI | Codecov | Docs | Questions | Release |
| :---: | :---: | :---: | :---: | :---: |
| [![Build Status](https://travis-ci.org/project-aries/gradle-docker-appserver-plugin.svg?branch=master)](https://travis-ci.org/project-aries/gradle-docker-appserver-plugin) | [![codecov](https://codecov.io/gh/project-aries/gradle-docker-appserver-plugin/branch/master/graph/badge.svg)](https://codecov.io/gh/project-aries/gradle-docker-appserver-plugin) | [![Docs](https://img.shields.io/badge/docs-latest-blue.svg)](http://htmlpreview.github.io/?https://github.com/project-aries/gradle-docker-appserver-plugin/blob/gh-pages/docs/index.html) | [![Stack Overflow](https://img.shields.io/badge/stack-overflow-4183C4.svg)](https://stackoverflow.com/questions/tagged/gradle-docker-appserver-plugin) | [![gradle-docker-appserver-plugin](https://api.bintray.com/packages/project-aries/libs-release-local/gradle-docker-appserver-plugin/images/download.svg) ](https://bintray.com/project-aries/libs-release-local/gradle-docker-appserver-plugin/_latestVersion) |

## Getting Started

```
buildscript() {
    repositories {
        jcenter()
    }
    dependencies {
        classpath group: 'com.aries', name: 'gradle-docker-appserver-plugin', version: 'X.Y.Z'
    }
}

apply plugin: 'gradle-docker-appserver-plugin'
```
