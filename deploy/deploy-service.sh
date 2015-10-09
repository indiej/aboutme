#!/bin/bash

# $1 = the kubernetes context (specified in kubeconfig)
# $2 = the kubernetes namespace
# $3 = directory that contains your kubernetes files to deploy
# $4 = container tag
DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
NAMESPACE=$2

echo "Deploying service to https://admin:${KUBEPASSWORD}@${KUBEIP}/api/v1/proxy/namespaces/$2/services/${SERVICENAME}"

#make sure we have the kubectl comand
chmod +x $DIR/ensure-kubectl.sh
$DIR/ensure-kubectl.sh

# delete service (does nothing if service does not exist already)
for f in $3/*.yaml; do envsubst < $f > kubetemp.yaml; cat kubetemp.yaml; ~/.kube/kubectl delete --namespace=$2 -f kubetemp.yaml || /bin/true; done

# create service (does nothing if the service already exists)
for f in $3/*.yaml; do envsubst < $f > kubetemp.yaml; ~/.kube/kubectl create --namespace=$2 -f kubetemp.yaml || /bin/true; done

# perform a rolling update. $CIRCLE_SHA1 can be used to tag, push and run your container
#~/.kube/kubectl rolling-update ${SERVICENAME} --image=${DOCKER_REGISTRY}/${CONTAINER1}:$4 --namespace=$2  || /bin/true