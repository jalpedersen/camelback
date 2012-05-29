#!/bin/sh
#This will compile and install couchdb on an Ubuntu based system
xulrunner_version=1.9.2.28
couchdb_version=1.2.0
prefix=/opt/couchdb

ubuntu_release=`lsb_release -sc`

die() {
    echo $1
    exit 1
}
cd apache-couchdb-$couchdb_version || die "Cannot find couchdb $couchdb_version"

#dependencies
sudo apt-get install build-essential erlang libicu-dev help2man libcurl4-openssl-dev
if [ "$ubuntu_release" != "precise" ]; then
    sudo apt-get install build-essential erlang xulrunner-dev libicu-dev help2man libcurl4-openssl-dev
    ./configure --prefix=$prefix --with-js-lib=/usr/lib/xulrunner-$xulrunner_version/ \
        --with-js-include=/usr/lib/xulrunner-devel-$xulrunner_version/include || die "Failed to configure couchdb"
    sudo su -c "echo \"/usr/lib/xulrunner-$xulrunner_version/\" > /etc/ld.so.conf.d/xulrunner.conf" || die "Failed to add xulrunner to ld.so.conf.d"

else
    sudo apt-get install libmozjs185-dev
    ./configure --prefix=$prefix || die "Failed to configure couchdb"
fi


(\
make && \
sudo make install \
) &&\


# Add couchdb user account
sudo useradd -d $prefix/var/lib/couchdb couchdb 
sudo chown -R couchdb:couchdb $prefix/var/lib/couchdb $prefix/var/log/couchdb &&\
sudo chown -R couchdb:couchdb $prefix/var/run/couchdb $prefix/var/log/couchdb &&\

# next two steps fix problems where adding admin hangs or setting admins in local.ini hangs the start. Also fixes problems with reader_acl test.
sudo chown -R root:couchdb $prefix/etc/couchdb &&\
sudo chmod 664 $prefix/etc/couchdb/*.ini &&\
sudo chmod 775 $prefix/etc/couchdb/*.d &&\

#symlink init script
sudo ln -s $prefix/etc/init.d/couchdb /etc/init.d/ &&\

sudo ln -s $prefix/etc/logrotate.d/couchdb /etc/logrotate.d/couchdb

# Start couchdb on system start
sudo update-rc.d couchdb defaults 
