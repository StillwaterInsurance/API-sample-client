#!/bin/bash

. api.properties

DATE=`date +%G-%m-%d`
NEXT_YEAR=$((`date +%G` + 1))
DATE2=`date +${NEXT_YEAR}-%m-%d`

STEP1=`sed "s/DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD/${DATE}/g" ./requests/Auto_Quote_Request.xml-TEMPLATE`
echo $STEP1 | sed "s/NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN/${DATE2}/g" > ./requests/Auto_Quote_Request.xml


# POST
# Auto Quote example
curl -i --user ${ACCOUNTNAME}:${TOKEN} -X POST -d @./requests/Auto_Quote_Request.xml ${HOST}/auto/quote/v1.0 --header "Content-Type:text/xml"

