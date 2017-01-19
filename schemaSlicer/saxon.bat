
echo off

rem java -Xmx1024m -Xms512m -cp .\Saxon6.5.5\saxon.jar com.icl.saxon.StyleSheet -?

set dir=home_quote_1_0-slice
copy %dir%\messages_home_quote_1.0.xml messages.xml
java -Xmx1024m -Xms512m -cp .\Saxon6.5.5\saxon.jar com.icl.saxon.StyleSheet -t -o %dir%\home-quote-v1.0.xsd  acord-pcs-v1_26_0-ns-nodoc-codes.xsd PCS-schemaMessageExtract.xsl

set dir=auto_quote_1_0-slice
copy %dir%\messages_auto_quote_1.0.xml messages.xml
java -Xmx1024m -Xms512m -cp .\Saxon6.5.5\saxon.jar com.icl.saxon.StyleSheet -t -o %dir%\auto-quote-v1.0.xsd  acord-pcs-v1_26_0-ns-nodoc-codes.xsd PCS-schemaMessageExtract.xsl

set dir=policy_endorse-slice
copy %dir%\messages_policy_endorsement.xml messages.xml
java -Xmx1024m -Xms512m -cp .\Saxon6.5.5\saxon.jar com.icl.saxon.StyleSheet -t -o %dir%\policy-endorsement.xsd  acord-pcs-v1_26_0-ns-nodoc-codes.xsd PCS-schemaMessageExtract.xsl

del messages.xml

pause