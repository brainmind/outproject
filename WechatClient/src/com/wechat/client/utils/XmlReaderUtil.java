package com.wechat.client.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 *  <xml>
	<ToUserName><![CDATA[toUser]]></ToUserName>
	<FromUserName><![CDATA[FromUser]]></FromUserName>
	<CreateTime>123456789</CreateTime>
	<MsgType><![CDATA[event]]></MsgType>
	<Event><![CDATA[VIEW]]></Event>
	<EventKey><![CDATA[www.qq.com]]></EventKey>
	</xml>

 * @author yangzhou
 *
 */
public class XmlReaderUtil {
	
	private static DocumentBuilder db = null;
	
	public static Map<String, String> read(InputStream is) throws ParserConfigurationException, SAXException, IOException{
		if(db == null){
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		}
		Document doc = db.parse(is);
		NodeList list = doc.getChildNodes();
		if(list != null && list.getLength() > 0){
			Map<String, String> xmlMap = new HashMap<String, String>();
			iteratorNode(list, xmlMap);
			is.close();
			return xmlMap;
		}
		return null;
	}
	
	private static void iteratorNode(NodeList list, Map<String, String> xmlMap){
		if(list != null && list.getLength() > 0){
			for(int i=0; i<list.getLength(); i++){
				Node node = list.item(i);
				if(node.getChildNodes() != null && node.getChildNodes().getLength() > 1){
					iteratorNode(node.getChildNodes(), xmlMap);
				}else{
					String value = node.getNodeValue() == null ? node.getFirstChild().getNodeValue():node.getNodeValue();
					xmlMap.put(node.getNodeName(), value);
				}
			}
		}
	}
}
