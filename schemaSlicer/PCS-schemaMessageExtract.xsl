<?xml version="1.0" encoding="utf-8"?>
<!--

COPYRIGHT NOTICE
________________

(c) 2011 ACORD. All Rights Reserved.

Usage Information
__________________

  This stylesheet processes an W3C schema definition and 
  extracts all the elementDefs required to support the 
  message or aggregate name provided in the $startMessage
  parameter. This effectively subsets the schema to a 
  smaller unit that contains everything needed to validate 
  that message or aggregate. The command line would be:
  
        saxon -o message.xsd P&C-schema.xsd multipleschemaMessageExtract.xsl 

	
	NOTE: This stylesheet makes use of SAXON extensions and
	      SAXON can be found at: http://saxon.sourceforge.net/
-->


<!-- Version 1.6-->


<xsl:stylesheet version="1.0" 
			xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
			xmlns:xsd="http://www.w3.org/2001/XMLSchema"
			xmlns:saxon="http://icl.com/saxon"
 			extension-element-prefixes="saxon">

<xsl:output  indent="no"
             method="xml"/>

<!-- Name of the message or starting aggregate for extraction -->
<xsl:variable name="startMessage" select="''" saxon:assignable="yes"/>
<xsl:variable name="allMessages" select="''" saxon:assignable="yes"/>
	
<!--<xsl:param name="startMessage" select="'PersAutoPolicyAddRq'"/>-->
	
<!-- variables used to track elements, groups or datatypes already processed
		- note using SAXON specific function for these variables 
		- use    <saxon:assign name="i" select=""/> to update variable -->

<xsl:variable name="ElementList" select="''" saxon:assignable="yes"/>
<xsl:variable name="ElementList2" select="''" saxon:assignable="yes"/>
<xsl:variable name="ElementList_temp" select="''" saxon:assignable="yes"/>
<xsl:variable name="AttributeList" select="''" saxon:assignable="yes"/>
<xsl:variable name="GroupList" select="''" saxon:assignable="yes"/>
<xsl:variable name="AttributeGroupList" select="''" saxon:assignable="yes"/>
<xsl:variable name="DataTypeList" select="''" saxon:assignable="yes"/>
<xsl:variable name="Messages" select="document('messages.xml')"/>
<xsl:variable name="Group" select="''" saxon:assignable="yes"/>
<xsl:variable name="Found" select="''" saxon:assignable="yes"/>
<xsl:variable name="GroupId" select="''" saxon:assignable="yes"/>
<xsl:variable name="ComplexType" select="''"  saxon:assignable="yes"/>
<xsl:variable name="ComplexType2" select="''"  saxon:assignable="yes"/>	
	


<!-- These keys will make it easier to find the elementDefs of the elements, 
	groupDefs, and data types in the schema -->
	
	<xsl:key name="elementDefs" match="xsd:element" use="@name"/>
	<xsl:key name="groupDefs" match="xsd:group" use="@name"/>
	<xsl:key name="typeDefs" match="xsd:complexType" use="@name"/>
	<xsl:key name="typeDefs" match="xsd:simpleType" use="@name"/>
	
	<!-- use to copy the contents as found in the source document -->
<xsl:template match="@*|node()" mode="ident">
<xsl:copy>
<xsl:apply-templates select="@*|node()" mode="ident"/>
</xsl:copy>			
</xsl:template>
	
	<xsl:template match="@*|node()" mode="ident2">
		<xsl:choose>
			<xsl:when test="@ref ">
				<xsl:variable name="test" select="@ref"></xsl:variable>
				<xsl:choose>
					<xsl:when test="substring($test,0,3)='MR'">
						<xsl:if test="$Found='true'">
							<xsl:copy>
								<xsl:apply-templates select="@*|node()" mode="ident2"/>
							</xsl:copy>
						</xsl:if>
			                             </xsl:when>
					<xsl:when test="$Messages//Type[@name=$ComplexType] and $Messages//Type/@name!=''">
						<xsl:choose>
							<xsl:when test="@ref =$Messages//Type[@name=$ComplexType]/Element">
								<saxon:assign name="Found" select="'true'"/>
								<xsl:copy>
									<xsl:apply-templates select="@*|node()" mode="ident2"/>
								</xsl:copy>	
							</xsl:when>
							<xsl:when test="@ref =$Messages//Type[@name=$ComplexType]/Group">
								<saxon:assign name="Found" select="'true'"/>
								<xsl:copy>
									<xsl:apply-templates select="@*|node()" mode="ident2"/>
								</xsl:copy>	
							</xsl:when>
							<xsl:otherwise>
								<saxon:assign name="Found" select="'false'"/>
								
								<xsl:choose>
									<xsl:when test="@ref =$Messages//Type/Element">
										<!-- dont add to list as it may be used later -->
									</xsl:when>
									<xsl:otherwise>
										<xsl:choose>
											<xsl:when test="self::xsd:element">
												<saxon:assign name="ElementList_temp" select="concat($ElementList_temp, ' ', @ref, ' ')"/>
											</xsl:when>
											<xsl:when test="self::xsd:group">
												<saxon:assign name="GroupList" select="concat($GroupList, ' ', @ref, ' ')"/>
											</xsl:when>
										</xsl:choose>
									</xsl:otherwise>
								</xsl:choose>
								<xsl:apply-templates select="@*|node()" mode="ident2"/>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					<xsl:otherwise>
						<xsl:choose>
						<xsl:when test="@ref =$Messages//Messages/Groups/Group/Element">
						<saxon:assign name="Found" select="'true'"/>
						<xsl:copy>
							<xsl:apply-templates select="@*|node()" mode="ident2"/>
						</xsl:copy>	
						</xsl:when>
						<xsl:when test="@ref =$Messages//Messages/Type/Group">
						<saxon:assign name="Found" select="'true'"/>
						<xsl:copy>
							<xsl:apply-templates select="@*|node()" mode="ident2"/>
						</xsl:copy>	
					               </xsl:when>
						<xsl:otherwise>
							<saxon:assign name="Found" select="'false'"/>
							<xsl:choose>
								<xsl:when test="self::xsd:element">
									<saxon:assign name="ElementList" select="concat($ElementList, ' ', @ref, ' ')"/>
								</xsl:when>
								<xsl:when test="self::xsd:group">
									<saxon:assign name="GroupList" select="concat($GroupList, ' ', @ref, ' ')"/>
								</xsl:when>
							</xsl:choose>
							<xsl:apply-templates select="@*|node()" mode="ident2"/>
						</xsl:otherwise>
						</xsl:choose>
						
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:when test="@type ">
				<!--<xsl:variable name="test" select="@name"></xsl:variable>-->
				<xsl:choose>
					<xsl:when test="@name =$Messages//Messages/Groups/Group/Element">
						<saxon:assign name="Found" select="'true'"/>
						<xsl:copy>
							<xsl:apply-templates select="@*|node()" mode="ident2"/>
						</xsl:copy>	
					</xsl:when>
					<xsl:when test="self::xsd:attribute">
						<saxon:assign name="Found" select="'true'"/>
						<xsl:copy>
							<xsl:apply-templates select="@*|node()" mode="ident2"/>
						</xsl:copy>
					</xsl:when>
					<xsl:otherwise>
						<saxon:assign name="Found" select="'false'"/>
						<saxon:assign name="ElementList" select="concat($ElementList, ' ', @name, ' ')"/>
						<xsl:apply-templates select="@*|node()" mode="ident2"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:when test="self::xsd:sequence">
				<xsl:copy>
					
					<xsl:apply-templates select="@*|node()" mode="ident2"/>
				</xsl:copy>
			</xsl:when>
			<xsl:otherwise>
				<xsl:choose>
					<xsl:when test="$Found='true'">			
						<xsl:copy>
							<xsl:apply-templates select="@*|node()" mode="ident2"/>
						</xsl:copy>
					</xsl:when>
					<xsl:otherwise>
						<xsl:apply-templates select="@*|node()" mode="ident2"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	

<xsl:template match="text()"  mode="copy">
<!-- NICK
Commented this out so it would work with both fulldoc and nodoc versions of the schema -->
<!--<xsl:value-of select="normalize-space(.)"/>-->
</xsl:template>


<!-- processing starts here -->
<xsl:template match="/">
<!-- <xsl:message>
Parameter           Default

startMessage        PersAutoPolicyAddRq

</xsl:message> -->

<xsl:comment>
COPYRIGHT NOTICE:
(c) 2001-20011 ACORD.  All Rights Reserved.

   Use of this Schema and its documentation are covered by the terms and
   conditions file that was included in the distribution file that you
   found this Schema.

   http://www.acord.org

</xsl:comment>
<xsl:text>
	
</xsl:text>

<!--
<xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema" 
				xmlns:acord="http://www.ACORD.org/standards/Support/xml/v1.0"
				elementFormDefault="{//xsd:schema/@elementFormDefault}" 
				attributeFormDefault="{//xsd:schema/@attributeFormDefault}" 
				targetNamespace="{//xsd:schema/@targetNamespace}" 
				version="{//xsd:schema/@version}">
-->

<!-- Define the targetNamespace for the schema -->
<xsl:variable name="target"
   select="//xsd:schema/@targetNamespace"/>
      <!-- part of trick to make the namespace appear on schema element -->
   <xsl:variable name="ns-node">
      <xsl:element name="ns-element" namespace="{$target}"/>
   </xsl:variable>

	<xsd:schema xmlns:acorddoc="http://www.ACORD.org/standards/Support/xml/v1.0">
				
         <!-- the rest of the namespace trick -->
      <xsl:copy-of
            select="saxon:node-set($ns-node)/*/namespace::*[local-name()='']"/>
      <xsl:attribute name="targetNamespace"><xsl:value-of select="$target"/></xsl:attribute>

      <xsl:attribute name="elementFormDefault"><xsl:value-of select="//xsd:schema/@elementFormDefault"/></xsl:attribute>
      <xsl:attribute name="attributeFormDefault"><xsl:value-of select="//xsd:schema/@attributeFormDefault"/></xsl:attribute>


      <xsl:attribute name="version"><xsl:value-of select="//xsd:schema/@version"/></xsl:attribute>


<xsl:text>

</xsl:text>				
   <xsd:annotation>
      <xsd:appinfo>
      	<p>This schema has been extracted from the primary ACORD spec identified as:
         	Version: <xsl:value-of select="//xsd:schema/xsd:annotation/xsd:appinfo/acorddoc:version"/>, 
            Generated on: <xsl:value-of select="//xsd:schema/xsd:annotation/xsd:appinfo/acorddoc:genDate"/></p>
      </xsd:appinfo>
   </xsd:annotation>
<xsl:text>
	
</xsl:text>   
   <xsd:import namespace="http://www.w3.org/XML/1998/namespace" 
   				schemaLocation="xml-ns.xsd">
      <xsd:annotation>
         <xsd:documentation>Get access to the xml: attribute groups for xml:lang</xsd:documentation>
      </xsd:annotation>
   </xsd:import>
<xsl:text>
	
</xsl:text>	
   	<xsl:for-each select="$Messages//Messages/Message">
   		<saxon:assign name="allMessages" select="concat($allMessages, ' ', Name, ' :')"/>
   	</xsl:for-each>	
   	<xsl:call-template name="loopMessages">
   		<xsl:with-param name="messageList" select="$allMessages"/>
   	</xsl:call-template>
		
</xsd:schema>
	</xsl:template>

	<xsl:template name="loopMessages">
		<xsl:param name="messageList" select="':'"/>
		<xsl:if test="contains($messageList,':')">
			<saxon:assign name="startMessage" select="normalize-space(substring-before( $messageList,':'))" />
			<xsl:call-template name="getMessage">
				<xsl:with-param name="Message" select="$startMessage"/>
			</xsl:call-template>
			<xsl:call-template name="loopMessages">
				<xsl:with-param name="messageList" select="substring-after($messageList,':')"></xsl:with-param>
			</xsl:call-template>
		</xsl:if>
	</xsl:template>


<xsl:template name="getMessage">
	<xsl:param name="Message" select="'a'"/>
	<xsl:for-each select="//xsd:element[@name=$Message]">

		<!-- Copy the contents of the element to the output -->
		<xsl:apply-templates select="." mode="ident"/>

		<!-- Now process the definition of the message -->
		<xsl:apply-templates mode="copy"/>		

		<!-- Now process the data type of the element to find
				groups, attributes, attributeGroups, base types, etc -->
		<xsl:variable name="type" select="@type"/>
		<xsl:apply-templates select="//xsd:complexType[@name=$type] |
									 //xsd:simpleType[@name=$type]" mode="content"/>		

			<!-- Update the list of elements with the name of the message processed -->
		<saxon:assign name="ElementList" select="concat($ElementList, ' ', @name, ' ')"/>

	</xsl:for-each>
</xsl:template>



<xsl:template match="xsd:element" mode="copy">
<xsl:choose>
	<xsl:when test="@ref">
		<xsl:variable name="lookfor" select="@ref"/>
		<xsl:apply-templates select="//xsd:element[@name=$lookfor]" mode="copy"/>
<xsl:text>
	
</xsl:text>
   </xsl:when>
	<xsl:otherwise>
		<xsl:if test="parent::xsd:schema">
			<xsl:choose>
				<xsl:when test="$ComplexType2=$Messages//Type/@name and contains($ElementList_temp, concat(' ', @name, ' '))">
					<!-- do nothing -->
				</xsl:when>
				
			<xsl:otherwise>
			<xsl:if test="not(contains($ElementList, concat(' ', @name, ' '))) or (@name = $Messages//Type[@name=$ComplexType2]/Element and not(contains($ElementList2, concat(' ', @name, ' '))))">
				
				<xsl:if test="contains($ElementList, concat(' ', @name, ' '))">
					<saxon:assign name="ElementList2" select="concat($ElementList2, ' ', @name, ' ')"/>
				</xsl:if>
				<!-- copy the element definition -->
					
					<xsl:apply-templates select="." mode="ident"/>


					<!-- Update the list of elements with the name of the element processed -->
					<saxon:assign name="ElementList" select="concat($ElementList, ' ', @name, ' ')"/>
					<saxon:assign name="ElementList2" select="concat($ElementList2, ' ', @name, ' ')"/>

					<!-- Now process the content of the element -->
					<xsl:apply-templates mode="copy"/>		
				<!-- NICK
					Commented this out so it would work with both fulldoc and nodoc versions of the schema -->
					<!-- Now process the definition of the element's content
							groups, attributes, attributeGroups, base types, etc 
					<xsl:apply-templates mode="content"/>	-->
					
					<!-- Now process the data type of the element to find
							groups, attributes, attributeGroups, base types, etc -->
					<xsl:variable name="type" select="@type"/>
					<saxon:assign name="ComplexType" select="@name"/>
					<xsl:apply-templates select="//xsd:complexType[@name=$type] |
					                      //xsd:simpleType[@name=$type]" mode="content"/>		

					<xsl:variable name="base" select="xsd:complexType/xsd:simpleContent/xsd:extension/@base |
																 xsd:complexType/xsd:simpleContent/xsd:restriction/@base |
																 xsd:complexType/xsd:complexContent/xsd:extension/@base |
																 xsd:complexType/xsd:complexContent/xsd:restriction/@base" />
					<xsl:apply-templates select="//xsd:simpleType[@name=$base] |
		                         //xsd:complexType[@name=$base]" mode="content"/>	
			</xsl:if>
			</xsl:otherwise>	
		</xsl:choose>
			
		</xsl:if>
	
	</xsl:otherwise>
</xsl:choose>

</xsl:template>


<xsl:template match="xsd:complexType" mode="content">
<xsl:choose>
	<xsl:when test="@ref">
		<xsl:variable name="lookfor" select="@ref"/>
		<xsl:apply-templates select="//xsd:complexType[@name=$lookfor]" mode="content"/>
   </xsl:when>
	<xsl:otherwise>
<xsl:if test="not(contains($DataTypeList, concat(' ', @name, ' ')))">
		<!-- copy the group definition -->
	<xsl:choose>
	<xsl:when test="@name=$Messages//Type/@name">
		<saxon:assign name="ComplexType" select="@name"/>
		<saxon:assign name="ElementList_temp" select="''"/>
		<saxon:assign name="Found" select="'true'"/>
		<xsl:apply-templates select="." mode="ident2"/>
	</xsl:when>
	
	<xsl:otherwise>
		<xsl:apply-templates select="." mode="ident"/>
	</xsl:otherwise>
	</xsl:choose>
	
	<!--<xsl:apply-templates select="." mode="ident"/>-->
	
		<!-- Now process the content of the type -->
	             <!-- need to reset complex type first -->
	          <saxon:assign name="ComplexType2" select="$ComplexType"/>
	            <saxon:assign name="ComplexType" select="''"/>
					
	
		<xsl:apply-templates mode="copy"/>	
		
		<xsl:variable name="base" select="xsd:simpleContent/xsd:extension/@base |
													 xsd:simpleContent/xsd:restriction/@base |
													 xsd:complexContent/xsd:extension/@base |
													 xsd:complexContent/xsd:restriction/@base" />
		<xsl:apply-templates select="//xsd:simpleType[@name=$base] |
		                         //xsd:complexType[@name=$base]" mode="content"/>		

		<!-- Now process the base type of the type to find
				groups, attributes, attributeGroups, base types, etc -->
		<xsl:variable name="base2" select="descendant::xsd:extension/@base |
											  descendant::xsd:restriction/@base"/>
		<xsl:apply-templates select="//xsd:complexType[@name=$base2]" mode="content"/>		

			<!-- Update the list of groups with the name of the group processed -->
		<saxon:assign name="DataTypeList" select="concat($DataTypeList, ' ', @name, ' ')"/>
</xsl:if>
	</xsl:otherwise>
</xsl:choose>

</xsl:template>


<xsl:template match="xsd:simpleType" mode="content">
<xsl:choose>
	<xsl:when test="@ref">
		<xsl:variable name="lookfor" select="@ref"/>
		<xsl:apply-templates select="//xsd:simpleType[@name=$lookfor]" mode="content"/>

   </xsl:when>
	<xsl:otherwise>
		<xsl:if test="not(contains($DataTypeList, concat(' ', @name, ' ')))">
				<!-- copy the group definition -->
				<xsl:apply-templates select="." mode="ident"/>

				<!-- Now process the content of the type -->
				<xsl:apply-templates mode="copy"/>	

				<xsl:variable name="base" select="xsd:restriction/@base | xsd:extension/@base"/>
				<xsl:apply-templates select="//xsd:simpleType[@name=$base]" mode="content"/>		
					<!-- Update the list of type with the name of the type processed -->

				<saxon:assign name="DataTypeList" select="concat($DataTypeList, ' ', @name, ' ')"/>
		</xsl:if>	
	</xsl:otherwise>
</xsl:choose>

</xsl:template>


	<xsl:template match="xsd:group" mode="copy">
		<xsl:choose>
			<xsl:when test="@ref">
				<xsl:variable name="lookfor" select="@ref"/>
				<xsl:apply-templates select="//xsd:group[@name=$lookfor]" mode="copy"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:if test="not(contains($GroupList, concat(' ', @name, ' ')))">
					<!--  add group specific slice in here -->
					<xsl:choose>
						<xsl:when test="@name=$Messages//Messages/Groups/Group/@name">
							<saxon:assign name="Found" select="'true'"/>
							<saxon:assign name="ElementList_temp" select="''"/>
							<saxon:assign name="GroupId" select="@name"/>
							<xsl:apply-templates select="." mode="ident2"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:apply-templates select="." mode="ident"/>
						</xsl:otherwise>
					</xsl:choose>
					<!-- copy the group definition 
						<xsl:apply-templates select="." mode="ident"/>-->
					<!-- Update the list of groups with the name of the group processed -->
					<saxon:assign name="GroupList" select="concat($GroupList, ' ', @name, ' ')"/>
					<!-- Now process the content of the group -->
					<xsl:apply-templates mode="copy"/>
					
				</xsl:if>
			</xsl:otherwise>
		</xsl:choose>
		
	</xsl:template>
	
	<xsl:template match="xsd:sequence" mode="ident">
		<xsl:choose>
			<xsl:when test="@id=$Messages//Messages/Groups/Group/@name">
				<saxon:assign name="Found" select="'true'"/>
				<saxon:assign name="GroupId" select="@name"/>
				<xsl:apply-templates select="." mode="ident2"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:copy>
					<xsl:apply-templates select="@*|node()" mode="ident"/>
				</xsl:copy>
			</xsl:otherwise>
		</xsl:choose>
		
	</xsl:template>
	
	

<!--<xsl:template match="xsd:group" mode="copy">
<xsl:choose>
	<xsl:when test="@ref">
		<xsl:variable name="lookfor" select="@ref"/>
		<xsl:apply-templates select="//xsd:group[@name=$lookfor]" mode="copy"/>
   </xsl:when>
	<xsl:otherwise>
		<xsl:if test="not(contains($GroupList, concat(' ', @name, ' ')))">
			 copy the group definition 
			<xsl:apply-templates select="." mode="ident"/>
			 Update the list of groups with the name of the group processed 
			<saxon:assign name="GroupList" select="concat($GroupList, ' ', @name, ' ')"/>
			Now process the content of the group
			<xsl:apply-templates mode="copy"/>
			
		</xsl:if>
	</xsl:otherwise>
</xsl:choose>

</xsl:template>-->

<xsl:template match="xsd:attributeGroup" mode="copy">
<xsl:choose>
	<xsl:when test="@ref">
		<xsl:variable name="lookfor" select="@ref"/>
		<xsl:apply-templates select="//xsd:attributeGroup[@name=$lookfor]" mode="copy"/>
   </xsl:when>
	<xsl:otherwise>
		<xsl:if test="not(contains($AttributeGroupList, concat(' ', @name, ' ')))">
				<!-- copy the group definition -->
				<xsl:apply-templates select="." mode="ident"/>
					<!-- Update the list of groups with the name of the group processed -->
				<saxon:assign name="AttributeGroupList" select="concat($AttributeGroupList, ' ', @name, ' ')"/>
				<!-- Now process the content of the group -->
				<xsl:apply-templates mode="copy"/>		
		</xsl:if>
	</xsl:otherwise>
</xsl:choose>

</xsl:template>




<xsl:template match="xsd:attribute" mode="copy">
<xsl:choose>
	<xsl:when test="@ref">
		<xsl:variable name="lookfor" select="@ref"/>
		<xsl:apply-templates select="//xsd:attribute[@name=$lookfor]" mode="copy"/>

   </xsl:when>
	<xsl:otherwise>
			<!-- Process if a global definition only -->

			<xsl:if test="parent::xsd:schema">
				<xsl:if test="not(contains($AttributeList, concat(' ', @name, ' '))) |
								parent::xsd:complexType">
						<!-- copy the attribute definition -->
						<xsl:apply-templates select="." mode="ident"/>


					<!-- Update the list of attributes with the name of the attribute
										processed - need to handle Local vs Global definitions -->
				<xsl:if test="not(parent::xsd:complexType)">
					<saxon:assign name="AttributeList" select="concat($AttributeList, ' ', @name, ' ')"/>
				</xsl:if>


				</xsl:if>
			</xsl:if>
	
	</xsl:otherwise>
</xsl:choose>

				<!-- Now process the type of the attribute -->
		<xsl:variable name="type" select="@type"/>
		<xsl:apply-templates select="//xsd:simpleType[@name=$type]" mode="content"/>		
		<xsl:apply-templates select="//xsd:complexType[@name=$type]" mode="content"/>		

</xsl:template>






		<!-- Different mode for processing a group definition -->
<xsl:template match="xsd:attribute" mode="group">
ATTRIBUTE mode GROUP
<xsl:choose>
	<xsl:when test="@ref">
		<xsl:variable name="lookfor" select="@ref"/>
		<xsl:apply-templates select="//xsd:attribute[@name=$lookfor]" mode="copy"/>
   </xsl:when>
	<xsl:otherwise>
	<!-- Need to handle local and global declarations -->
<xsl:if test="not(contains($AttributeList, concat(' ', @name, ' '))) |
					parent::xsd:complexType">
		<!-- copy the attribute definition -->
		<xsl:apply-templates select="." mode="ident"/>
		<!-- Now process the content of the attribute -->
		<xsl:apply-templates mode="copy"/>		
		<xsl:variable name="type" select="@type"/>
		"<xsl:value-of select="@type"/>"
		<xsl:apply-templates select="//xsd:simpleType[@name=$type]" mode="content"/>		
		<xsl:apply-templates select="//xsd:complexType[@name=$type]" mode="content"/>		

			<!-- Update the list of attriubutes with the name of the attribute
								processed - need to handle Local vs Global definitions -->
		<xsl:if test="not(parent::xsd:complexType)">
			<saxon:assign name="AttributeList" select="concat($AttributeList, ' ', @name, ' ')"/>
		</xsl:if>
		
</xsl:if>
	
	</xsl:otherwise>
</xsl:choose>

</xsl:template>


<xsl:template match="xsd:attributeGroup" mode="content">
attributeGroup mode CONTENT
<xsl:choose>
	<xsl:when test="@ref">
		<xsl:variable name="lookfor" select="@ref"/>
		<xsl:apply-templates select="//xsd:attributeGroup[@name=$lookfor]" mode="content"/>
<xsl:text>
	
</xsl:text>
   </xsl:when>
	<xsl:otherwise>
<xsl:if test="not(contains($AttributeGroupList, concat(' ', @name, ' ')))">
		<!-- copy the group definition -->
		<xsl:apply-templates select="." mode="ident"/>
<xsl:text>
	
</xsl:text>	
		<!-- Now process the content of the group -->
		<xsl:apply-templates mode="group"/>		
			<!-- Update the list of groups with the name of the group processed -->
		<saxon:assign name="AttributeGroupList" select="concat($AttributeGroupList, ' ', @name, ' ')"/>
</xsl:if>
	
	</xsl:otherwise>
</xsl:choose>

</xsl:template>

</xsl:stylesheet>