package la.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import la.bean.CartBean;
import la.bean.CustomerBean;
import la.bean.ProductBean;

public class OrderDAO {
	
	// URL、ユーザ名、パスワードの準備
	private String url = "jdbc:postgresql:postgres";
	private String user = "user1";
	private String pass = "himitu";

	public OrderDAO() throws DAOException {
		try {
			// JDBCドライバの登録
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DAOException("JDBCドライバの登録に失敗しました。");
		}
	}
	
	public int saveOrder(CartBean cart, CustomerBean customer) throws DAOException {
		
		// 伝票番号の取得
		int orderId = 0;
		String sql = "SELECT nextval('sale_order_id_seq')";
		try (
				Connection con = DriverManager.getConnection(url, user, pass);
				PreparedStatement st = con.prepareStatement(sql);
				ResultSet rs = st.executeQuery();) {
			
			if (rs.next()) {
				orderId = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		}
		
		// saleテーブルに追加
		sql = "INSERT INTO sale VALUES(?, ?, ?, ?)";
		try (
				Connection con = DriverManager.getConnection(url, user, pass);
				PreparedStatement st = con.prepareStatement(sql);) {
			
			// プレースホルダの設定
			st.setInt(1, orderId);
			Date today = new Date(System.currentTimeMillis());
			st.setDate(2, today);
			st.setString(3, customer.getName());
			st.setInt(4, cart.getTotal());
			
			// SQLを実行
			st.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		}
		
		// sale_detailテーブルに追加
		sql = "INSERT INTO sale_detail VALUES (?, ?, ?)";
		try (
				Connection con = DriverManager.getConnection(url, user, pass);
				PreparedStatement st = con.prepareStatement(sql);) {
			
			// プレースホルダの設定
			List<ProductBean> list = cart.getProducts();
			for (ProductBean p : list) {
				st.setInt(1, orderId);
				st.setInt(2, p.getProductId());
				st.setInt(3, p.getQuantity());
				st.executeUpdate();
			}
			return orderId;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		}
	}
}
