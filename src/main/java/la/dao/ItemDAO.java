package la.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import la.bean.CategoryBean;
import la.bean.ProductBean;

public class ItemDAO {
	
	// URL、ユーザ名、パスワードの準備
	private String url = "jdbc:postgresql:postgres";
	private String user = "user1";
	private String pass = "himitu";
	
	public ItemDAO() throws DAOException {
		try {
			// JDBCドライバの登録
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DAOException("JDBCドライバの登録に失敗しました。");
		}
	}
	
	public List<CategoryBean> findAllCategory() throws DAOException {
		
		// SQL文を作成
		String sql = "SELECT * FROM category ORDER BY category_id";
		
		try (
				Connection con = DriverManager.getConnection(url, user, pass);
				PreparedStatement st = con.prepareStatement(sql);
				ResultSet rs = st.executeQuery();) {
			
			// 結果の取得および表示
			List<CategoryBean> list = new ArrayList<CategoryBean>();
			while (rs.next()) {
				int id = rs.getInt("category_id");
				String name = rs.getString("category_name");
				CategoryBean bean = new CategoryBean(id, name);
				list.add(bean);
			}
			
			// カテゴリ一覧を返す
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}
	
	public List<ProductBean> findAllProduct(int id) throws DAOException {
		
		// SQL文を作成
		String sql = "SELECT * FROM product WHERE category_id = ? ORDER BY product_id";
		
		try (
				Connection con = DriverManager.getConnection(url, user, pass);
				PreparedStatement st = con.prepareStatement(sql);) {
			
			// プレースホルダの設定
			st.setInt(1, id);
			
			try (ResultSet rs = st.executeQuery();) {
				
				// 結果の取得および表示
				List<ProductBean> list = new ArrayList<ProductBean>();
				while (rs.next()) {
					int productId = rs.getInt("product_id");
					String name = rs.getString("product_name");
					int price = rs.getInt("price");
					ProductBean bean = new ProductBean(productId, name, price);
					list.add(bean);
				}
				
				// カテゴリーごとの商品一覧を返す
				return list;
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}
	
	public ProductBean findByPrimaryKey(int id) throws DAOException {
		
		// SQL文を作成
		String sql = "SELECT * FROM product WHERE product_id = ?";
		
		try (
				Connection con = DriverManager.getConnection(url, user, pass);
				PreparedStatement st = con.prepareStatement(sql);) {
			
			// プレースホルダの設定
			st.setInt(1, id);
			
			try (ResultSet rs = st.executeQuery();) {
				
				// 結果の取得および表示
				if (rs.next()) {
					int productId = rs.getInt("product_id");
					String productName = rs.getString("product_name");
					int price = rs.getInt("price");
					ProductBean bean = new ProductBean(productId, productName, price);
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
