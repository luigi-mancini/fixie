package FixData;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Created by lmancini on 12/7/17.
 */

public class FieldParser {

    public FieldParser(String inFile, Map<String, FixField> fields) throws SAXException, IOException {
        XMLReader parser = XMLReaderFactory.createXMLReader();
        parser.setContentHandler(new FieldHandler(fields));
        parser.parse(inFile != null ? inFile : "xml/Fields.xml");
    }

    class FieldHandler extends DefaultHandler {
        boolean tag = false;
        boolean name = false;
        boolean type = false;

        FixField field = null;

        Map<String, FixField> fieldMap;

        public FieldHandler(Map<String, FixField> fieldMap) {
            this.fieldMap = fieldMap;
        }

        public void startElement(
                String nsURI,
                String localName,
                String rawName,
                Attributes attributes) throws SAXException {
            // Consult rawName since we aren't using xmlns prefixes here.
            if (rawName.equalsIgnoreCase("field")) {
                field = new FixField();
            }

            if (rawName.equalsIgnoreCase("tag")) tag = true;
            if (rawName.equalsIgnoreCase("name")) name = true;
            if (rawName.equalsIgnoreCase("value")) type = true;
        }

        public void endElement(
                String nsURI,
                String localName,
                String rawName) throws SAXException {
            // Consult rawName since we aren't using xmlns prefixes here.
            if (rawName.equalsIgnoreCase("field")) {
                fieldMap.put(field.tag, field);
                field = null;
            }
        }

        public void characters(
                char[] ch,
                int start,
                int length) {
            if (tag) {
                if (null != field) {
                    field.setTag(new String(ch, start, length));
                }
                tag = false;
            } else if (name) {
                if (null != field) {
                    field.setName(new String(ch, start, length));
                }
                name = false;
            } else if (type) {
                if (null != field) {
                    field.setType(new String(ch, start, length));
                }
                type = false;
            }
        }
    }
}
