#!/bin/bash

. api.properties

# POST
# curl -i --user ${ACCOUNTNAME}:${TOKEN} -X POST -d @${HQREQUEST} ${HOST}/status/ping/v1.0


# GET
curl -i --user ${ACCOUNTNAME}:${TOKEN} -X GET -d @${HQREQUEST} ${HOST}//status/ping/v1.0
