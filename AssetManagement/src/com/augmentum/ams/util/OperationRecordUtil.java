package com.augmentum.ams.util;

import java.lang.reflect.Field;
import java.util.Date;

public class OperationRecordUtil<T> {

	/**
	 * 支持自己定义一个RecordUtil对象并保存入库.
	 * 
	 * @param RecordUtil
	 */
	public void record(RecordUtil recordUtil) {
		System.out.println(recordUtil);
		System.out.println(recordUtil.getDesc());
	}

	/**
	 * 比较两个对象哪些属性发生变化,将变化的属性保存为RecordUtil对象.
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
			return;// 如果两个对象相同直接退出
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
		RecordUtil.setEntityId(entityId);// 记录修改的对象的主键Id.
		RecordUtil.setUser(user);// 记录修改者
		RecordUtil.setDesc("From " + oldProperty + " to " + newProperty);
		record(RecordUtil);
	}
}
