#!/bin/sh
release=$1

if [ "x$release" = "x" ]; then
    echo "Provide Ubuntu release please. (lucid for instance)"
    exit 1
fi
(cd /tmp && wget http://nginx.org/keys/nginx_signing.key && sudo apt-key add nginx_signing.key)

sudo apt-add-repository "deb http://nginx.org/packages/ubuntu/ $release nginx"
sudo apt-add-repository "deb-src http://nginx.org/packages/ubuntu/ $release nginx"

