package edu.zju.bme.clever.website.controller;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.zju.bme.clever.service.CleverService;
import edu.zju.bme.clever.website.dao.ServiceControl;

@Controller
public class ServiceController {
	@Resource(name="ServiceControl")
	private ServiceControl serviceControl;
	
	@RequestMapping("/serviceControl")
	public ModelAndView serviceControl() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("/beans.xml", ServiceController.class);
		CleverService client = (CleverService) context.getBean("wsclient");	
		return new ModelAndView("serviceControl", "archetypeIdList", client.getArchetypeIds());
	}

	@RequestMapping("/serviceReconfigure")
	@ResponseBody
	public ServiceControlResult serviceReconfigure() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("/beans.xml", ServiceController.class);
		CleverService client = (CleverService) context.getBean("wsclient");	
		serviceControl.execute(client);
		return new ServiceControlResult("");
	}
	
	public class ServiceControlResult {
		private String result;
		public ServiceControlResult(String result) {
			super();
			this.result = result;
		}
		public String getResult() {
			return result;
		}
		public void setResult(String result) {
			this.result = result;
		}
	}
}
