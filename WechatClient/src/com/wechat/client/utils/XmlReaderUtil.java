package com.wechat.client.utils;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XmlReaderUtil {
	
	private static DocumentBuilder db = null;
	
	public void read(InputStream is) throws ParserConfigurationException, SAXException, IOException{
		if(db == null){
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		}
		Document doc = db.parse(is);
	}
}
