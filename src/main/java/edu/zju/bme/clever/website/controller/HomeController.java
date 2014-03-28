package edu.zju.bme.clever.website.controller;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.zju.bme.clever.website.dao.ARMBeanDao;
import edu.zju.bme.clever.website.dao.ArchetypeBeanDao;
import edu.zju.bme.clever.website.model.ARMBean;
import edu.zju.bme.clever.website.model.ArchetypeBean;

@Controller
public class HomeController {
	
	@Resource(name="ArchetypeBeanDao")
	private ArchetypeBeanDao archetypeBeanDao;
	
	@Resource(name="ARMBeanDao")
	private ARMBeanDao armBeanDao;
	
	@RequestMapping("/")
	public String archetypeManage() {
		return "archetypeManage";
	}

	@RequestMapping("/archetypeSearch")
	public ModelAndView archetypeSearch(@RequestParam String condition) {
		List<ArchetypeBean> archetypeBeanList = archetypeBeanDao.matchProbableByNamePart(condition);
		return new ModelAndView("archetypeSearch", "archetypeBeanList", archetypeBeanList);
	}

	@RequestMapping("/archetypeDisplay")
	public ModelAndView archetypeDisplay(@RequestParam String keyName) {
		ArchetypeBean archetypeBean = archetypeBeanDao.selectByName(keyName);
		ARMBean armBean = armBeanDao.findByName(keyName);
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("archetypeBean", archetypeBean);
		modelMap.addAttribute("armBean", armBean);
		return new ModelAndView("archetypeDisplay", modelMap);
	}
	
}
