#!/bin/bash

. api.properties

DATE=`date +%G-%m-%d`
NEXT_YEAR=$((`date +%G` + 1))
DATE2=`date +${NEXT_YEAR}-%m-%d`

STEP1=`sed "s/DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD/${DATE}/g" ./requests/H3_Quote_Request.xml-TEMPLATE`
echo $STEP1 | sed "s/NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN/${DATE2}/g" > ./requests/H3_Quote_Request.xml

STEP1=`sed "s/DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD/${DATE}/g"  ./requests/H4_Quote_Request.xml-TEMPLATE`
echo $STEP1 | sed "s/NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN/${DATE2}/g" > ./requests/H4_Quote_Request.xml

STEP1=`sed "s/DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD/${DATE}/g"  ./requests/H6_Quote_Request.xml-TEMPLATE`
echo $STEP1 | sed "s/NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN/${DATE2}/g" > ./requests/H6_Quote_Request.xml


# POST
# H3 Homeowners example
curl -i --user ${ACCOUNTNAME}:${TOKEN} -X POST -d @./requests/H3_Quote_Request.xml ${HOST}/home/quote/v1.0 --header "Content-Type:text/xml"

# H4 - Renters example
curl -i --user ${ACCOUNTNAME}:${TOKEN} -X POST -d @./requests/H4_Quote_Request.xml ${HOST}/home/quote/v1.0 --header "Content-Type:text/xml"

# H6 Condo example
curl -i --user ${ACCOUNTNAME}:${TOKEN} -X POST -d @./requests/H6_Quote_Request.xml ${HOST}/home/quote/v1.0 --header "Content-Type:text/xml"

# No content type header (this wail fail)
#curl -i --user ${ACCOUNTNAME}:${TOKEN} -X POST -d @./requests/H6_Quote_Request.xml ${HOST}/home/quote/v1.0

# GET (this will fail)
#curl -i --user ${ACCOUNTNAME}:${TOKEN} -X GET -d @${HQREQUEST} ${HOST}/home/quote/v1.0 --header "Content-Type:text/xml"
