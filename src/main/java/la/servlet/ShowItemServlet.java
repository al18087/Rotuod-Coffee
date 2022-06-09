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

import la.bean.CategoryBean;
import la.bean.ProductBean;
import la.dao.DAOException;
import la.dao.ItemDAO;

/**
 * Servlet implementation class ShowItemServlet
 */
@WebServlet("/ShowItemServlet")
public class ShowItemServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String action = request.getParameter("action");
			if (action == null || action.length() == 0 || action.equals("login") || action.equals("isLogin")) {
				HttpSession session = request.getSession(false);
				
				// ログインしていないとき
				if (session == null) {
					request.setAttribute("message", "不正です！ログインしてください。");
					gotoPage(request, response, "/errInternal.jsp");
					return;
				}
				// session.getAttribute("login");
				gotoPage(request, response, "/top.jsp");
			} 
			
			// listは「list.jsp」に表示する
			else if (action.equals("list")) {
				int id = Integer.parseInt(request.getParameter("id"));
				ItemDAO dao = new ItemDAO();
				List<ProductBean> list = dao.findAllProduct(id);
				
				// Listをリクエストスコープに入れてJSPへフォワードする
				request.setAttribute("items", list);
				gotoPage(request, response, "/list.jsp");
				
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
	
	public void init() throws ServletException {
		try {
			
			// カテゴリ一覧は最初にアプリケーションスコープへ保存する
			ItemDAO dao = new ItemDAO();
			List<CategoryBean> list = dao.findAllCategory();
			getServletContext().setAttribute("categories", list);
			
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServletException();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
