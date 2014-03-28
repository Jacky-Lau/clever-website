package edu.zju.bme.clever.website.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import edu.zju.bme.clever.website.model.ArchetypeBean;
import edu.zju.bme.clever.website.model.HistoriedArchetypeBean;
import edu.zju.bme.clever.website.util.CommitSequenceConstant;

@Transactional
@Component("ArchetypeBeanDao")
public class ArchetypeBeanDaoImpl implements ArchetypeBeanDao {

	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	public List<ArchetypeBean> matchProbableByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from ArchetypeBean as atb where atb.name like :conditionname");
		query.setString("conditionname", "%" + name + "%");
		@SuppressWarnings("unchecked")
		List<ArchetypeBean> list = query.list();
		return list;
	}

	public List<ArchetypeBean> matchProbableByNamePaging(String name, int sequenceOfPage, int perPageAmount) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from ArchetypeBean as atb where atb.name like :conditionname");
		query.setString("conditionname", "%" + name + "%");
		query.setFirstResult((sequenceOfPage - 1) * perPageAmount);
		query.setMaxResults(perPageAmount);
		@SuppressWarnings("unchecked")
		List<ArchetypeBean> list = query.list();
		return list;
	}

	public List<ArchetypeBean> matchProbableByNamePart(String name) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select new ArchetypeBean(atb.name,atb.description) from ArchetypeBean as atb where atb.name like :conditionname");
		query.setString("conditionname", "%" + name + "%");
		@SuppressWarnings("unchecked")
		List<ArchetypeBean> list = query.list();
		return list;
	}

	public List<ArchetypeBean> matchProbableByNamePagingPart(String name, int sequenceOfPage, int perPageAmount) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select new ArchetypeBean(atb.name,atb.description) from ArchetypeBean as atb where atb.name like :conditionname");
		query.setString("conditionname", "%" + name + "%");
		query.setFirstResult((sequenceOfPage - 1) * perPageAmount);
		query.setMaxResults(perPageAmount);
		@SuppressWarnings("unchecked")
		List<ArchetypeBean> list = query.list();
		return list;

	}

	public void saveOrUpdate(ArchetypeBean bean) {
		Session session = sessionFactory.getCurrentSession();
		HistoriedArchetypeBean historiedArchetypeBean = new HistoriedArchetypeBean();
		historiedArchetypeBean.setCommitSequence(bean.getCommitSequence());
		historiedArchetypeBean.setContent(bean.getContent());
		historiedArchetypeBean.setDescription(bean.getDescription());
		historiedArchetypeBean.setName(bean.getName());
		historiedArchetypeBean.setHistoriedTime(bean.getModifyTime());
		session.save(historiedArchetypeBean);
		ArchetypeBean existBean = selectByName(bean.getName());
		if (existBean != null) {
			existBean.setContent(bean.getContent());
			existBean.setModifyTime(bean.getModifyTime());
			existBean.setCommitSequence(bean.getCommitSequence());
			existBean.setDescription(bean.getDescription());
		} else {
			session.save(bean);
		}
	}

	public ArchetypeBean selectByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from ArchetypeBean as atb where atb.name = :conditionname");
		query.setString("conditionname", name);
		ArchetypeBean archetypeBean = (ArchetypeBean) query.uniqueResult();
		return archetypeBean;
	}

	public List<ArchetypeBean> selectAll() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from ArchetypeBean");
		@SuppressWarnings("unchecked")
		List<ArchetypeBean> archetypes = query.list();
		return archetypes;
	}

	public void deleteAndRestore(ArchetypeBean archetypeBean) {
		Session session = sessionFactory.getCurrentSession();
		ArchetypeBean archetypeBeanRestored = this.selectByName(archetypeBean.getName());
		Query query = session.createQuery(
				"from HistoriedArchetypeBean as hab "
						+ "where hab.commitSequence.commitValidation=:conditionCommitValidation and hab.name=:conditionname "
						+ "order by hab.commitSequence.id desc ");
		query.setString("conditionname", archetypeBean.getName());
		query.setString("conditionCommitValidation", CommitSequenceConstant.VALID);
		HistoriedArchetypeBean archetypeBeanValidated = (HistoriedArchetypeBean) query.uniqueResult();
		if (archetypeBeanValidated != null) {
			archetypeBeanRestored.setCommitSequence(archetypeBeanValidated.getCommitSequence());
			archetypeBeanRestored.setContent(archetypeBeanValidated.getContent());
			archetypeBeanRestored.setDescription(archetypeBeanValidated.getDescription());
			archetypeBeanRestored.setModifyTime(archetypeBeanValidated.getHistoriedTime());
			archetypeBeanRestored.setName(archetypeBeanValidated.getName());
		} else {
			session.delete(archetypeBeanRestored);
		}
	}
}
