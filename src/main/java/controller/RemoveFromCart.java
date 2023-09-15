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
import dto.Cart;
import dto.Customer;
import dto.CustomerFoodItem;

@WebServlet("/removefromcart")
public class RemoveFromCart extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Customer customer = (Customer) req.getSession().getAttribute("customer");
		if (customer == null) {
			resp.getWriter().print("<h1 style='color:red'>Invalid Session</h1>");
			req.getRequestDispatcher("Login.html").include(req, resp);
		} else {
			int id = Integer.parseInt(req.getParameter("id"));
			MyDao dao = new MyDao();
			AddFoodItem foodItem = dao.find(id);
			Cart cart = customer.getCart();
			if(cart==null)
			{
				resp.getWriter().print("<h1 style='color:red'>No Item in Cart</h1>");
				req.getRequestDispatcher("ViewCustomerMenu").include(req, resp);
			}
			else
			{
				List<CustomerFoodItem> list=cart.getFoodItems();
				if(list==null)
				{
					resp.getWriter().print("<h1 style='color:red'>No Item in Cart</h1>");
					req.getRequestDispatcher("ViewCustomerMenu").include(req, resp);
				}
				else
				{
					  CustomerFoodItem foodItem2 = null;
					for(CustomerFoodItem item:list)
					{
						if(item.getName().equals(foodItem.getName()))
						{
							 foodItem2 = item;
							 break;
						}
					}
					if(foodItem2==null)
					{
						resp.getWriter().print("<h1 style='color:red'>No Item in Cart</h1>");
						req.getRequestDispatcher("ViewCustomerMenu").include(req, resp);
					}
					else
					{
						if(foodItem2.getQuntity()>1)
						{
							//this set method locally set
							foodItem2.setQuntity(foodItem2.getQuntity()-1);
							foodItem2.setPrice(foodItem2.getPrice()-foodItem.getPrice());
							foodItem.setStock(foodItem.getStock()+1);
							dao.update(foodItem);
							dao.update(foodItem2);
						}
						else
						{
							list.remove(foodItem2);
							foodItem.setStock(foodItem.getStock()+1);
							dao.update(foodItem);
							dao.update(cart);
						}
						req.getSession().removeAttribute("customer");
						req.getSession().setAttribute("customer", dao.finds(customer.getId()));
						
						resp.getWriter().print("<h1 style='color:green'>Item Removed from Cart Successfullly</h1>");
						req.getRequestDispatcher("ViewCustomerMenu").include(req, resp);
					}
				}
			}
						
		}
	}
}
