package dto;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


import lombok.Data;

@Entity
@Data
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	//cascade = CascadeType.ALL=whatever we do for parents it will happen for child
	@OneToMany(cascade = CascadeType.ALL)
      java.util.List<CustomerFoodItem> foodItems;

}
