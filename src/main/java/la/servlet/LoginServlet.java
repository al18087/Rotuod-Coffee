package la.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import la.bean.CustomerBean;
import la.bean.LoginBean;
import la.dao.DAOException;
import la.dao.LoginDAO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		try {
			if (action.equals("isLogin")) {
				HttpSession session = request.getSession(true);
				LoginBean login = (LoginBean)session.getAttribute("login");
				CustomerBean customer = (CustomerBean)session.getAttribute("customer");
				
				// ログインしていないとき
				if (login == null) {
					gotoPage(request, response, "/login.jsp");
					return;
				}
				
				// ログインしているとき
				LoginDAO dao = new LoginDAO();
				login = dao.findLoginInfo(login.getUser(), login.getPassword());
				System.out.println(login.getUser());
				System.out.println(login.getPassword());
				session.setAttribute("login", login);
				session.setAttribute("customer", customer);
				gotoPage(request, response, "/ShowItemServlet");
			}
			
			// ログイン(ユーザ名とパスワードを入力したとき)
			else if (action.equals("login")) {
				String user = request.getParameter("user");
				String password = request.getParameter("password");
				LoginDAO dao = new LoginDAO();
				LoginBean login = dao.findLoginInfo(user, password);
				
				// ログイン成功
				if (login != null) {
					HttpSession session = request.getSession(true);
					session.setAttribute("login", login);
					CustomerBean customer = dao.findCustomer(user, password);
					session.setAttribute("customer", customer);
					gotoPage(request, response, "/ShowItemServlet");
				} 
				// ログイン失敗
				else {
					request.setAttribute("message", "ログインに失敗しました。");
					gotoPage(request, response, "/errInternal.jsp");
				}
			}
			
			// 新規登録
			else if (action.equals("register")) {
				String name = request.getParameter("name");
				int year = Integer.parseInt(request.getParameter("year"));
				String tel = request.getParameter("tel");
				String postCode = request.getParameter("post_code");
				String address = request.getParameter("address");
				String user = request.getParameter("user");
				String password = request.getParameter("password");
				CustomerBean bean = new CustomerBean(name, year, tel, postCode,
						address, user, password);
				
				LoginDAO dao = new LoginDAO();
				
				// customerテーブル、loginテーブルに新規登録をする
				user = dao.register(bean);
				request.setAttribute("message", user);
				gotoPage(request, response, "/registerFinish.jsp");
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
