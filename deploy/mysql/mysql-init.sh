#!/bin/bash
echo 'Please wait 30 seconds in case mysql server needs time to start up'.
sleep 30
mysql -h $1 -u root -p$2 < ./deploy/mysql/mysql-init.sql
