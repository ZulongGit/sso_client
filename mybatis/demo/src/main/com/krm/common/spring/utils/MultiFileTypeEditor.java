package com.krm.common.spring.utils;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

public class MultiFileTypeEditor extends PropertyEditorSupport {
	

	public void setAsText(String text) throws IllegalArgumentException {
		System.out.println(text);
	}

	/**
	 * Format the Date as String, using the specified DateFormat.
	 */
	public String getAsText() {
		return null;
	}
}
