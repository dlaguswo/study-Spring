package com.spring.dao;

import java.util.List;
import java.util.Map;

public interface CommonDAO {
	public void insertData(String id, Object value) throws Exception;
	
	public int updateData(String id, Object pData) throws Exception;
	public int updateData(String id, Map<String, Object> map) throws Exception;
	
	public int deleteData(String id, Map<String, Object> map) throws Exception;
	public int deleteData(String id, Object value) throws Exception;
	public int deleteAll(String id) throws Exception;
	
	public int getIntValue(String id, Map<String, Object> map) throws Exception;
	public int getIntValue(String id, Object value) throws Exception;
	public int getIntValue(String id) throws Exception;
	
	public List<Object> getListData(String id, Map<String, Object> map) throws Exception;
	public List<Object> getListData(String id, Object value) throws Exception;
	public List<Object> getListData(String id) throws Exception;
	
	public Object getReadData(String id) throws Exception;
	public Object getReadData(String id, Object value) throws Exception;
	public Object getReadData(String id, Map<String, Object> map) throws Exception;
}
