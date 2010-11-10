#!/bin/sh
base=`dirname $0`


lein compile && \
lein jar && \

rm -rf $base/war/*
mkdir -p $base/war/WEB-INF/lib 2> /dev/null

echo -n "Copying..."
cp -r $base/webapp/* war/ && \
cp $base/*.jar war/WEB-INF/lib && \
cp $base/lib/*.jar war/WEB-INF/lib && \
echo "Ok" && \

echo -n "Building war..." &&\
(cd $base/war && jar cf ../example.war .) &&\
echo "Ok"
