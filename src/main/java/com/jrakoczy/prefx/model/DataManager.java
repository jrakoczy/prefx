package com.jrakoczy.prefx.model;

import javax.servlet.ServletContext;

abstract class DataManager {

	protected ServletContext context;

	public DataManager(ServletContext context) {
		this.context = context;
	}

}
