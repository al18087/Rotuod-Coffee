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
import la.bean.CustomerBean;
import la.dao.DAOException;
import la.dao.OrderDAO;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 注文処理はセッションとcartが存在していることが大前提
		HttpSession session = request.getSession(false);
		if (session == null) {
			request.setAttribute("message", "セッションが切れています。もう一度トップページより操作してください。");
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
		
		try {
			String action = request.getParameter("action");
			if (action.equals("order")) {
				OrderDAO dao = new OrderDAO();
				CustomerBean customer = (CustomerBean)session.getAttribute("customer");
				int orderId = dao.saveOrder(cart, customer);
				
				// セッション情報をクリアする
				session.removeAttribute("cart");
				session.removeAttribute("customer");
				
				// 伝票番号をクライアントに送信する
				request.setAttribute("orderId", Integer.valueOf(orderId));
				gotoPage(request, response, "/order.jsp");
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
