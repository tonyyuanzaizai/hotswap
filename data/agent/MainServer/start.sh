#!/bin/bash

export CLASSPATH=.:/data/agent/lib/commons-lang-2.4.jar:/data/agent/lib/commons-io-1.4.jar:/data/agent/lib/slf4j-api-1.7.6.jar:/usr/java/jdk1.8.0_152/lib/tools.jar:/data/agent/lib/logback-classic-1.1.2.jar:/data/agent/lib/logback-core-1.1.2.jar:/data/agent/patches

/usr/local/match3/jre/bin/java  -XXaltjvm=dcevm -javaagent:/data/agent/zaizai-agent.jar MainServer

