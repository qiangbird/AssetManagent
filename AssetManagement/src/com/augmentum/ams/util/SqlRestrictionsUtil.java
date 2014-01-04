package com.augmentum.ams.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 
 * Make up the SQL statement
 * 
 */
public class SqlRestrictionsUtil {

	public static String eq(String propertyName, Object value) {
		return " " + propertyName + "=" + "'" + value + "'";
	}

	public static String ne(String propertyName, Object value) {
		return " " + propertyName + "<>" + "'" + value + "'";
	}

	public static String gt(String propertyName, Object value) {
		return " " + propertyName + ">" + value;
	}

	public static String lt(String propertyName, Object value) {
		return " " + propertyName + "<" + value;
	}

	public static String le(String propertyName, Object value) {
		return " " + propertyName + "<=" + value;
	}

	public static String ge(String propertyName, Object value) {
		return " " + propertyName + ">=" + value;
	}

	public static String like(String propertyName, Object value) {
		return " " + propertyName + " like " + "'%" + value + "%'";
	}

	public String bettween(String propertyName, Object lo, Object hi) {
		return " " + propertyName + " bettween " + lo + " and " + hi;
	}

	public static String and(String sql1, String sql2) {
	    StringBuilder sqlBuilder = new StringBuilder();
	    sqlBuilder.append(sql1);
	    sqlBuilder.append(" and ");
	    sqlBuilder.append(sql2);
		return sqlBuilder.toString();
	}

	public static String or(String sql1, String sql2) {
	    StringBuilder sqlBuilder = new StringBuilder();
	    sqlBuilder.append(sql1);
	    sqlBuilder.append(" or ");
	    sqlBuilder.append(sql2);
		return sqlBuilder.toString();
	}

	public static String in(String field, List<?> arrays) {
		String inSql = field + " in(";
		StringBuilder builder = new StringBuilder();
		for (Object obj : arrays) {
			if (obj instanceof String) {
				builder.append("'" + obj + "'");
				builder.append(",");
			} else {
				builder.append(obj);
				builder.append(",");
			}
		}
		inSql += builder.substring(0, builder.length() - 1) + ")";
		return inSql;
	}
	
	@SuppressWarnings({ "unchecked"})
	public static String in(String field, Set<?> arrays) {
		@SuppressWarnings("rawtypes")
        List<?> arrayList = new ArrayList(arrays);
		return in(field, arrayList);
	}
	
	public static String in(String field, String value) {
		return field + " in(" + value + " )";
	}
	
	
}
