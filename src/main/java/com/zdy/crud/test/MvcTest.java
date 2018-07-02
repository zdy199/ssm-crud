package com.zdy.crud.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.pagehelper.PageInfo;
import com.zdy.crud.bean.Employee;

/**
 * 使用springMvc测试模块测试请求功能
 * @author dy
 *
 */


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"classpath:applicationContext.xml","file:src/main/webapp/WEB-INF/dispatcherServlet-servlet.xml"})
public class MvcTest {
	MockMvc mockMvc;
	@Autowired
	WebApplicationContext context;
	
	@Before
	public void initMockMvc(){
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		
	}
	
	@Test
	public void testPage() throws Exception{
		//模拟请求返回值
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "5")).andReturn();
		//请求成功以后，请求域中有pageinfo，取出pageinfo校验
		MockHttpServletRequest request = result.getRequest();
		PageInfo pi  = (PageInfo) request.getAttribute("pageInfo");
		System.out.println("asd"+pi.getPageNum());
		System.out.println("asd"+pi.getPages());
		System.out.println("asd"+pi.getTotal());
		int[] nums = pi.getNavigatepageNums();
		for(int i:nums){
			System.out.println(" " +i);
		}

		List<Employee> list  = pi.getList();
		for(Employee employee:list){
			System.out.println("ID" + employee.getEmpId()+"Name"+employee.getEmpName());
		}
		
	}
	
}
