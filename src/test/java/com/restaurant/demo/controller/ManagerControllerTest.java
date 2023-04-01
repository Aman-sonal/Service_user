package com.restaurant.demo.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.demo.model.Employee;
import com.restaurant.demo.service.UserService;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
class ManagerControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	UserService userService;

	@MockBean
	UserController userController;

	@MockBean
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private Employee emp;

	@BeforeEach
	public void setup() {
		emp = new Employee();
		emp.setEmpId(1);
		emp.setEmpName("abc");
		emp.setEmpRole("Manager");
		emp.setIsAvailable(1);
		emp.setStoreId(12345);
	}

	@Test
	void testFetchEmployee() throws Exception {
		List<Employee> empList = new ArrayList<>();
		empList.add(emp);
		Mockito.when(userService.fetchEmployee()).thenReturn(empList);

		mockMvc.perform(get("/manager").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", is(empList.size())));
	}

	@Test
	void testFetcEmployeeById() throws Exception {
		Mockito.when(userService.saveEmployee(emp)).thenReturn(emp);
		Mockito.when(userService.fetchEmployeeById(1L)).thenReturn(emp);

		mockMvc.perform(get("/manager/get-employee/{id}", "1").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.empId", is(1)));
	}

	@Test
	void testAddEmployee() throws Exception {
		Mockito.when(userService.saveEmployee(emp)).thenReturn(emp);

		mockMvc.perform(post("/manager/add-employee").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(new ObjectMapper().writeValueAsString(emp)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
//				.andExpect(jsonPath("$.empId", is(emp.getEmpId())))
//				.andExpect(jsonPath("$.empRole", is(emp.getEmpRole())));
	}

	@Test
	void testUpdateEmployee() throws Exception {
		Mockito.when(userService.saveEmployee(emp)).thenReturn(emp);
		emp.setEmpName("tuntun");
		Mockito.when(userService.updateEmployee(emp, 1)).thenReturn(emp);

		mockMvc.perform(put("/manager/update-employee-managerAccess/{id}", "1").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").content(new ObjectMapper().writeValueAsString(emp))
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.empId", is(emp.getEmpId())))
				.andExpect(jsonPath("$.empName", is(emp.getEmpName())))
				.andExpect(jsonPath("$.empRole", is(emp.getEmpRole())))
				.andExpect(jsonPath("$.IsAvailable", is(emp.getIsAvailable())))
				.andExpect(jsonPath("$.storeId", is(emp.getStoreId())));
	}
}
