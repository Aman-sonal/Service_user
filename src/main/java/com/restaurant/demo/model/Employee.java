package com.restaurant.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;

@Entity
@Table(name = "res_employee")
@Builder
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long empId;
	@Column(name = "emp_name")
	private String empName;
	@Column(name = "emp_role")
	private String empRole;
	@Column(name = "is_available")
	private int isAvailable;
	@Column(name = "store_id")
	private long storeId;

	public Employee(long empId, String empName, String empRole, int isAvailable, long storeId) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.empRole = empRole;
		this.isAvailable = isAvailable;
		this.storeId = storeId;
	}

	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getEmpId() {
		return empId;
	}

	public void setEmpId(long empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpRole() {
		return empRole;
	}

	public void setEmpRole(String empRole) {
		this.empRole = empRole;
	}

	public int getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(int isAvailable) {
		this.isAvailable = isAvailable;
	}

	public long getStoreId() {
		return storeId;
	}

	public void setStoreId(long storeId) {
		this.storeId = storeId;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj == this)
			return true;
		if (!(obj instanceof Employee))
			return false;
		Employee emp = (Employee) obj;
		return emp.getEmpName() == this.getEmpName() && emp.getEmpRole() == this.getEmpRole()
				&& emp.getIsAvailable() == this.getIsAvailable() && emp.getStoreId() == this.storeId;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int res=17;
		res=(int) (31*res+this.getEmpId());
		res=31*res+ (this.getEmpName()!=null?this.getEmpName().hashCode():0);
		return res;
	}
	
	
}
