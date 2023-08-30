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

@WebServlet("/ViewMenu")
public class AdminViewMenu extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// logic to fetch data from database
		MyDao dao = new MyDao();
		List<AddFoodItem> items = dao.fetchAllFoodItem();

//		 //logic to display data on frontend
//		 resp.getWriter().print("<html><body><h1>Menu</h1>");
//		 resp.getWriter().print("<table border='1'>");
//			resp.getWriter().print(
//					"<tr><th>Name</th><th>Type</th><th>Price</th><th>Quantity</th><th>Edit</th><th>Delete</th></tr>");
//			for (AddFoodItem item : items) {
//				resp.getWriter().print(
//						"<tr><th>"+item.getName()+"</th><th>"+item.getType()+"</th><th>Price</th><th>"+item.getQuantity()+"</th><th><button>Edit</button></th><th><button>Delete</button></th></tr>");
//			}
//			resp.getWriter().print("</table></body></html>");

		// logic to carry data to frontend

		if (items.isEmpty()) {
			resp.getWriter().print("<h1 style='color:red'>No Items Found</h1> ");
			req.getRequestDispatcher("AdminHome.html").include(req, resp);
		} else {

			req.setAttribute("list", items);
			req.getRequestDispatcher("ViewMenu.jsp").include(req, resp);
		}
	}
}
