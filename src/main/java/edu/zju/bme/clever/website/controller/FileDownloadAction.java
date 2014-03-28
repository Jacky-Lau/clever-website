package edu.zju.bme.clever.website.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import edu.zju.bme.clever.website.dao.ARMBeanDao;
import edu.zju.bme.clever.website.dao.ARMBeanDaoImpl;
import edu.zju.bme.clever.website.dao.ArchetypeBeanDao;
import edu.zju.bme.clever.website.dao.ArchetypeBeanDaoImpl;
import edu.zju.bme.clever.website.model.ARMBean;
import edu.zju.bme.clever.website.model.ArchetypeBean;

//public class FileDownloadAction extends ActionSupport {
public class FileDownloadAction {
	private static final long serialVersionUID = -8185888950509500292L;

	private String fileType;
	private String keyName;
	private static ArchetypeBeanDao archetypeBeanDao = new ArchetypeBeanDaoImpl();
	private static ARMBeanDao armBeanDao = new ARMBeanDaoImpl();

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public InputStream getInputStream() throws UnsupportedEncodingException {
		InputStream input = null;
		if ("adl".equalsIgnoreCase(fileType)) {
			ArchetypeBean archetypeBean = archetypeBeanDao
					.selectByName(keyName);
			input = new ByteArrayInputStream(archetypeBean.getContent()
					.getBytes("UTF-8"));
		} else if ("arm".equalsIgnoreCase(fileType)) {
			ARMBean armBean = armBeanDao.findByName(keyName);
			input = new ByteArrayInputStream(armBean.getContent().getBytes(
					"UTF-8"));
		}
		return input;
	}

	public String execute() {
		return "success";
	}
}
