package com.sayem.daolayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.sayem.entity.Complaint;

@Service
public class ComplaintDao {

	@Autowired
	JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	public int delete(int id) {
		String sql = "delete from Users where id=" + id + "";
		return template.update(sql);
	}

	public Complaint getEmpById(int id) {
		String sql = "select * from Users where Id=?";
		return template.queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper<Complaint>(Complaint.class));
	}

	public List<Complaint> getEmployees() {
		return template.query("select * from request", new RowMapper<Complaint>() {
			public Complaint mapRow(ResultSet rs, int row) throws SQLException {
				Complaint com = new Complaint();
				com.setRequestId(rs.getInt(1));
				com.setRequestDate(rs.getDate(2));
				com.setRequestBy(rs.getString(3));
				com.setDescription(rs.getString(4));
				com.setAttachment(rs.getString(5));
				com.setDepartment(rs.getString(6));
				com.setStatus(rs.getString(7));
				com.setPriority(rs.getString(8));
				com.setAssignedTo(rs.getString(9));
				return com;
			}
		});
	}

	public List<Complaint> getMyRequests(String username) {
//		System.out.println(username);
		return template.query("select requestId,requestDate,description,attachment,department,status,priority"
				+ " from request where requestBy = '" + username + "'", new RowMapper<Complaint>() {
					public Complaint mapRow(ResultSet rs, int row) throws SQLException {
						Complaint com = new Complaint();
						com.setRequestId(rs.getInt(1));
						com.setRequestDate(rs.getDate(2));
						com.setDescription(rs.getString(3));
						com.setAttachment(rs.getString(4));
						com.setDepartment(rs.getString(5));
						com.setStatus(rs.getString(6));
						com.setPriority(rs.getString(7));
						return com;
					}
				});
	}

	public List<Complaint> assigned(String name) {
		// TODO Auto-generated method stub
		return template.query("select * from request where assignedTo = '"+ name+"'", new RowMapper<Complaint>() {
			public Complaint mapRow(ResultSet rs, int row) throws SQLException {
				Complaint com = new Complaint();
				com.setRequestId(rs.getInt(1));
				com.setRequestDate(rs.getDate(2));
				com.setRequestBy(rs.getString(3));
				com.setDescription(rs.getString(4));
				com.setAttachment(rs.getString(5));
				com.setDepartment(rs.getString(6));
				com.setStatus(rs.getString(7));
				com.setPriority(rs.getString(8));
				return com;
			}
		});
	}

	public void save(Complaint com) {

		String sql = "insert into request(requestBy,description,attachment,department,priority) values(?,?,?,?,?)";
		
		template.update(sql, new Object[] { com.getRequestBy(), com.getDescription(), com.getAttachment(),
				com.getDepartment(), com.getPriority() });

		
	}

	public int changeStatusOpen(int requestId) {
		// TODO Auto-generated method stub
		String sql = "update request set status='Open' where requestId=" + requestId + "";
		return template.update(sql);
	}

	public int changeStatusClose(int requestId) {
		// TODO Auto-generated method stub
		String sql = "update request set status='Closed' where requestId=" + requestId + "";
		return template.update(sql);
	}

	public void assignManager(Complaint com) {
		// TODO Auto-generated method stub
		String sql = "update request set assignedTo = '"+ com.getAssignedTo() +"' where requestId=" + com.getRequestId() + "";
		 template.update(sql);
	}

	public List<Complaint> getManagers() {
		// TODO Auto-generated method stub
		return template.query("select username from authorities where authority= 'ROLE_MANAGER' ", new RowMapper<Complaint>() {
			public Complaint mapRow(ResultSet rs, int row) throws SQLException {
				Complaint e = new Complaint();
				e.setAssignedTo(rs.getString(1));
				return e;
			}
		});
	}

}
