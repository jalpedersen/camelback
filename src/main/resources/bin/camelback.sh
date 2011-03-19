#!/bin/sh
### BEGIN INIT INFO
# Provides:          camelback
# Required-Start:    $local_fs $remote_fs $network $syslog
# Required-Stop:     $local_fs $remote_fs $network $syslog
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: Camelback
# Description:       Camelback is a simple clusterable servlet-container.
### END INIT INFO

usage()
{
    echo "Usage: ${0} {start|stop|restart} "
    exit 1
}
ACTION=$1
[ -z $ACTION ] && usage

#Pull in external configuration
if [ -f /etc/camelback/init.rc ]; then
  . /etc/camelback/init.rc
fi
if [ -f $HOME/.camelbackrc ]; then
  . $HOME/.camelbackrc
fi

CB_TMPDIR=${CB_TMPDIR:-/tmp/camelback}
CB_HOME=${CB_HOME:-`dirname $0`/..}
CB_CONF=${CB_CONF:-$CB_HOME/etc/camelback.json}
CB_PIDFILE=${CB_PIDFILE:-$CB_TMPDIR/camelback.pid}

if [ -z "$JAVA" ]
then
  JAVA=`which java`
fi

JAVA_OPTIONS="$JAVA_OPTIONS -Djava.io.tmpdir=$CB_TMPDIR"
CMD="$JAVA -- $JAVA_OPTIONS -jar $CB_HOME/camelback.jar -c $CB_CONF"
mkdir -p $CB_TMPDIR 2> /dev/null

if [ -n "$CB_USER" ]; then
  START_OPTIONS="$START_OPTIONS -c $CB_USER"
  chown -R $CB_USER $CB_TMPDIR
fi

case "$ACTION" in
  start)
    echo -n "Starting Camelback: "
    start-stop-daemon $START_OPTIONS -S -b -m -p $CB_PIDFILE -d $CB_HOME -a $CMD 
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
