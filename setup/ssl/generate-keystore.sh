#/bin/sh
keystore_dir=../../etc
keystore_file=monty.jks

mkdir -p $keystore_dir && \
password=tobechanged
keytool -genkeypair -dname "cn=Jesper Andre Lyngesen Pedersen, ou=Development, o=Signaut, c=DK" \
                -alias monty -keypass $password -keystore $keystore_dir/$keystore_file \
                -storepass $password -validity 365
