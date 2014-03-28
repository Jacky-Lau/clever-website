package edu.zju.bme.clever.website.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import edu.zju.bme.clever.service.CleverService;
import edu.zju.bme.clever.website.model.ARMBean;
import edu.zju.bme.clever.website.model.ArchetypeBean;

@Component("ServiceControl")
public class ServiceControl {
	
	@Resource(name="ArchetypeBeanDao")
	private ArchetypeBeanDao archetypeBeanDao;
	
	@Resource(name="ARMBeanDao")
	private ARMBeanDao armBeanDao;

	public void execute(CleverService client) {
		List<ArchetypeBean> archetypes = archetypeBeanDao.selectAll();
		List<ARMBean> arms = armBeanDao.selectAll();
		client.stop();
		List<String> archetypesStrings = new ArrayList<String>();
		archetypes.forEach(a -> archetypesStrings.add(a.getContent()));
		List<String> armStrings = new ArrayList<String>();
		arms.forEach(a -> armStrings.add(a.getContent()));
		client.reconfigure(archetypesStrings, armStrings);
		client.start();
	}
}
