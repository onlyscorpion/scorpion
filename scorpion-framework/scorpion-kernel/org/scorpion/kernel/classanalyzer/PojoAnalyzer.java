package org.scorpion.kernel.classanalyzer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.scorpion.api.configuration.PojoEntityInfo;
import org.scorpion.api.configuration.PojoEntityInfo.PoProperty;
import org.scorpion.api.configuration.SQLConfig.SQLProperty;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.IAnnotationAnalyzerListener;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.common.annotation.Column;
import org.scorpion.common.annotation.Entity;
import org.scorpion.common.context.SystemContext;

/**
 * 自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>
 * com.taiji.tscp.common
 * <p>
 * File: AbsTscpFactory.java create time:2015-5-8下午07:57:37
 * </p>
 * <p>
 * Title: abstract factory class
 * </p>
 * <p>
 * Description: if developer want to create a component. the developer must
 * extends the abstract
 * </p>
 * <p>
 * class ATscpComponet. the ATscpComponent exist life cycle. developer can
 * override
 * </p>
 * <p>
 * the initialization method or service method or destroy method to handle
 * themselves business
 * </p>
 * <p>
 * but we don't suggest the developer do that
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015 taiji.com.cn
 * </p>
 * <p>
 * Company: taiji.com.cn
 * </p>
 * <p>
 * module: common abstract class
 * </p>
 * 
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class PojoAnalyzer implements IAnnotationAnalyzerListener {

	
	
	/**
	 * @param field
	 * 
	 * @param poproperty
	 * 
	 * @throws TscpBaseException
	 */
	private void FeildHandler(Class<?> clazz, Field field, PoProperty poproperty)throws TscpBaseException {

		Column column = field.getAnnotation(Column.class);
		if (column == null)
			return;

		if ("".equals(column.columnName())) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < field.getName().length(); i++) {
				if (!Character.isUpperCase(field.getName().charAt(i))) {
					sb.append(field.getName().charAt(i));
					continue;
				}
				sb.append("_"+ Character.toLowerCase(field.getName().charAt(i)));
			}
			poproperty.getFieldSequence().add(sb.toString());
			poproperty.getSetAttributes().put(sb.toString(),getMethod(clazz, field, false));
			poproperty.getGetAttributes().put(sb.toString(),getMethod(clazz, field, true));
		}else{
			poproperty.getFieldSequence().add(column.columnName());
			if(column.isPrimaryKey())
				poproperty.getPrimaryKey().add(column.columnName());
			if(column.nullable())
				poproperty.getPrimaryKey().add(column.columnName());
			if(!"".equals(column.dataDefault()))
				poproperty.getDataDefault().put(column.columnName(), column.dataDefault());
			poproperty.getSetAttributes().put(column.columnName(),getMethod(clazz, field, false));
			poproperty.getGetAttributes().put(column.columnName(),getMethod(clazz, field, true));
		}
	}

	/**
	 * 
	 * @param poproperty
	 * 
	 * @throws TscpBaseException
	 */
	protected void poConvertSQL(PoProperty poproperty) throws TscpBaseException {

		StringBuilder sb = new StringBuilder();
		sb.append("insert into " + poproperty.getTableName() + " (");
		StringBuilder value = new StringBuilder();
		value.append(" values(");

		try {
			for (String field : poproperty.getFieldSequence()) {
				sb.append(field + ",");
				value.append("?,");
			}
		} catch (Exception e) {
			throw new TscpBaseException("TSCP-9858:非法参数异常", e);
		}

		SQLProperty sqlpropertyInsert = ((SystemContext) SystemContext.getApplicationContext()).getSqlconfig().new SQLProperty();
		sqlpropertyInsert.setKey(poproperty.getClazz().getName());
		sqlpropertyInsert.setVaule(sb.substring(0, sb.length() - 1) + ") "+ value.substring(0, value.length() - 1) + ")");
		((SystemContext) SystemContext.getApplicationContext()).getSqlconfig().getPoInsertMap().put(poproperty.getClazz().getName(), sqlpropertyInsert);

		SQLProperty sqlpropertyDel = ((SystemContext) SystemContext.getApplicationContext()).getSqlconfig().new SQLProperty();
		sqlpropertyDel.setKey(poproperty.getClazz().getName());
		sqlpropertyDel.setVaule("delete from " + poproperty.getTableName());
		((SystemContext) SystemContext.getApplicationContext()).getSqlconfig().getPoDelMap().put(poproperty.getClazz().getName(), sqlpropertyDel);

		SQLProperty sqlpropertyUpdate = ((SystemContext) SystemContext.getApplicationContext()).getSqlconfig().new SQLProperty();
		sqlpropertyUpdate.setKey(poproperty.getClazz().getName());
		sqlpropertyUpdate.setVaule("update " + poproperty.getTableName());
		((SystemContext) SystemContext.getApplicationContext()).getSqlconfig().getPoUpdateMap().put(poproperty.getClazz().getName(), sqlpropertyUpdate);

	}

	/**
	 * @param clazz
	 * 
	 * @param fieldName
	 * 
	 * @param isGet
	 * 
	 * @return
	 * 
	 * @throws SecurityException
	 * 
	 * @throws NoSuchMethodException
	 * 
	 * @throws TscpBaseException
	 */
	public Method getMethod(Class<?> clazz, Field field, boolean isGet)throws TscpBaseException {

		Method method = null;

		if (isGet) {

			try {
				method = clazz.getMethod("get"+ Character.toUpperCase(field.getName().charAt(0))+ field.getName().substring(1, field.getName().length()));
			} catch (SecurityException e) {
				throw new TscpBaseException("TSCP-9076:存在安全侵犯", e);
			} catch (NoSuchMethodException e) {
				throw new TscpBaseException("TSCP-9064:PO信息不完整,PO["+ clazz.getName()+ "]不存在GET方法["+ "get"+ Character.toUpperCase(field.getName().charAt(0))+ field.getName().substring(1, field.getName().length()) + "]",e);
			}

			if (method != null)
				return method;
			throw new TscpBaseException("TSCP-9064:PO信息不完整,PO["+ clazz.getName() + "]不存在GET方法");

		} else {

			try {
				method = clazz.getMethod("set"+ Character.toUpperCase(field.getName().charAt(0))+ field.getName().substring(1,field.getName().length()),field.getType());
			} catch (SecurityException e) {
				throw new TscpBaseException("TSCP-9076:存在安全侵犯", e);
			} catch (NoSuchMethodException e) {
				throw new TscpBaseException("TSCP-9064:PO信息不完整,PO["+ clazz.getName()+ "]不存在SET方法["+ "set"+ Character.toUpperCase(field.getName().charAt(0))+ field.getName().substring(1, field.getName().length()) + "]",e);
			}

			if (method != null)
				return method;

			throw new TscpBaseException("TSCP-9064:PO信息不完整,PO["+ clazz.getName() + "]不存在SET方法");
		}

	}

	
	@Override
	public void analyse(Class<?> clazz, String jarName)throws TscpBaseException {

		Entity entity = clazz.getAnnotation(Entity.class);

		if (entity == null)
			return;

		PojoEntityInfo pojo = SystemContext.getApplicationContext().getSystemPojoInformation();
		PoProperty poproperty = pojo.new PoProperty();
		poproperty.setJarName(jarName);

		try {
			for (Field field : clazz.getDeclaredFields()) {
				FeildHandler(clazz, field, poproperty);
			}

			poproperty.setClazz(clazz);
			poproperty.setTableName(entity.tableName());
			pojo.getPropertyMap().put(clazz.getName(), poproperty);
			poConvertSQL(poproperty);
			poproperty.setValid(true);
		} catch (Throwable e) {
			poproperty.setValid(false);
			throw new TscpBaseException("TSCP-9086:加载PO[" + clazz.getName()+ "]出错！", e);
		}
		PlatformLogger.info("在类[" + clazz.getName() + "]扫描到POJO信息,["+ clazz.getName() + "]");
	}

}
