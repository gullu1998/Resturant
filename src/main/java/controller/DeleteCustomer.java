package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MyDao;
import dto.Customer;

@WebServlet("/deleteCustomer")
public class DeleteCustomer extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Http Session verification last phase of project this if else is used in the
		// concept of session tracking
		if (req.getSession().getAttribute("admin") == null) {
			resp.getWriter().print("<h1 style='color:red'>Invalid Session</h1>");
			req.getRequestDispatcher("Login.html").include(req, resp);
		} else {
			// getting id from the url
			int id = Integer.parseInt(req.getParameter("id"));
			MyDao dao = new MyDao();
			// Finding object because remove method accepts object
			Customer customer = dao.finds(id);
			dao.deleteCustomer(customer);
			resp.getWriter().print("<h1 style='color:blue'>Data Deleted Successfully</h1>");
			req.getRequestDispatcher("ViewCustomer").include(req, resp);
		}
	}

}
