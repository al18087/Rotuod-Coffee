package la.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import la.bean.ProductBean;

public class HistoryDAO {
	
	// URL、ユーザ名、パスワードの準備
	private String url = "jdbc:postgresql:postgres";
	private String user = "user1";
	private String pass = "himitu";

	public HistoryDAO() throws DAOException {
		try {
			// JDBCドライバの登録
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DAOException("JDBCドライバの登録に失敗しました。");
		}
	}
	
	public List<List<ProductBean>> findHistory(String customerName) throws DAOException {
		
		// SQL文を作成
		String sql = "SELECT order_id FROM sale WHERE customer_name = ?";
		
		// saleテーブルからorder_idを取得し、それをsetに格納するためのもの
		Set<Integer> set = new TreeSet<Integer>();
		
		try (
				Connection con = DriverManager.getConnection(url, user, pass);
				PreparedStatement st = con.prepareStatement(sql);) {
			
			// プレースホルダの設定
			st.setString(1, customerName);
			
			try (ResultSet rs = st.executeQuery();) {
				while (rs.next()) {
					int orderId = rs.getInt("order_id");
					set.add(orderId);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
		
		/* sale_detailテーブルとproductテーブルからorderIdをもとにproductテーブルの
		   情報を取得*/
		List<List<ProductBean>> list = new ArrayList<List<ProductBean>>();
		for (Integer orderId : set) {
			sql = "SELECT p.product_id, p.product_name, p.price, sd.quantity "
					+ "FROM product p INNER JOIN sale_detail sd ON "
					+ "p.product_id = sd.product_id AND sd.order_id = ?";
			
			try (
					Connection con = DriverManager.getConnection(url, user, pass);
					PreparedStatement st = con.prepareStatement(sql);) {
				
				// プレースホルダの設定
				st.setInt(1, orderId);
				
				try (ResultSet rs = st.executeQuery();) {
					List<ProductBean> productList = new ArrayList<ProductBean>();
					while (rs.next()) {
						int productId = rs.getInt("product_id");
						String productName = rs.getString("product_name");
						int price = rs.getInt("price");
						int quantity = rs.getInt("quantity");
						ProductBean bean = new ProductBean(productId, productName, price, quantity);
						productList.add(bean);
					}
					list.add(productList);
					
				} catch (SQLException e) {
					e.printStackTrace();
					throw new DAOException("レコードの取得に失敗しました。");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}
		}
		
		return list;
	}
}
