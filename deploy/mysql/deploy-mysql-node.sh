#!/bin/bash

export NODE=n$1
MYSQLNAME=pxc-$(tr [A-Z] [a-z] <<< ${CIRCLE_PROJECT_USERNAME:0:8})-$(tr [A-Z] [a-z] <<< ${CIRCLE_PROJECT_REPONAME:0:8})

for f in ./deploy/mysql/*.yaml; do envsubst < $f > kubetemp.yaml; cat kubetemp.yaml; ~/.kube/kubectl delete --namespace=$2 -f kubetemp.yaml || ~/.kube/kubectl create --namespace=$2 -f kubetemp.yaml || /bin/true; done