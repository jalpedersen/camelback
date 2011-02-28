#!/bin/sh

openssl genrsa -aes256 -out server.key 4096 && \

openssl req -new -key server.key -out server.csr

#Remove key from private key
cp server.key server.key.org && \
openssl rsa -in server.key.org -out server.key

if [ x"$1" = x"sign" ]; then
  #Now sign it
  openssl x509 -req -days 365 -in server.csr \
               -signkey server.key -out server.crt
fi
