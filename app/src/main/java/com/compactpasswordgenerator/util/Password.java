package com.compactpasswordgenerator.util;

import com.orm.SugarRecord;

public class Password extends SugarRecord<Password> {
	public String name;
	public String value;

	public Password () {}

	public Password (String name, String value) {
		this.name = name;
		this.value = value;
	}
}
