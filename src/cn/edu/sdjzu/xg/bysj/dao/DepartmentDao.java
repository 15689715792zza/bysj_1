package cn.edu.sdjzu.xg.bysj.dao;


import cn.edu.sdjzu.xg.bysj.domain.Degree;
import cn.edu.sdjzu.xg.bysj.domain.Department;
import cn.edu.sdjzu.xg.bysj.domain.School;
import cn.edu.sdjzu.xg.bysj.service.DepartmentService;
import cn.edu.sdjzu.xg.bysj.service.SchoolService;
import util.JdbcHelper;
import util.ShowTable;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;

public final class DepartmentDao {
	private static Collection<Department> departments;


	private static DepartmentDao departmentDao=new DepartmentDao();
	private DepartmentDao(){}

	public static DepartmentDao getInstance(){
		return departmentDao;
	}


	public Collection<Department> findAll() throws SQLException {
		Collection<Department> departments = new TreeSet<Department>();
		Connection connection = JdbcHelper.getConn();
		Statement stmt = connection.createStatement();
		ResultSet resultSet = stmt.executeQuery("select * from department");
		while(resultSet.next()){

			Department department = new Department(
					resultSet.getInt("id"),
					resultSet.getString("description"),
					resultSet.getString("no"),
					resultSet.getString("remarks"),
					SchoolService.getInstance().find(resultSet.getInt("school_id")));
			departments.add(department);
		}
		return departments;
	}

	public Collection<Department> findAllBySchool(int schoolId) throws SQLException {
		Collection<Department> departments = new TreeSet<Department>();
		Connection connection = JdbcHelper.getConn();
		String findAllBySchool_sql = "SELECT * FROM department where school_id = ?";
		PreparedStatement pstmt = connection.prepareStatement(findAllBySchool_sql);
		pstmt.setInt(1,schoolId);
		ResultSet resultSet = pstmt.executeQuery();
		while(resultSet.next()){
			Department department = new Department(
					resultSet.getInt("id"),
					resultSet.getString("description"),
					resultSet.getString("no"),
					resultSet.getString("remarks"),
					SchoolService.getInstance().find(resultSet.getInt("school_id")));
			departments.add(department);
		}
		return departments;
	}

	public Department find(Integer id) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String updateDegree_sql = "SELECT * FROM department where id = ?";
		PreparedStatement pstmt = connection.prepareStatement(updateDegree_sql);
		pstmt.setInt(1,id);
		ResultSet resultSet = pstmt.executeQuery();
		resultSet.next();

		Department department = new Department(resultSet.getInt("id"),
				resultSet.getString("description"),
				resultSet.getString("no"),
				resultSet.getString("remarks"),
				SchoolDao.getInstance().find(resultSet.getInt("school_id")));
		return department;
	}

	public void update(Department department) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String updateDegree_sql = "UPDATE department SET description = ?,no = ?,remarks = ? where id = ?";
		PreparedStatement pstmt = connection.prepareStatement(updateDegree_sql);
		pstmt.setString(1,department.getDescription());
		pstmt.setString(2,department.getNo());
		pstmt.setString(3,department.getRemarks());
		pstmt.setInt(4,department.getId());
		pstmt.executeUpdate();
		JdbcHelper.close(pstmt,connection);
	}

	public void add(Department department) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String addDegree_sql = "INSERT INTO department(description,no,remarks,school_id) VALUES" +
				" (?,?,?,?)";
		PreparedStatement pstmt = connection.prepareStatement(addDegree_sql);

		pstmt.setString(1, department.getDescription());
		pstmt.setString(2,department.getNo());
		pstmt.setString(3,department.getRemarks());
		pstmt.setInt(4,department.getSchool().getId());

		int affectedRowNum = pstmt.executeUpdate();
		System.out.println("添加了 " + affectedRowNum +" 行记录");

		JdbcHelper.close(pstmt,connection);
	}

	public void delete(Integer id) throws SQLException {
		Department department = this.find(id);
		this.delete(department);
	}

	public void delete(Department department) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String addDegree_sql = "DELETE FROM department WHERE id = ?";
		PreparedStatement pstmt = connection.prepareStatement(addDegree_sql);
		pstmt.setInt(1,department.getId());
		int affectedRowNum = pstmt.executeUpdate();
		System.out.println("删除了 " + affectedRowNum +" 行记录");

		JdbcHelper.close(pstmt,connection);
	}

	public static void main(String[] args) throws SQLException {
		Department departmentToChange = DepartmentService.getInstance().find(1);
		departmentToChange.setDescription("管理");
		departmentDao.update(departmentToChange);
		Department departmentChanged = DepartmentService.getInstance().find(1);
		System.out.println(departmentChanged.getDescription());

	}
}

