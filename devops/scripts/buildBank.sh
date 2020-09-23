#!/bin/bash

gradle clean :bank-commons:build -p=../../ && \
gradle clean :bank:build -p=../../ && \
docker image rm hobbikertesz17/bank:kecske ;\
docker build -f ../../bank/Dockerfile -t hobbikertesz17/bank:kecske . &&\
docker push hobbikertesz17/bank:kecske && \
curl -X DELETE http://localhost:8080/api/charts/bank-chart/0.1.0 ; \
helm package ../charts/bank-chart &&\
curl -F "chart=@bank-chart-0.1.0.tgz" http://localhost:8080/api/charts
