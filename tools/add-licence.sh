#!/bin/sh

tmp=/tmp/tmp.java.$$
src=src/main/java

for f in `find $src -name \*.java`; do 
        touch $tmp;
	echo "/*" > $tmp; 
	cat LICENSE >> $tmp;
	echo >> $tmp; 
	echo "*/">> $tmp; 
	cat $f >> $tmp;
	mv $tmp $f;
done

