package FixData;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lmancini on 12/7/17.
 */

public class EnumParser {

    public EnumParser(String inFile,  Map<String, Map<String, FixEnum>> enums) throws SAXException, IOException {
        XMLReader parser = XMLReaderFactory.createXMLReader();
        parser.setContentHandler(new EnumHandler(enums));
        parser.parse(inFile != null ? inFile : "xml/Enums.xml");
    }

    class EnumHandler extends DefaultHandler {
        boolean tag = false;
        boolean value = false;
        boolean name = false;

        FixEnum fenum = null;

        public EnumHandler(Map<String, Map<String, FixEnum>> enumMap) {
            this.enumMap = enumMap;
        }

        Map<String, Map<String, FixEnum>> enumMap;


        public void startElement(
                String nsURI,
                String localName,
                String rawName,
                Attributes attributes) throws SAXException {
            // Consult rawName since we aren't using xmlns prefixes here.
            if (rawName.equalsIgnoreCase("enum")) {
                fenum = new FixEnum();
            }

            if (rawName.equalsIgnoreCase("tag")) tag = true;
            if (rawName.equalsIgnoreCase("symbolicname")) name = true;
            if (rawName.equalsIgnoreCase("value")) value = true;
        }

        public void endElement(
                String nsURI,
                String localName,
                String rawName) throws SAXException {
            // Consult rawName since we aren't using xmlns prefixes here.
            if (rawName.equalsIgnoreCase("enum")) {
                if (!enumMap.containsKey(fenum.tag)) {
                    enumMap.put(fenum.tag, new HashMap());
                }
                enumMap.get(fenum.tag).put(fenum.value, fenum);
                fenum = null;
            }
        }

        public void characters(
                char[] ch,
                int start,
                int length) {
            if (tag) {
                if (null != fenum) {
                    fenum.setTag(new String(ch, start, length));
                }
                tag = false;
            } else if (name) {
                if (null != fenum) {
                    fenum.setName(new String(ch, start, length));
                }
                name = false;
            } else if (value) {
                if (null != fenum) {
                    fenum.setValue(new String(ch, start, length));
                }
                value = false;
            }
        }
    }
}
