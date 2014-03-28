package edu.zju.bme.clever.website.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class HistoriedARMBean implements Serializable {
	private static final long serialVersionUID = 8129161833339416767L;
	
	private Integer id;
	private String name;
	private String content;
	private Date historiedTime;
    private CommitSequence commitSequence;
	
    @Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Lob
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getHistoriedTime() {
		return historiedTime;
	}

	public void setHistoriedTime(Date historiedTime) {
		this.historiedTime = historiedTime;
	}
    
	@ManyToOne
	public CommitSequence getCommitSequence() {
		return commitSequence;
	}

	public void setCommitSequence(CommitSequence commitSequence) {
		this.commitSequence = commitSequence;
	}
}
