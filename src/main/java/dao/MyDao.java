package dao;

import java.util.List;

import javax.naming.ldap.ManageReferralControl;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.mysql.cj.Query;

import dto.Customer;

public class MyDao {

	EntityManagerFactory e=Persistence.createEntityManagerFactory("dev");
	EntityManager m=e.createEntityManager();
	EntityTransaction t=m.getTransaction();
	
	//we using pass by reference here to access Customer class object
	public void save(Customer customer)
	{
		t.begin();
		m.persist(customer);//here we add object of Customer class thatsy we using pass by reference
		t.commit();
	}
	public Customer fetchByEmail(String email)
	{
       javax.persistence.Query query = m.createQuery("select x from Customer x where email=?1").setParameter(1,email);
	 //Customer is the table name created by hibernate and in jpql in place of * like in sql we use any variable to create query
	 //we can not concadinate so, ? acts like placeholder and parameter(String email) will stored in 1(is position) and write same in setParameter..
       List<Customer> list = query.getResultList();
       //getResultList() is used to ececute the query and gave result
       if(list.isEmpty()){
    	  return null; 
       }
       else
       {
    	   return list.get(0);
    	   //0 is int index position it will start checking from 0 and  check the email is already present or no
       }
	}
	public Customer fetchByMobile(long mobile)
	{
		javax.persistence.Query query = m.createQuery("select mob from Customer mob where mobile=?5").setParameter(5, mobile);
		List<Customer> list = query.getResultList();
		if(list.isEmpty())
		{
			return null;
		}
		else
		{
			return list.get(0);
		}
	}
}