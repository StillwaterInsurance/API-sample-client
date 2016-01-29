package com.stillwaterinsurance.api.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;
import java.util.Random;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.ACORDType;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class Utils {

	
	private static final String PROP_FILE = "client.properties";
	private static final String ACORD_URI = "http://www.ACORD.org/standards/PC_Surety/ACORD1/xml/";
	private static final String ACORD_NS = "ACORD";
	
	
	/**
	 * Loads the client.properties file from the classpath and returns a Properties object.
	 * 
	 * @return Properties
	 */
	public static final Properties getProps(){
		Properties prop = new Properties();
		InputStream input = null;
	 
		try {
	 
			//load props file from classpath
			input = Utils.class.getClassLoader().getResourceAsStream(PROP_FILE);
	 
			// load a properties from file
			prop.load(input);
	 
	 
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return prop;
	}
	
	/**
	 * Marshalls an ACORDType jaxb object to xml for posting to a service.
	 * 
	 * @param acord
	 * @return String xml
	 */
	public static final String jaxbMarshall(final ACORDType acord){
		
		StringWriter writer = new StringWriter();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ACORDType.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			
			//format the xml for easier debugging
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// the acord schema has many root elements.  jaxb does not know which element to use 
			// as the root, so this is a workaround
			JAXBElement<ACORDType> jaxbElement = new JAXBElement<ACORDType>(
					new QName(ACORD_URI, ACORD_NS), ACORDType.class, acord);
			
			jaxbMarshaller.marshal(jaxbElement, writer);
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return writer.toString();
		
	}
	
	/**
	 * Takes an XML ACORD message and converts it into an ACORDType object
	 * 
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final ACORDType jaxbUnmarshall(final String xml){
		
		StringReader stringReader = new StringReader(xml);
		ACORDType result = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ACORDType.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			
			//ACORDType root element does not have XmlRootElement annotation, so we must manually choose the root.
			JAXBElement<ACORDType> root = (JAXBElement<ACORDType>) jaxbUnmarshaller.unmarshal(stringReader);
			result = root.getValue();
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	public static final String formatXML(final String unformattedXml) {
        String result = "";
		try {
        	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db;
			db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(unformattedXml));
            final Document document = db.parse(is);
            
            OutputFormat format = new OutputFormat(document);
            //format.setLineWidth(65);
            format.setIndenting(true);
            format.setIndent(4);
            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(document);

            result =  out.toString();
        } catch (IOException e) {
        	// TODO Auto-generated catch block
        	e.printStackTrace();
        } catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
    }
	
	/**
	 * Generates a random RqUID for use in ACORD messages.
	 * 
	 * eg: A6DAB09E-C365-4C82-A686-69326EC8ABC1
	 * 
	 * @return String like A6DAB09E-C365-4C82-A686-69326EC8ABC1
	 */
	public static final String randomRqUid(){
		
		StringBuffer sb = new StringBuffer();

		sb.append(hexString(8));
        sb.append("-");
       
        sb.append(hexString(4));
        sb.append("-");
        
        sb.append(hexString(4));
        sb.append("-");
        
        sb.append(hexString(4));
        sb.append("-");
        
        sb.append(hexString(12));
        
		return sb.toString();
	}
	
	/**
	 * private method to generate a hex string with numbers and uppercase
	 * letters of given length.
	 * 
	 * @param int length
	 * @return String hex string
	 */
	private static final String hexString(final int length){
		Random r = new Random();
		int i = 0;
		StringBuffer sb = new StringBuffer();
        while(i < length){
            sb.append(Integer.toHexString(r.nextInt()));
            i++;
        }
        sb.setLength(length);
        return sb.toString().toUpperCase();
	}
	
}
