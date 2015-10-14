#!/bin/bash
# used to install kubectl inside circleci.

# make the temp directory
if [ ! -e "/home/ubuntu/.kube" ]; then
    mkdir -p /home/ubuntu/.kube;
fi

if [ ! -e "/home/ubuntu/.kube/kubectl" ]; then
    wget https://storage.googleapis.com/kubernetes-release/release/v1.0.6/bin/linux/amd64/kubectl -O /home/ubuntu/.kube/kubectl
    chmod +x /home/ubuntu/.kube/kubectl
fi

if [ ! -e "/home/ubuntu/.kube/config" ]; then
    wget https://s3-us-west-2.amazonaws.com/concur-dev-launchertools/kubeconfig-nonprod-aws -O /home/ubuntu/.kube/config
fi


# add in getting the password from a protected environment variable 
# kubectl config set-credentials admin --username=admin --password=$kubepassword

# add in kubectl config use-context for different kubernetes clusters

~/.kube/kubectl version

##docker certificate needed to get push working in circleci
#sudo mkdir -p /etc/docker/certs.d/docker-registry.concur.com
#sudo wget https://s3-us-west-2.amazonaws.com/concur-dev-launchertools/digicert.crt -O /etc/docker/certs.d/docker-registry.concur.com/ca.crt