package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import config.ServerInfo;

public class MenuDaoImpl implements MenuDao {

private DataSource ds;
	
	private static MenuDaoImpl dao = new MenuDaoImpl();
	
	private MenuDaoImpl() {
		try {
			Class.forName(ServerInfo.DRIVER_NAME);
			System.out.println("드라이버 로딩 성공");
		} catch (ClassNotFoundException e) {
			System.out.print("드라이버 로딩 실패 ");
			System.out.println(e);
		}
		//ds = DataSourceManager.getInstance().getConnection();
	}
	
	public static MenuDaoImpl getInstance() {
		return dao;
	}	

	public Connection getConnection() throws SQLException {
		System.out.println("디비연결...");
		
		return DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASS);
		//return ds.getConnection();
	}
	
	public void closeAll(PreparedStatement ps, Connection conn) throws SQLException{
		if(ps!=null) ps.close();		
		if(conn != null) conn.close();
		return;
	}
	
	public void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) throws SQLException{		
		if(rs != null)	rs.close();
		closeAll(ps, conn);		
		return;
	}	
	

	@Override
	public ArrayList<Menu> getAllPizza() throws SQLException {
		
		ArrayList<Menu> list = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			list = new ArrayList<Menu>();
			conn = getConnection();
			String query = "SELECT * FROM menu WHERE MENU_TYPE=?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, 1);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new Menu(
						rs.getString(1), 
						rs.getInt(2), 
						rs.getString(3), 
						rs.getInt(4), 
						rs.getString(5)
						));
			}
			
		} finally {
			closeAll(rs, ps, conn);
		}
		
		return list;
	}

	@Override
	public ArrayList<Menu> getAllSide() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Menu> getAllTopping() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Menu getPizza(String menuId) throws SQLException {
		
		
		
		return null;
	}

	public static void main(String[] args) throws SQLException {
		
		MenuDaoImpl test = new MenuDaoImpl();

		for(Menu m : test.getAllPizza())
			System.out.println(m);
	}
}
