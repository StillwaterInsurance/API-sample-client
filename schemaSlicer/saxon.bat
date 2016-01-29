
echo off

rem java -Xmx1024m -Xms512m -cp .\Saxon6.5.5\saxon.jar com.icl.saxon.StyleSheet -?

copy messages_home_quote_1.0.xml messages.xml
java -Xmx1024m -Xms512m -cp .\Saxon6.5.5\saxon.jar com.icl.saxon.StyleSheet -t -o home-quote-v1.0.xsd  acord-pcs-v1_26_0-ns-nodoc-codes.xsd PCS-schemaMessageExtract.xsl

copy messages_auto_quote_1.0.xml messages.xml
java -Xmx1024m -Xms512m -cp .\Saxon6.5.5\saxon.jar com.icl.saxon.StyleSheet -t -o auto-quote-v1.0.xsd  acord-pcs-v1_26_0-ns-nodoc-codes.xsd PCS-schemaMessageExtract.xsl

del messages.xml

pause