package la.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import la.bean.CustomerBean;
import la.bean.LoginBean;

public class LoginDAO {
	// URL、ユーザ名、パスワードの準備
	private String url = "jdbc:postgresql:postgres";
	private String user = "user1";
	private String pass = "himitu";

	public LoginDAO() throws DAOException {
		try {
			// JDBCドライバの登録
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DAOException("JDBCドライバの登録に失敗しました。");
		}
	}
	
	public LoginBean findLoginInfo(String loginUser, String loginPass) throws DAOException {
		
		// SQL文を作成
		String sql = "SELECT * FROM login WHERE login_user = ? AND password = ?";
		
		try (
				Connection con = DriverManager.getConnection(url, user, pass);
				PreparedStatement st = con.prepareStatement(sql);) {
			
			// プレースホルダの設定
			st.setString(1, loginUser);
			st.setString(2, loginPass);
			
			try (ResultSet rs = st.executeQuery();) {
				
				if (rs.next()) {
					String user = rs.getString("login_user");
					String pass = rs.getString("password");
					LoginBean bean = new LoginBean(user, pass);
					return bean;
				} else {
					return null;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}
	
	public String register(CustomerBean bean) throws DAOException {
		
		// 会員番号の取得
		int customerId = 0;
		String sql = "SELECT nextval('customer_customer_id_seq')";
		try (
				Connection con = DriverManager.getConnection(url, user, pass);
				PreparedStatement st = con.prepareStatement(sql);
				ResultSet rs = st.executeQuery();) {
			
			if (rs.next()) {
				customerId = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		}
		
		// customerテーブルに追加
		sql = "INSERT INTO customer VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (
				Connection con = DriverManager.getConnection(url, user, pass);
				PreparedStatement st = con.prepareStatement(sql);) {
			
			// プレースホルダを設定
			st.setInt(1, customerId);
			st.setString(2, bean.getName());
			st.setInt(3, bean.getYear());
			st.setString(4, bean.getTel());
			st.setString(5, bean.getPostCode());
			st.setString(6, bean.getAddress());
			st.setString(7, bean.getUser());
			st.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		}
		
		// loginテーブルに追加
		sql = "INSERT INTO login VALUES (?, ?)";
		try (
				Connection con = DriverManager.getConnection(url, user, pass);
				PreparedStatement st = con.prepareStatement(sql);) {
			
			// プレースホルダの設定
			st.setString(1, bean.getUser());
			st.setString(2, bean.getPassword());
			st.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		}
		
		return bean.getUser();
	}
	
	public CustomerBean findCustomer(String loginUser, String loginPassword) throws DAOException {
		
		// SQL文を作成
		String sql = "SELECT * FROM customer WHERE login_user = ?";
		
		try (
				Connection con = DriverManager.getConnection(url, user, pass);
				PreparedStatement st = con.prepareStatement(sql);) {
			
			// プレースホルダの設定
			st.setString(1, loginUser);
			
			try (ResultSet rs = st.executeQuery();) {
				if (rs.next()) {
					String name = rs.getString("name");
					int year = rs.getInt("birthday_year");
					String tel = rs.getString("tel");
					String postCode = rs.getString("post_code");
					String address = rs.getString("address");
					String user = rs.getString("login_user");
					CustomerBean bean = new CustomerBean(name, year, tel, 
							postCode, address, user, loginPassword);
					return bean;
				} else {
					return null;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}
}
