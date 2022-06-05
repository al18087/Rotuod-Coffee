package la.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import la.bean.CartBean;
import la.bean.ProductBean;
import la.dao.DAOException;
import la.dao.ItemDAO;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			String action = request.getParameter("action");
			
			if (action.equals("add")) {
				int productId = Integer.parseInt(request.getParameter("product_id"));
				int quantity = Integer.parseInt(request.getParameter("quantity"));
				HttpSession session = request.getSession(true);
				CartBean cart = (CartBean)session.getAttribute("cart");
				
				// 初めてのクライアントの場合
				if (cart == null) {
					cart = new CartBean();
					session.setAttribute("cart", cart);
				}
				
				// 商品コードの商品を取得する
				ItemDAO dao = new ItemDAO();
				ProductBean bean = dao.findByPrimaryKey(productId);
				
				// カートに追加する
				cart.addCart(bean, quantity);
				gotoPage(request, response, "/cart.jsp");
			}
			
			// deleteは削除
			else if (action.equals("delete")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					request.setAttribute("message", "セッションが切れています。もう一度トップページに戻ってください。");
					gotoPage(request, response, "/errInternal.jsp");
					return;
				}
				
				CartBean cart = (CartBean)session.getAttribute("cart");
				// カートがない
				if (cart == null) {
					request.setAttribute("message", "正しく操作してください。");
					gotoPage(request, response, "/errInternal.jsp");
					return;
				}
				
				int productId = Integer.parseInt(request.getParameter("item_product_id"));
				cart.deleteCart(productId);
				gotoPage(request, response, "/cart.jsp");
			}
		} catch (DAOException e) {
			e.printStackTrace();
			request.setAttribute("message", "内部エラーが発生しました。");
			gotoPage(request, response, "/errInternal.jsp");
		}
	}
	
	private void gotoPage(HttpServletRequest request, HttpServletResponse response, String page)
			throws ServletException, IOException {

		RequestDispatcher rd = request.getRequestDispatcher(page);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
