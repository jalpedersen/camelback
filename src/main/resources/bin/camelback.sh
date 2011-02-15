#!/bin/sh
# Startup script for Camelback

usage()
{
    echo "Usage: ${0} {start|stop|restart} "
    exit 1
}
ACTION=$1
[ -z $ACTION ] && usage

TMPDIR=${TMPDIR:-/tmp/camelback}
CB_HOME=${CB_HOME:-`dirname $0`/..}
CB_CONF=${CB_CONF:-$CB_HOME/etc/camelback.json}
CB_PIDFILE=${CB_PIDFILE:-$TMPDIR/camelback.pid}

if [ -z "$JAVA" ]
then
  JAVA=`which java`
fi

JAVA_OPTIONS="$JAVA_OPTIONS -Djava.io.tmpdir=$TMPDIR"
CMD="$JAVA -- $JAVA_OPTIONS -jar $CB_HOME/camelback.jar -c $CB_CONF"
mkdir -p $TMPDIR 2> /dev/null

case "$ACTION" in
  start)
    echo -n "Starting Camelback: "
    start-stop-daemon -S -b -m -p $CB_PIDFILE -d $CB_HOME -a $CMD 
    echo Ok
    ;;

  stop)
    echo -n "Stopping Camelback: "
    start-stop-daemon -K --retry 10 -p $CB_PIDFILE  
    if [ $? = 1 ]; then
      echo "Failed"
    else
      echo Ok
    fi
    ;;

  restart)
    $0 stop
    $0 start
    ;;

  check)
    echo "TMPDIR:     $TMPDIR"
    echo "CB_HOME:    $CB_HOME"
    echo "CB_CONF:    $CB_CONF"
    echo "CB_PIDFILE: $CB_PIDFILE"
    echo "CB_USER:    $CB_USER"
    ;;
  *)
    usage

    ;;
esac

exit 0
