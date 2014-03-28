package edu.zju.bme.clever.website.dao;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import edu.zju.bme.clever.website.model.CommitSequence;

@Transactional
@Component("CommitSequenceDao")
public class CommitSequenceDaoImpl implements CommitSequenceDao {

	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	public void saveCommitSequence(CommitSequence commitSequence) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(commitSequence);
	}
}
