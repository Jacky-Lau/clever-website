package edu.zju.bme.clever.website.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import edu.zju.bme.clever.website.model.ARMBean;
import edu.zju.bme.clever.website.model.HistoriedARMBean;
import edu.zju.bme.clever.website.util.CommitSequenceConstant;

@Transactional
@Component("ARMBeanDao")
public class ARMBeanDaoImpl implements ARMBeanDao {

	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	public void saveOrUpdate(ARMBean bean) {
		Session session = sessionFactory.getCurrentSession();
		HistoriedARMBean historiedARMBean = new HistoriedARMBean();
		historiedARMBean.setCommitSequence(bean.getCommitSequence());
		historiedARMBean.setContent(bean.getContent());
		historiedARMBean.setHistoriedTime(bean.getModifyTime());
		historiedARMBean.setName(bean.getName());
		session.save(historiedARMBean);
		ARMBean existBean = findByName(bean.getName());
		if (existBean != null) {
			existBean.setContent(bean.getContent());
			existBean.setModifyTime(bean.getModifyTime());
			existBean.setCommitSequence(bean.getCommitSequence());
		} else {
			session.save(bean);
		}
	}

	public ARMBean findByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from ARMBean as arm where arm.name=:conditionname");
		query.setString("conditionname", name);
		ARMBean armBean = (ARMBean) query.uniqueResult();
		return armBean;
	}

	public List<ARMBean> selectAll() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from ARMBean");
		@SuppressWarnings("unchecked")
		List<ARMBean> arms = query.list();
		return arms;
	}

	public void deleteAndRestore(ARMBean armBean) {
		Session session = sessionFactory.getCurrentSession();
		ARMBean armBeanRestored = findByName(armBean.getName());
		Query query = session.createQuery(
				"from HistoriedARMBean as harm "
						+ "where harm.name=:conditionname and harm.commitSequence.commitValidation=:conditionCommitValidation "
						+ "order by harm.commitSequence.id desc");
		query.setString("conditionname", armBean.getName());
		query.setString("conditionCommitValidation", CommitSequenceConstant.VALID);
		HistoriedARMBean armBeanValidated = (HistoriedARMBean) query.uniqueResult();
		if (armBeanValidated != null) {
			armBeanRestored.setCommitSequence(armBeanValidated.getCommitSequence());
			armBeanRestored.setContent(armBeanValidated.getContent());
			armBeanRestored.setModifyTime(armBeanValidated.getHistoriedTime());
			armBeanRestored.setName(armBeanValidated.getName());
		} else {
			session.delete(armBeanRestored);
		}
	}
}
