package com.zdy.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdy.crud.bean.Employee;
import com.zdy.crud.bean.EmployeeExample;
import com.zdy.crud.bean.EmployeeExample.Criteria;
import com.zdy.crud.dao.EmployeeMapper;


@Service
public class EmployeeService {
	
	
	@Autowired
	EmployeeMapper employeeMapper;
	
	/**
	 * count 返回的是int型的数据，表示数据库中是否有用户名一样的数据
	 * @param empName
	 * @return
	 */
	public boolean	checkUser(String empName){
		EmployeeExample example	= new EmployeeExample();
		Criteria criter = example.createCriteria();
		criter.andEmpNameEqualTo(empName);
		long count = employeeMapper.countByExample(example);
		return count==0;
	}
	
	
	
	public List<Employee> getAll(){
		
		return employeeMapper.selectByExampleWithDept(null);
	}
	
	
	
	
	public void saveEmp(Employee employee){
		employeeMapper.insertSelective(employee);
	}
	
	//按照id插叙员工信息
	public Employee getEmp(Integer id){
		Employee employee = employeeMapper.selectByPrimaryKey(id);
		return employee;
	}
	
	//员工更新
	public void updateEmp(Employee employee){
		employeeMapper.updateByPrimaryKeySelective(employee);
	}
	
	//员工删除 
	public void deleteEmp(Integer id){
		employeeMapper.deleteByPrimaryKey(id);
	}
	
	public void deleteBatch(List<Integer> ids){
		EmployeeExample example = new EmployeeExample();
		Criteria criteria = example.createCriteria();
		criteria.andEmpIdIn(ids);
		employeeMapper.deleteByExample(example);
	}
	
}
