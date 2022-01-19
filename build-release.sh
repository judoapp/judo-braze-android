#!/bin/sh

JAVA_HOME=/Applications/Android\ Studio.app/Contents/jre/Contents/Home ANDROID_HOME=~/Library/Android/sdk ./gradlew clean publishReleasePublicationToMavenRepository
