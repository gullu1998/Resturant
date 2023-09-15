package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MyDao;
import dto.Customer;

@WebServlet("/ViewCustomer")
public class AdminViewCustomer extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Http Session verification
		if (req.getSession().getAttribute("admin") == null) {
			resp.getWriter().print("<h1 style='color:red'>Invalid Session</h1>");
			req.getRequestDispatcher("Login.html").include(req, resp);
		} else {

			// logic to fetch data from database
			MyDao dao = new MyDao();
			List<Customer> customers = dao.fetchAllCustomer();

			// logic to carry or display data on frontend
			if (customers.isEmpty()) {
				resp.getWriter().print("<h1 style='color:red'>No Customer Found</h1> ");
				req.getRequestDispatcher("AdminHome.html").include(req, resp);
			} else {

				req.setAttribute("list", customers);
				req.getRequestDispatcher("ViewCustomer.jsp").include(req, resp);
			}
		}
	}

}
