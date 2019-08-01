package com.ideasStudio.website.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * 请求流xml处理类
 * @author Administrator
 *
 */
public class XmlUtil {

	/**
	 * 生成xml
	 */
	public static String genXml(Map<String,Object> map){
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		for (String k : map.keySet()) {
			Object value = map.get(k);
			sb.append("<"+k+">"+value+"</"+k+">");
		}
		sb.append("</xml>");
		try {
			return new String(sb.toString().getBytes("utf-8"),"ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 解析xml，返回第一季元素键值对，如果第一季元素柚子节点，则次节点的知识子节点的xml数据
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	public static Map doXMLParse(String strxml) throws JDOMException, IOException{
		strxml = strxml.replace("encoding=\".*\"", "encoding=\"UTF-8\"");
		if(null==strxml || "".equals(strxml)){
			return null;
		}
		Map m = new HashMap();
		InputStream in = new ByteArrayInputStream(strxml.getBytes("utf-8"));
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while(it.hasNext()){
			Element e = (Element) it.next();
			String k = e.getName();
			String v= "";
			List children = e.getChildren();
			if(children.isEmpty()){
				v = e.getTextNormalize();
			}else{
				v = XmlUtil.getChildrenText(children);
			}
			m.put(k, v);
		}
		in.close();
		return m;
	}
	
	public static String getChildrenText(List children){
		StringBuffer sb = new StringBuffer();
		if(!children.isEmpty()){
			Iterator it = children.iterator();
			while(it.hasNext()){
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<"+ name + ">");
				if(!list.isEmpty()){
					sb.append(XmlUtil.getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		return sb.toString();
	}
}
