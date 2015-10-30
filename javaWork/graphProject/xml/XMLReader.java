package graphProject.xml;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.*;
import javax.xml.transform.stax.StAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import graphProject.GraphLibrary;
import graphProject.Result;


public class XMLReader {

	public static void main(String[] args) {
		//XMLParser("<graphProject><graph name=\"graph1\" costInterval=\"45\"><edge from=\"A\" to=\"B\" cost=\"5\"></edge><edge from=\"B\" to=\"C\" cost=\"8\"/><edge from=\"C\" to=\"A\" cost=\"12\"/></graph></graphProject>");
	}
	
	public String XMLParser(String xml) {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        Result result = ValidateXML(xmlInputFactory,xml);
        GraphLibrary library = new GraphLibrary();
        String graphName = "";
        if(result.errorOccured()) {
        	//System.out.println(result.getErrorMessage());
        	return result.getErrorMessage();
        }
        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new StringReader(xml));
            while(xmlEventReader.hasNext()){
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
               if (xmlEvent.isStartElement()){	
                   StartElement startElement = xmlEvent.asStartElement();
                   //graph tag
                   if(startElement.getName().getLocalPart().equals("graph")){
                       Attribute nameAttr = startElement.getAttributeByName(new QName("name"));
                       Attribute intervalAttr = startElement.getAttributeByName(new QName("costInterval"));
                       
                       if(nameAttr != null && intervalAttr != null){
                    	   graphName = nameAttr.getValue();
                    	   library.createGraph(nameAttr.getValue(), Integer.parseInt(intervalAttr.getValue()));
                       }
                   }
                   //Edge tag
                   else if(startElement.getName().getLocalPart().equals("edge")){
                	   
                       xmlEvent = xmlEventReader.nextEvent();
                       Attribute fromAttr = startElement.getAttributeByName(new QName("from"));
                       Attribute toAttr = startElement.getAttributeByName(new QName("to"));
                       Attribute costAttr = startElement.getAttributeByName(new QName("cost"));
                       if(fromAttr != null && toAttr != null && costAttr != null)
                    	   library.addEdge(graphName, fromAttr.getValue(), toAttr.getValue(), Integer.parseInt(costAttr.getValue()));
                    
                   }
                   //join tag
                   else if(startElement.getName().getLocalPart().equals("join")){
                	   Attribute fromAttr = startElement.getAttributeByName(new QName("from"));
                       Attribute toAttr = startElement.getAttributeByName(new QName("to"));
                       if(fromAttr != null && toAttr != null)
                    	   library.addGraph(fromAttr.getValue(),toAttr.getValue());
                   }
                   //path tag
                   else if(startElement.getName().getLocalPart().equals("path")){
                	   Attribute nameAttr = startElement.getAttributeByName(new QName("graph"));
                	   Attribute fromAttr = startElement.getAttributeByName(new QName("from"));
                       Attribute toAttr = startElement.getAttributeByName(new QName("to"));
                       if(fromAttr != null && toAttr != null && nameAttr != null)
                    	   library.computePath(nameAttr.getValue(), fromAttr.getValue(), toAttr.getValue());
                   }
               }
               //Check for complete end of the xml and print the errors
               if(xmlEvent.isEndElement()){
                   EndElement endElement = xmlEvent.asEndElement();
                   if(endElement.getName().getLocalPart().equals("graphProject")){
                       //print any errors
                	   
                   }
               }
            }
             
        } catch (XMLStreamException  e) {
            return "Exception";
        }
		return graphName;
	}

	private static Result ValidateXML(XMLInputFactory xmlInputFactory, String xml) {

		XMLStreamReader reader;
		Result result = new Result();
		try {
			reader = xmlInputFactory.createXMLStreamReader(new StringReader(xml));
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(new File(System.getProperty("user.dir")+"\\src\\graphProject\\schema.xsd"));
			Validator validator = schema.newValidator();
			validator.validate(new StAXSource(reader));
		} catch (XMLStreamException | SAXException | IOException  e) {
			result.setErrorFlag(true);
			result.setMessage(e.getMessage());
			return result;
		}
		result.setErrorFlag(false);
		return result;
	}

}
