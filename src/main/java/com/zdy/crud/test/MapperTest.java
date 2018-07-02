package com.zdy.crud.test;

import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zdy.crud.bean.Department;
import com.zdy.crud.bean.Employee;
import com.zdy.crud.dao.DepartmentMapper;
import com.zdy.crud.dao.EmployeeMapper;


/**
 * ʹ��spring����
 * @author dy
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class MapperTest {
	
	@Autowired
	DepartmentMapper departmentMapper;
	
	@Autowired
	EmployeeMapper employeeMapper;
	
	@Autowired
	SqlSession sqlSession;
	
	@Test
	public void testCRUD(){
//		//1����spring����
//		ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
//		//2������ȡmapper
//		DepartmentMapper bean = ioc.getBean(DepartmentMapper.class);
		
		System.out.println(departmentMapper);
		 
		//departmentMapper.insertSelective(new Department(1,"test"));
		//departmentMapper.insertSelective(new Department(2,"devlop"));
		//生成员工数据
		
		//employeeMapper.insertSelective(new Employee(null,"aa","M","123@qq.com",1));
		
		EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
		for(int i= 0; i<1000; i++){
			
			String uid = UUID.randomUUID().toString().substring(0, 5)+i;
			mapper.insertSelective(new Employee(null,uid,"M",uid+"@qq.com",1));
			
		}
		System.out.println("123");
	}
}
