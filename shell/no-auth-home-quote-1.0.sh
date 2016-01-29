#!/bin/bash

. api.properties

# POST
curl -i -X POST -d @${HQREQUEST} ${HOST}/home/quote/v1.0 --header "Content-Type:text/xml"

# GET
curl -i -X GET -d @${HQREQUEST} ${HOST}/home/quote/v1.0 --header "Content-Type:text/xml"
