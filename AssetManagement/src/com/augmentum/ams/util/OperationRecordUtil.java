package com.augmentum.ams.util;

import java.lang.reflect.Field;
import java.util.Date;

public class OperationRecordUtil<T> {

	/**
	 * 
	 * @param RecordUtil
	 */
	public void record(RecordUtil recordUtil) {
		System.out.println(recordUtil);
		System.out.println(recordUtil.getDesc());
	}

	/**
	 * 
	 * @param clazz
	 *            Operation Class
	 * @param oldObj
	 *            Old Operation Object
	 * @param newObj
	 *            New Operation Object
	 * @param entityId
	 *            Entity Id
	 * @param user
	 *            Operator
	 */
	public void record(Class<T> clazz, T oldObj, T newObj, String entityId,
			String user) {
		if (oldObj == newObj) {
			return;
		}

		Field[] allFields = clazz.getDeclaredFields();// 得到指定类的所有属性Field.

		StringBuffer oldProperty = new StringBuffer();
		StringBuffer newProperty = new StringBuffer();
		RecordUtil RecordUtil = new RecordUtil();

		for (Field field : allFields) {
			field.setAccessible(true);// 设置类的私有字段属性可访问.
			try {
				if (!field.get(oldObj).equals(field.get(newObj))) {
					RecordUtil.setEntity(clazz.toString());

					oldProperty.append(field.getName()).append("=")
							.append(field.get(oldObj)).append(" ");
					newProperty.append(field.getName()).append("=")
							.append(field.get(newObj)).append(" ");
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		RecordUtil.setModifyDate(new Date());
		RecordUtil.setEntityId(entityId);
		RecordUtil.setUser(user);
		RecordUtil.setDesc("From " + oldProperty + " to " + newProperty);
		record(RecordUtil);
	}
}
