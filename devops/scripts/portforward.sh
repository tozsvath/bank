#!/bin/bash

kubectl port-forward svc/bank-app-rabbitmq 5672:5672 -n bank-services & kubectl port-forward svc/bank-app-rabbitmq 15672:15672 -n bank-services & kubectl port-forward svc/bank-app-mongodb 27017:27017 -n bank-services & kubectl port-forward bank-app-keycloak-0 8080 -n bank-services & kubectl port-forward --namespace common svc/chartmuseum-chartmuseum 8080:8080  -n bank-services &