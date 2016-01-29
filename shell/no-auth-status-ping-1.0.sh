#!/bin/bash

. api.properties

# POST
#curl -i -X POST -d @${HQREQUEST} ${HOST}/status/ping/v1.0

# GET
curl -i -X GET -d @${HQREQUEST} ${HOST}/status/ping/v1.0
