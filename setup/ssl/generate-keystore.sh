#/bin/sh
keystore_dir=../../etc
keystore_file=monty.jks
password="${1-tobechanged}"

mkdir -p $keystore_dir && \
keytool -genkeypair -dname "cn=Jesper Andre Lyngesen Pedersen, ou=Development, o=Signaut, c=DK" \
                -alias monty -keypass "$password" -keystore $keystore_dir/$keystore_file \
                -storepass "$password" -validity 365
