#!/bin/bash

# resolve links - $0 may be a softlink
PRG="$0"

while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

# Get standard environment variables
PRGDIR=`dirname "$PRG"`

cd $PRGDIR

[ -z "$APP_HOME" ] && APP_HOME=`pwd`

APP_NUM=`ps -ef | grep $APP_HOME/resources | grep -v grep | awk '{print $2}'`
CHECK_NUM=`ps -ef | grep $APP_HOME/process.sh | grep -v grep | awk '{print $2}'`

[ -n "$APP_NUM" ] && kill -9 $APP_NUM
[ -n "$CHECK_NUM" ] && kill -9 $CHECK_NUM
