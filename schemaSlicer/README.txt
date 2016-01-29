
The included saxon.bat file can be used to slice up the full acord schema into something
a bit smaller and more manageable.  This is an example, you will have to modify the
messages.xml file to include the elements that you care about.  This is the process we used
when creating the schemas for our services here: 

	https://api-qua.stillwaterinsurance.com/api-docs/page?id=services
	https://api.stillwaterinsurance.com/api-docs/page?id=services

How to use:

	1) read the messages.xml so you know what elements will be included in result.xsd
	2) make sure java is installed and on your path ("java -version" works on your command line)
	3) run the saxon.bat script to generate sliced XSDs.


The acord-pcs-v1_26_0-ns-nodoc-codes.xsd and PCS-schemaMessageExtract.xsl files both come from
https://www.acord.org/Pages/default.aspx. You can find all of the acord XSDs here:

	https://www.acord.org/standards/Downloads/Pages/default.aspx
	https://www.acord.org/standards/downloads/Pages/PCSPublic1.aspx
	