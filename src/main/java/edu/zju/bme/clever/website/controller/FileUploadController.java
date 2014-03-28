package edu.zju.bme.clever.website.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import edu.zju.bme.clever.service.CleverService;
import edu.zju.bme.clever.website.dao.ARMBeanDao;
import edu.zju.bme.clever.website.dao.ArchetypeBeanDao;
import edu.zju.bme.clever.website.dao.CommitSequenceDao;
import edu.zju.bme.clever.website.dao.ServiceControl;
import edu.zju.bme.clever.website.exception.ServiceValidationException;
import edu.zju.bme.clever.website.exception.RestoreFileException;
import edu.zju.bme.clever.website.exception.SaveFileException;
import edu.zju.bme.clever.website.model.ARMBean;
import edu.zju.bme.clever.website.model.ArchetypeBean;
import edu.zju.bme.clever.website.model.CommitSequence;
import edu.zju.bme.clever.website.util.ARMUtil;
import edu.zju.bme.clever.website.util.ArchetypeUtil;
import edu.zju.bme.clever.website.util.CommitSequenceConstant;
import edu.zju.bme.clever.website.util.FileUtil;

@Controller
public class FileUploadController {
	private static Logger logger = Logger.getLogger(FileUploadController.class.getName());

	@Resource(name="ArchetypeBeanDao")
	private ArchetypeBeanDao archetypeBeanDao;

	@Resource(name="ARMBeanDao")
	private ARMBeanDao armBeanDao;

	@Resource(name="CommitSequenceDao")
	private CommitSequenceDao commitSequenceDao;
	
	@Resource(name="ServiceControl")
	private ServiceControl serviceControl;

	private List<ArchetypeBean> constructArchetypeBeans(
			List<MultipartFile> upload,
			Date modifyTime, 
			CommitSequence version) {
		List<ArchetypeBean> list = new ArrayList<ArchetypeBean>();
		for (int i = 0; i < upload.size(); i++) {
			if (FileUtil.getFileType(upload.get(i)).compareToIgnoreCase("adl") == 0) {
				ArchetypeUtil archetypeUtil = new ArchetypeUtil(upload.get(i));
				String archetypeId = archetypeUtil.getArchetypeId();
				String archetypeDescription = archetypeUtil.getArchetypeDescription();
				String archetypeContent = archetypeUtil.getArchetypeContent();
				if (!archetypeId.isEmpty() && !archetypeContent.isEmpty()) {
					ArchetypeBean archetypeBean = new ArchetypeBean();
					archetypeBean.setModifyTime(modifyTime);
					archetypeBean.setContent(archetypeContent);
					archetypeBean.setName(archetypeId);
					archetypeBean.setDescription(archetypeDescription);
					archetypeBean.setCommitSequence(version);
					list.add(archetypeBean);
				}
			}
		}
		return list;
	}

	private List<ARMBean> constructArmBeans(
			List<MultipartFile> upload,
			Date modifyTime, 
			CommitSequence version) 
					throws Exception {
		List<ARMBean> list = new ArrayList<ARMBean>();
		for (int i = 0; i < upload.size(); i++) {
			if (FileUtil.getFileType(upload.get(i)).compareToIgnoreCase("xml") == 0) {
				ARMUtil armUtil = new ARMUtil(upload.get(i));
				String archetypeId = armUtil.getArchetypeId();
				String armContent = armUtil.getARMContent();
				if (!archetypeId.isEmpty() && !armContent.isEmpty()) {
					ARMBean armBean = new ARMBean();
					armBean.setContent(armContent);
					armBean.setModifyTime(modifyTime);
					armBean.setName(archetypeId);
					armBean.setCommitSequence(version);
					list.add(armBean);
				}
			}
		}
		return list;
	}

	@RequestMapping("/fileUpload")
	@ResponseBody
	public FileUploadResult fileUpload(MultipartHttpServletRequest request) {				
		List<MultipartFile> upload = new ArrayList<>();	
		for (Iterator<String> it = request.getFileNames(); it.hasNext(); ) {
			upload.add(request.getFile(it.next()));			
		}

		String uploadResult = "success";
		String uploadResultDescription = "";
		List<ArchetypeBean> listArchetypeBeans = null;
		List<ARMBean> listArmBeans = null;
		CommitSequence commitSequence = null;
		try {
			Date modifyTime = new Date(System.currentTimeMillis());
			commitSequence = new CommitSequence();
			commitSequence.setCommitValidation(CommitSequenceConstant.VALID);
			commitSequence.setCommitTime(modifyTime);
			commitSequenceDao.saveCommitSequence(commitSequence);
			listArchetypeBeans = constructArchetypeBeans(
					upload,
					modifyTime,
					commitSequence);
			listArmBeans = constructArmBeans(
					upload, 
					modifyTime, 
					commitSequence);
			save(listArmBeans, listArchetypeBeans);
			validate();
		} catch (SaveFileException e) {
			uploadResult = "fail";
			uploadResultDescription = "Error occured during save file to database";
			logger.error("Error occured during save file to database", e);
		} catch (ServiceValidationException e) {
			uploadResult = "fail";
			uploadResultDescription = "Error occured during validate hibernarm";
			logger.error("Error occured during validate hibernarm", e);
			commitSequence.setCommitValidation(CommitSequenceConstant.INVALID);
			commitSequenceDao.saveCommitSequence(commitSequence);
			cancel(listArmBeans, listArchetypeBeans);
		} catch (Throwable e) {
			uploadResult = "fail";
			uploadResultDescription = "Error occured during upload file";
			logger.error("Error occured during upload file", e);
			commitSequence.setCommitValidation(CommitSequenceConstant.INVALID);
			commitSequenceDao.saveCommitSequence(commitSequence);
			cancel(listArmBeans, listArchetypeBeans);
		} finally {
		}
		
		return new FileUploadResult(uploadResult, uploadResultDescription);
	}

	protected void validate() {
		try {
			@SuppressWarnings("resource")
			ApplicationContext context = new ClassPathXmlApplicationContext("/beans.xml", this.getClass());
			CleverService client = (CleverService) context.getBean("wsclientvalidation");
			serviceControl.execute(client);
		} catch (Exception e) {
			throw new ServiceValidationException(e);
		}
	}

	private void save(
			List<ARMBean> listArmBeans,
			List<ArchetypeBean> listArchetypeBeans) {
		try {
			listArmBeans.forEach(a -> armBeanDao.saveOrUpdate(a));
			listArchetypeBeans.forEach(a -> archetypeBeanDao.saveOrUpdate(a));
		} catch (Throwable e) {
			throw new SaveFileException(e);
		}
	}

	private void cancel(
			List<ARMBean> listArmBeans,
			List<ArchetypeBean> listArchetypeBeans) {
		try {
			listArmBeans.forEach(a -> armBeanDao.deleteAndRestore(a));
			listArchetypeBeans.forEach(a -> archetypeBeanDao.deleteAndRestore(a));
		} catch (Throwable e) {
			throw new RestoreFileException(e);
		}
	}
	
	public class FileUploadResult {
		private String uploadResult = "";
		private String uploadResultDescription = "";
		public FileUploadResult(String uploadResult, String uploadResultDescription) {
			super();
			this.uploadResult = uploadResult;
			this.uploadResultDescription = uploadResultDescription;
		}
		public String getUploadResult() {
			return uploadResult;
		}
		public void setUploadResult(String uploadResult) {
			this.uploadResult = uploadResult;
		}
		public String getUploadResultDescription() {
			return uploadResultDescription;
		}
		public void setUploadResultDescription(String uploadResultDescription) {
			this.uploadResultDescription = uploadResultDescription;
		}		
	}
}
