package edu.zju.bme.clever.website.dao;

import java.util.List;

import edu.zju.bme.clever.website.model.ARMBean;

public interface ARMBeanDao {
	void saveOrUpdate(ARMBean bean);

	ARMBean findByName(String name);

	List<ARMBean> selectAll();

	void deleteAndRestore(ARMBean armBean);
}
