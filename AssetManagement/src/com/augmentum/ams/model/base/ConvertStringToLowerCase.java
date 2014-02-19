package com.augmentum.ams.model.base;

import org.hibernate.search.bridge.StringBridge;

/**
 * convert index to lower case when create index
 * @author Geoffrey.Zhao
 *
 */
public class ConvertStringToLowerCase implements StringBridge{

	@Override
	public String objectToString(Object value) {
		return value.toString().toLowerCase();
	}
}
