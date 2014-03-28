package edu.zju.bme.clever.website.dao;

import java.util.List;

import edu.zju.bme.clever.website.model.ArchetypeBean;

public interface ArchetypeBeanDao {
	List<ArchetypeBean> matchProbableByName(String name);

	List<ArchetypeBean> matchProbableByNamePaging(String name, int sequenceOfPage, int perPageAmount);

	List<ArchetypeBean> matchProbableByNamePart(String name);

	List<ArchetypeBean> matchProbableByNamePagingPart(String name, int sequenceOfPage, int perPageAmount);

	void saveOrUpdate(ArchetypeBean bean);

	ArchetypeBean selectByName(String name);

	List<ArchetypeBean> selectAll();

	void deleteAndRestore(ArchetypeBean archetypeBean);
}
