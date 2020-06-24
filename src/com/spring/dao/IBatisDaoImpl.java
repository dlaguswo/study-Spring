package com.spring.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

@Repository("dao")
public class IBatisDaoImpl implements CommonDAO {
	/*
	  @Repository : 저장소 컴포넌트(DAO 등).
	  @Service : 상태없는 서비스 컴포넌트.
	  @Autowired 어노테이션 : Spring에서 의존관계를 자동으로 설정
	  @Resource 어노테이션 : 의존하는 빈 객체를 전달 할 때 사용
	  
	  @Resource는 이름을 지정할 수 있으나 @Autowired는 이름을 지정할 수 없고 id 값과 일치해야한다.

      - Service에 있는 @Autowired는 @Repository("dao")에 등로된 dao와 변수명이 같아야 한다.
	  - @Autowired 어노테이션은 Spring에서 의존관계를 자동으로 설정
	*/

	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	// ******************************************************************
	// 데이터 추가
    public void insertData(String id, Object value) throws Exception {
		try {
			// sqlMapClientTemplate.getSqlMapClient().startTransaction();
			// sqlMapClientTemplate.getSqlMapClient().getCurrentConnection().setAutoCommit( false );
			sqlMapClientTemplate.insert(id, value);  // return Object
			// sqlMapClientTemplate.getSqlMapClient().getCurrentConnection().commit();
		} catch (Exception e) {
			System.out.println(e.toString());			
			throw e;
		} finally {
			// sqlMapClientTemplate.getSqlMapClient().getCurrentConnection().setAutoCommit( true );
			// sqlMapClientTemplate.getSqlMapClient().endTransaction();
		}    	
    }
    
	// ******************************************************************
	// 데이터 수정
    public int updateData(String id, Object value) throws Exception {
		int result = 0;
		
		try {
	   	    result = sqlMapClientTemplate.update(id, value);
		} catch (Exception e) {
			System.out.println(e.toString());
			
			throw e;
		}
		
		return result;
    }
    public int updateData(String id, Map<String, Object> map) throws Exception {
		int result = 0;
		
		try {
	   	    result = sqlMapClientTemplate.update(id, map);
		} catch (Exception e) {
			System.out.println(e.toString());
			
			throw e;
		}
		
		return result;
    }
    
	// ******************************************************************
	// 데이터 삭제
	public int deleteData(String id, Map<String, Object> map) throws Exception {
		int result = 0;

		try {
			result = sqlMapClientTemplate.delete(id, map);
		} catch (Exception e) {
			System.out.println(e.toString());
			
			throw e;
		}

		return result;
    }
	public int deleteData(String id, Object value) throws Exception {
		int result = 0;
		
		try {
			result = sqlMapClientTemplate.delete(id, value);
		} catch (Exception e) {
			System.out.println(e.toString());
			
			throw e;
		}

		return result;
    }
	public int deleteAll(String id) throws Exception {
		int result = 0;
		
		try {
	    	result = sqlMapClientTemplate.delete(id);
		} catch (Exception e) {
			System.out.println(e.toString());
			
			throw e;
		}

		return result;
    }

	// ******************************************************************
	// 레 코드 수 / 최대 값 구하기
	public int getIntValue(String id, Map<String, Object> map) throws Exception {
		int num = 0;
		num = ((Integer)sqlMapClientTemplate.queryForObject(id, map)).intValue();
		return num;
    }
	public int getIntValue(String id, Object value) throws Exception {
		int num = 0;
		num = ((Integer)sqlMapClientTemplate.queryForObject(id, value)).intValue();
		return num;
    }
	public int getIntValue(String id) throws Exception {
		int num = 0;
		num = ((Integer)sqlMapClientTemplate.queryForObject(id)).intValue();
		return num;
    }
	
	// ******************************************************************
	// 테이블의 레코드를 리스트에 저장
	@SuppressWarnings("unchecked")
	public List<Object> getListData(String id, Map<String, Object> map) throws Exception {
		List<Object> lists = (List<Object>) sqlMapClientTemplate.queryForList(id, map);	
		return lists;
	}
	@SuppressWarnings("unchecked")
	public List<Object> getListData(String id, Object value) throws Exception {
		List<Object> lists = (List<Object>) sqlMapClientTemplate.queryForList(id, value);	
		return lists;
	}
	@SuppressWarnings("unchecked")
	public List<Object> getListData(String id) throws Exception {
		List<Object> lists = (List<Object>) sqlMapClientTemplate.queryForList(id);	
		return lists;
	}
	
	// ******************************************************************
	// 해당 레코드 가져오기
	public Object getReadData(String id) throws Exception {
		return  sqlMapClientTemplate.queryForObject(id);
	}
	public Object getReadData(String id, Object value) throws Exception {
		return  sqlMapClientTemplate.queryForObject(id, value);
	}
	public Object getReadData(String id, Map<String, Object> map) throws Exception {
		return  sqlMapClientTemplate.queryForObject(id, map);
	}
}
