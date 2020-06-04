#!/bin/bash

kubectl port-forward --namespace default svc/bank-app-rabbitmq 5672:5672 & kubectl port-forward --namespace default svc/bank-app-rabbitmq 15672:15672 & kubectl port-forward --namespace default svc/bank-app-mongodb 27017:27017 & kubectl port-forward --namespace default bank-app-keycloak-0 8080 & kubectl port-forward --namespace default svc/chartmuseum-chartmuseum 8081:8081 &