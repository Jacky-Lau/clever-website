package edu.zju.bme.clever.website.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class ArchetypeBean implements Serializable {
	private static final long serialVersionUID = -3471987239141601807L;

	private Integer id;
	private String name;
	private String content;
	private Date modifyTime;
	private String description;
	private CommitSequence commitSequence;

	public ArchetypeBean() {

	}

	public ArchetypeBean(String name, String description) {
		this.name = name;
		this.description = description;
	}

	@Lob
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(unique = true)
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

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@ManyToOne
	public CommitSequence getCommitSequence() {
		return commitSequence;
	}

	public void setCommitSequence(CommitSequence commitSequence) {
		this.commitSequence = commitSequence;
	}
}
