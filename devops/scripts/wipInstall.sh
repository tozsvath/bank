#!/bin/bash

#Prerequisits

#create namespace
kubectl create namespace common && \
kubectl create namespace bank-services && \

#add helm repos
helm install chartmuseum stable/chartmuseum -n common --set env.open.DISABLE_API=false && \
helm repo add nginx-stable https://helm.nginx.com/stable && \
helm repo add codecentric https://codecentric.github.io/helm-charts && \
helm repo add bitnami https://charts.bitnami.com/bitnami && \
#Add secret for keycloak
kubectl create secret generic bank-full-secret --from-file=../resources/keycloak/bank-realm.json -n bank-services && \
#install chartmuseum
kubectl port-forward --namespace common svc/chartmuseum-chartmuseum 8080:8080 & \

helm repo add chartmuseum http://localhost:8080 && \
#Ingress install
helm install ingress --namespace common stable/nginx-ingress --set controller.service.nodePorts.http=30598 --set controller.service.type=NodePort && \

minikube addons enable ingress && \

#Install service
#helm dep up ../charts/bank-app-root-chart && \
helm uninstall bank-app -n bank-services ; \
helm install bank-app -n bank-services ../charts/bank-app-root-chart