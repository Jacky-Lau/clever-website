package edu.zju.bme.clever.website.util;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.springframework.web.multipart.MultipartFile;

public class ARMUtil {
	private static Logger logger = Logger.getLogger(ARMUtil.class.getName());
	
	private Document doc = null;
	private String archetypeId = "";
	private String armContent = "";

	public ARMUtil(MultipartFile armFile) {
		try {
			SAXReader saxReader = new SAXReader();
			saxReader.setEntityResolver(new IgnoreDTDEntityResolver()); 
			doc = saxReader.read(armFile.getInputStream());
			Attribute classNode = (Attribute) doc.selectSingleNode("/hibernate-mapping/class/@name");
			archetypeId = classNode.getText();			
			armContent = doc.asXML();
		} catch (Exception e) {
			doc = null;
			archetypeId = "";
			armContent = "";
			logger.error("ARMUtil error", e);
		}
	}

	public String getArchetypeId() {
		return archetypeId;
	}

	public String getARMContent() {
		return armContent;
	}
}
