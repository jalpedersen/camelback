#!/bin/sh
xulrunner_version=1.9.2.17
couchdb_version=1.1.0
prefix=/opt/couchdb

#dependencies
sudo apt-get install build-essential erlang xulrunner-dev libicu-dev help2man libcurl4-openssl-dev

#echo "/usr/lib/xulrunner-devel-$xulrunner_version/lib" > /etc/ld.so.conf.d/xulrunner.conf

(\
cd apache-couchdb-$couchdb_version && \
./configure --prefix=$prefix --with-js-lib=/usr/lib/xulrunner-devel-$xulrunner_version/lib \
            --with-js-include=/usr/lib/xulrunner-devel-$xulrunner_version/include &&\
make && \
sudo make install \
) &&\



# Add couchdb user account
sudo useradd -d $prefix/var/lib/couchdb couchdb 
sudo chown -R couchdb:couchdb $prefix/var/lib/couchdb $prefix/var/log/couchdb &&\

# next two steps fix problems where adding admin hangs or setting admins in local.ini hangs the start. Also fixes problems with reader_acl test.
sudo chown -R root:couchdb $prefix/etc/couchdb &&\
sudo chmod 664 $prefix/etc/couchdb/*.ini &&\
sudo chmod 775 $prefix/etc/couchdb/*.d &&\

#symlink init script
sudo ln -s $prefix/etc/init.d/couchdb /etc/init.d/ &&\

sudo ln -s $prefix/etc/logrotate.d/couchdb /etc/logrotate.d/couchdb

# Start couchdb on system start
sudo update-rc.d couchdb defaults 
