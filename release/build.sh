#!/usr/bin/env bash
if [ "$EUID" -ne 0 ]
  then echo "Please run as root"
  exit
fi
mkdir -p /usr/lib/sail-software/
cat stub.sh oxidec.jar > oxidec && chmod +x oxidec
cp oxidec.jar /usr/lib/sail-software/
cp oxidec /usr/bin/oxidec