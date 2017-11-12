#!/bin/bash

[ -z "$APP_HOME" ] && APP_HOME=`pwd`

[ ! -z "$APP_HOME/resources" ] && CLASSPATH=$CLASSPATH:$APP_HOME/resources

for file in $APP_HOME/lib/*.jar
do
    [ -f $file ] && CLASSPATH=$CLASSPATH:$file
done

CLASSNAME=com.StartUp

RUNJAVA="java $JAVA_OPTS -cp $CLASSPATH $CLASSNAME"

while [ 1 ] ; do
    PROCESS_NUM=`ps -ef | grep $APP_HOME/resources | grep -v grep | wc -l`
    [ $PROCESS_NUM -eq 0 ] && nohup $RUNJAVA > /dev/null &
    sleep 10
done
