#!/bin/bash

export NODE=n$1

for f in ./deploy/mysql/*.yaml; do envsubst < $f > kubetemp.yaml; cat kubetemp.yaml; ~/.kube/kubectl replace --namespace=$2 -f kubetemp.yaml || ~/.kube/kubectl create --namespace=$2 -f kubetemp.yaml || ~/.kube/kubectl update --namespace=$2 -f kubetemp.yaml || /bin/true; done