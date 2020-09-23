#!/bin/bash


gradle clean :bank-commons:build -p=../../ && \
gradle clean :atm:build -p=../../ && \
docker image rm hobbikertesz17/atm:kecske ; \
#todo fix
cd ../../
docker build -f atm/Dockerfile -t hobbikertesz17/atm:kecske . && \
#todo fix
cd ./devops/scripts
docker push hobbikertesz17/atm:kecske && \
curl -X DELETE http://localhost:8080/api/charts/atm-chart/0.1.0 ; \
helm package ../charts/atm-chart &&\
curl -F "chart=@atm-chart-0.1.0.tgz" http://localhost:8080/api/charts