package com.zdy.crud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdy.crud.bean.Employee;
import com.zdy.crud.bean.Msg;
import com.zdy.crud.service.EmployeeService;
/**
 * c处理员工crud数据

 * @author dy
 *
 */
@Controller
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	
	
	//员工删除
	@ResponseBody
	@RequestMapping(value="/emp/{ids}",method=RequestMethod.DELETE)
	public Msg deleteEmp(@PathVariable("ids")String ids){
		if(ids.contains("-")){
			//xiugai可能要改为String
			List<Integer> del_ids = new ArrayList<Integer>();
			String[] str_ids = ids.split("-");
			//组装id的集合
			for(String string : str_ids){
				del_ids.add(Integer.parseInt(string));
			}
			
			employeeService.deleteBatch(del_ids);
		}else{
			Integer id = Integer.parseInt(ids);
			employeeService.deleteEmp(id);
		}
		
		return Msg.success();
	}
	
	//g更新员工数据
	@ResponseBody
	@RequestMapping(value="emp/{empId}",method=RequestMethod.PUT)
	public Msg saveEmp(Employee employee){
		employeeService.updateEmp(employee);
		return Msg.success();	
	}
	
	
	//按照id查询员工
	@RequestMapping(value="/emp/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Msg  getEmp(@PathVariable("id")Integer id){
		
		Employee employee = employeeService.getEmp(id);
		return Msg.success().add("emp", employee);
	}
	
	
	//员工新增姓名校验
	@ResponseBody
	@RequestMapping("/checkuser")
	public Msg checkuser(@RequestParam("empName")String empName){
		//先判断用户名是否是合法的表达式;
		String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
		if(!empName.matches(regx)){
			return Msg.fail().add("va_msg", "用户名必须是6-16位数字和字母的组合或者2-5位中文");
		}
				
		boolean b = employeeService.checkUser(empName);
		if(b){
			return Msg.success();
		}else{
			return Msg.fail().add("va_msg", "用户名不可用");
		}
	}
	
	
	//员工保存
	//@Valid JSR303bean类的属性校验
	@RequestMapping(value="/emp",method = RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@Valid Employee employee,BindingResult result){
		if(result.hasErrors()){
			//校验失败，应该返回失败，在模态框中显示校验失败的错误信息
			Map<String, Object> map = new HashMap<String,Object>();
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				System.out.println("错误的字段名："+fieldError.getField());
				System.out.println("错误信息："+fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Msg.fail().add("errorFields", map);
		}else{
			employeeService.saveEmp(employee);
			return Msg.success();
		}
	}
		
	@RequestMapping("/emps")
	@ResponseBody
	public Msg getEmpsWithJson(@RequestParam(value="pn", defaultValue="1")Integer pn){
			PageHelper.startPage(pn,5);
		
		//后面跟着的插叙就是为分页查询 
			List<Employee> emps = employeeService.getAll();
		//封装了详细的分页信息，包括查询出来的数据 ,5表示连续显示5页
			PageInfo page = new PageInfo(emps,5); 
			return Msg.success().add("pageInfo",page); 
	}

//	@RequestMapping("/emps")
//		public String getEmps(@RequestParam(value="pn", defaultValue="1")Integer pn, Model model){
//		
//			PageHelper.startPage(pn,5);
//			
//		//后面跟着的插叙就是为分页查询
//			List<Employee> emps = employeeService.getAll();
//		//封装了详细的分页信息，包括查询出来的数据 ,5表示连续显示5条
//			PageInfo page = new PageInfo(emps,5);
//			
//			model.addAttribute("pageInfo",page);
//			
//			
//			return "list";
//		}
	}
