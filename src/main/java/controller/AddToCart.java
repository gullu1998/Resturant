package controller;

import java.io.IOException;
import java.util.ArrayList;

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

@WebServlet("/addtocart")
public class AddToCart extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Http Session verification last phase of project this if else is used in the
		// concept of session tracking

		// it is the another way if we use old ways then in else block again we have to
		// write same below line in else block
		Customer customer = (Customer) req.getSession().getAttribute("customer");
		if (customer == null) {
			resp.getWriter().print("<h1 style='color:red'>Invalid Session</h1>");
			req.getRequestDispatcher("Login.html").include(req, resp);
		} else {
			int id = Integer.parseInt(req.getParameter("id"));
			MyDao dao = new MyDao();
			AddFoodItem foodItem = dao.find(id);
			//Checking if stock is there
			if(foodItem.getStock()>0)
			{
			Cart cart = customer.getCart();
			if (cart == null) {
				// if the variable cart is null then we will enters into the if block and
				// create an object and again store their address into cart
				cart = new Cart();
				customer.setCart(cart);
			}
			// if food item is not available or null then it wiil create a empty list and by
			// using this
			// it will not throw null pointer exception
			if (cart.getFoodItems() == null) {
				cart.setFoodItems(new ArrayList<CustomerFoodItem>());
			}
			boolean flag = true;
			for (CustomerFoodItem item : cart.getFoodItems()) {
				if (item.getName().equals(foodItem.getName())) {

					item.setQuntity(item.getQuntity() + 1);
					item.setPrice(item.getPrice() + foodItem.getPrice());
//					dao.update(item);
					flag = false;
					break;

				}
			}
			if(flag)
			{
				CustomerFoodItem item=new CustomerFoodItem();
				item.setName(foodItem.getName());
				item.setPicture(foodItem.getPicture());
				item.setPrice(foodItem.getPrice());
				item.setQuntity(1);
				item.setType(foodItem.getType());
				cart.getFoodItems().add(item);
			}
//			dao.update(cart);
//			customer.setCart(cart);
			dao.update(customer);
			req.getSession().removeAttribute("customer");
			req.getSession().setAttribute("customer", dao.finds(customer.getId()));
			
			//logic to reduce stock
			foodItem.setStock(foodItem.getStock()-1);
			dao.update(foodItem);
			
			
			resp.getWriter().print("<h1 style='color:green'>Added Successfully</h1>");
			req.setAttribute("cartitems", cart.getFoodItems());
			req.getRequestDispatcher("ViewCustomerMenu").include(req, resp);
			}
			else
			{
				resp.getWriter().print("<h1 style='color:red'>Out of Stock</h1>");
				req.getRequestDispatcher("ViewCustomerMenu").include(req, resp);
			}
		
		}	
	}

}
