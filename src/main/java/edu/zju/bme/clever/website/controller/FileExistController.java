package edu.zju.bme.clever.website.controller;

import java.util.Iterator;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import edu.zju.bme.clever.website.dao.ARMBeanDao;
import edu.zju.bme.clever.website.dao.ArchetypeBeanDao;
import edu.zju.bme.clever.website.model.ARMBean;
import edu.zju.bme.clever.website.model.ArchetypeBean;
import edu.zju.bme.clever.website.util.ARMUtil;
import edu.zju.bme.clever.website.util.ArchetypeUtil;
import edu.zju.bme.clever.website.util.FileExistConstant;
import edu.zju.bme.clever.website.util.FileUtil;

@Controller
public class FileExistController {
	private static Logger logger = Logger.getLogger(FileExistController.class.getName());
	
	@Resource(name="ArchetypeBeanDao")
	private ArchetypeBeanDao archetypeBeanDao;
	
	@Resource(name="ARMBeanDao")
	private ARMBeanDao armBeanDao;

	@RequestMapping(value="/fileExist", method=RequestMethod.POST)
	@ResponseBody
	public FileExistResult fileExist(MultipartHttpServletRequest request) {  
		String existStatus = "";
		String existName = "";
		String originalFileName = "";
		Iterator<String> it = request.getFileNames();
		if (it.hasNext()) {
			String singleFileFileName = it.next();
			MultipartFile singleFile = request.getFile(singleFileFileName);
			logger.info(singleFile.getName() + " size: " + singleFile.getSize());
			originalFileName = singleFileFileName;
			if (FileUtil.getFileType(singleFile).compareToIgnoreCase("adl") == 0) {
				ArchetypeUtil archetypeUtil = new ArchetypeUtil(singleFile);
				String archetypeId = archetypeUtil.getArchetypeId();
				String archetypeContent = archetypeUtil.getArchetypeContent();
				existName = archetypeId;
				if (!archetypeId.isEmpty() && !archetypeContent.isEmpty()) {
					ArchetypeBean archetypeBean = archetypeBeanDao.selectByName(archetypeId);
					if (archetypeBean != null) {
						if (archetypeBean.getContent().compareTo(archetypeContent) == 0) {
							existStatus = FileExistConstant.EXISTED;
						} else {
							existStatus = FileExistConstant.CHANGED;
						}
					} else {
						existStatus = FileExistConstant.NONE;
					}
				} else {
					existStatus = FileExistConstant.INVALID;
				}
			}

			if (FileUtil.getFileType(singleFile).compareToIgnoreCase("xml") == 0) {
				ARMUtil armUtil = new ARMUtil(singleFile);
				String archetypeId = armUtil.getArchetypeId();
				String armContent = armUtil.getARMContent();
				existName = archetypeId;
				if (!archetypeId.isEmpty() && !armContent.isEmpty()) {
					ARMBean armBean = armBeanDao.findByName(archetypeId);
					if (armBean != null) {
						if (armBean.getContent().compareTo(armContent) == 0) {
							existStatus = FileExistConstant.EXISTED;
						} else {
							existStatus = FileExistConstant.CHANGED;
						}
					} else {
						existStatus = FileExistConstant.NONE;
					}
				} else {
					existStatus = FileExistConstant.INVALID;
				}
			}
		}

		return new FileExistResult(existStatus, existName, originalFileName);
	}
	
	public class FileExistResult {
		public FileExistResult(String existStatus, String existName, String originalFileName) {
			super();
			this.existStatus = existStatus;
			this.existName = existName;
			this.originalFileName = originalFileName;
		}
		private String existStatus = "";
		private String existName = "";
		private String originalFileName = "";
		public String getExistStatus() {
			return existStatus;
		}
		public void setExistStatus(String existStatus) {
			this.existStatus = existStatus;
		}
		public String getExistName() {
			return existName;
		}
		public void setExistName(String existName) {
			this.existName = existName;
		}
		public String getOriginalFileName() {
			return originalFileName;
		}
		public void setOriginalFileName(String originalFileName) {
			this.originalFileName = originalFileName;
		}
	}
}
