# gradle-docker-appservers-plugin

Highly opinionated gradle plugin to start (Up), pause (Stop), and delete (Down) a dockerized application server.

## Status

| CI | Codecov | Docs | Questions | Release |
| :---: | :---: | :---: | :---: | :---: |
| [![Build Status](https://travis-ci.org/project-aries/gradle-docker-appservers-plugin.svg?branch=master)](https://travis-ci.org/project-aries/gradle-docker-appservers-plugin) | [![codecov](https://codecov.io/gh/project-aries/gradle-docker-appservers-plugin/branch/master/graph/badge.svg)](https://codecov.io/gh/project-aries/gradle-docker-appservers-plugin) | [![Docs](https://img.shields.io/badge/docs-latest-blue.svg)](http://htmlpreview.github.io/?https://github.com/project-aries/gradle-docker-appservers-plugin/blob/gh-pages/docs/index.html) | [![Stack Overflow](https://img.shields.io/badge/stack-overflow-4183C4.svg)](https://stackoverflow.com/questions/tagged/gradle-docker-appservers-plugin) | [![gradle-docker-appservers-plugin](https://api.bintray.com/packages/project-aries/libs-release-local/gradle-docker-appservers-plugin/images/download.svg) ](https://bintray.com/project-aries/libs-release-local/gradle-docker-appservers-plugin/_latestVersion) |

## Getting Started

```
buildscript() {
    repositories {
        jcenter()
    }
    dependencies {
        classpath group: 'com.aries', name: 'gradle-docker-appservers-plugin', version: 'X.Y.Z'
    }
}

apply plugin: 'gradle-docker-appservers-plugin'
```

## Motivation and Design Goals

Being based upon the [gradle-docker-application-plugin](https://github.com/project-aries/gradle-docker-application-plugin) the intent is to create dockerized app-server application definitions for developers to use as they see fit. Be it for desktop use, in a CICD pipeline, or for an arbitrary devops usecase you can easily manage (and customize should the need arise) a pre-defined dockerized app-server with a handful of tasks.

## Supported Databases

The below table lists the currently supported app-servers and their default connection details.

| Type | Commands |
| :--- | :---: |
| tomcat | **tomcatUp**, **tomcatStop**, **tomcatDown** |
