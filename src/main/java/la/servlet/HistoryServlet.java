package la.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import la.bean.LoginBean;
import la.bean.ProductBean;
import la.dao.DAOException;
import la.dao.HistoryDAO;

/**
 * Servlet implementation class HistoryServlet
 */
@WebServlet("/HistoryServlet")
public class HistoryServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(true);
		LoginBean login = (LoginBean)session.getAttribute("login");
		
		// ログインしていない場合は不正
		if (login == null) {
			request.setAttribute("message", "ログインしてください。");
			gotoPage(request, response, "/errInternal.jsp");
			return;
		}
		
		try {
			String action = request.getParameter("action");
			if (action.equals("history")) {
				String customerName = request.getParameter("name");
				HistoryDAO dao = new HistoryDAO();
				
				// ユーザが購入した商品履歴を取得
				List<List<ProductBean>> historyList = dao.findHistory(customerName);
				
				request.setAttribute("history", historyList);
				gotoPage(request, response, "/history.jsp");
			}
			
		} catch (DAOException e) {
			e.printStackTrace();
			request.setAttribute("message", "正しく操作してください。");
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
