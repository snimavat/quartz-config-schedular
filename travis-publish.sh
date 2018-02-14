#!/usr/bin/env bash

set -e

if [[ -n $TRAVIS_TAG ]] || [[ $TRAVIS_BRANCH == 'master' && $TRAVIS_REPO_SLUG == "yakworks/quartz-config-scheduler" && $TRAVIS_PULL_REQUEST == 'false' ]]; then

    if [[ -n $TRAVIS_TAG ]]
    then
        echo "### publishing release to BinTray"
        ./gradlew view-tools:bintrayUpload --no-daemon
    else
         echo "### publishing snapshot"
        ./gradlew view-tools:publish --no-daemon
    fi

else
  echo "Not a Tag or Not on master branch, not publishing"
  echo "TRAVIS_BRANCH: $TRAVIS_BRANCH"
  echo "TRAVIS_TAG: $TRAVIS_TAG"
  echo "TRAVIS_REPO_SLUG: $TRAVIS_REPO_SLUG"
  echo "TRAVIS_PULL_REQUEST: $TRAVIS_PULL_REQUEST"
fi