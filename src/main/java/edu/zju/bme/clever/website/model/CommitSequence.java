package edu.zju.bme.clever.website.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CommitSequence implements Serializable {
	private static final long serialVersionUID = -4754782613376077701L;

	private Integer id;
	private Date commitTime;
	private String commitValidation;

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
	}

	public String getCommitValidation() {
		return commitValidation;
	}

	public void setCommitValidation(String commitValidation) {
		this.commitValidation = commitValidation;
	}
}
