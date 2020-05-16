#!/bin/bash
sed "s/tagVersion/$1/g" sample.yaml > spring-app-deploy.yml
