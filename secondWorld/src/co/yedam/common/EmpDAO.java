package co.yedam.common;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpDAO extends DAO {
	
	// 데이터가 있거나, 에러 => false, 없는게 확실 => true.
	public boolean checkId(String id) {
		connect();
		String sql = "select * from empl_demo where employee_id = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			if(rs.next()) {
				return false;
			} else {
				return true; // 데이터가 없는게 확실할 경우.
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}		
		return false;
	}
	
	public List<Employee> getEmpList() {
		connect();
		List<Employee> list = new ArrayList<>();
		String sql = "select * from empl_demo order by 1 desc";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Employee emp = new Employee();
				emp.setEmployeeId(rs.getInt("employee_id"));
				emp.setLastName(rs.getString("last_name"));
				emp.setEmail(rs.getString("email"));
				emp.setHireDate(rs.getString("hire_date").substring(0, 10));
				emp.setJobId(rs.getString("job_id"));
				
				list.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}
	
	public boolean insertEmp(Employee emp) {
		connect();
		String sql= "insert into empl_demo (employee_id, last_name, email, job_id, hire_date)"
				+ "values(?, ?, ?, ?, ?)";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, emp.getEmployeeId());
			psmt.setString(2, emp.getLastName());
			psmt.setString(3, emp.getEmail());
			psmt.setString(4, emp.getJobId());
			psmt.setString(5, emp.getHireDate());
			int r = psmt.executeUpdate();
			System.out.println(r + "입력됨");
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnect();
		}
	}
	
	public void updateEmp(String id, String phone, String salary) {
		connect();
		String sql = "update empl_demo set phone_number=?, salary=? where employee_id=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, phone);
			psmt.setString(2, salary);
			psmt.setString(3, id);
			int r = psmt.executeUpdate();
			System.out.println(r + "건 수정됨");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	public int deleteEmployee(String empId) {
		connect();
		String sql = "delete from empl_demo where employee_id = " + empId;
		try {
			stmt = conn.createStatement();
			int r = stmt.executeUpdate(sql);
			System.out.println(r + "건 삭제됨.");
			return Integer.parseInt(empId);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return -1;
	}


}
