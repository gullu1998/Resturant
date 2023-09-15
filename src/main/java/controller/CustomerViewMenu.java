package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MyDao;
import dto.AddFoodItem;
import dto.Customer;
import dto.CustomerFoodItem;

@WebServlet("/ViewCustomerMenu")
public class CustomerViewMenu extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Http Session verification last phase of project this if else is used in the
		// concept of session tracking
		if (req.getSession().getAttribute("customer") == null) {
			resp.getWriter().print("<h1 style='color:red'>Invalid Session</h1>");
			req.getRequestDispatcher("Login.html").include(req, resp);
		} else {

			// logic to fetch data from database
			MyDao dao = new MyDao();
			List<AddFoodItem> items = dao.fetchAllFoodItem();



			// logic to carry data to frontend

			if (items.isEmpty()) {
				resp.getWriter().print("<h1 style='color:red'>No Items Found</h1> ");
				req.getRequestDispatcher("CustomerHome.html").include(req, resp);
			} else {
				Customer customer=(Customer)req.getSession().getAttribute("customer");
				List<CustomerFoodItem> cartItems=null;
				if(customer.getCart()!=null && customer.getCart().getFoodItems()!=null)
				{
					cartItems=customer.getCart().getFoodItems();
				}
                req.setAttribute("cartitems", cartItems);
				req.setAttribute("list", items);
				req.getRequestDispatcher("CustomerViewMenu.jsp").include(req, resp);
			}
		}
	}

}
