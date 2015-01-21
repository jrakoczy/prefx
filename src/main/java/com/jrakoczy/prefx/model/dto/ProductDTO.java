package com.jrakoczy.prefx.model.dto;

import java.io.InputStream;

public class ProductDTO {
	Long id;
	String name;
	String description;
	InputStream picutre;
	Long sid;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public InputStream getPicutre() {
		return picutre;
	}
	public void setPicutre(InputStream picutre) {
		this.picutre = picutre;
	}
	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
		this.sid = sid;
	}

}
