package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import entities.BaseEntity;

@Entity
@Table(name = "MENU_ITEMS")
@EntityListeners(value = { EntityListener.class })

public class MenuItems extends BaseEntity {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Column(name = "item_code")
	private String itemCode;


	@Column(name = "item_name")
	private String itemName;

	@Column(name = "price")
	private String price;

	@Column(name = "type")
	private String type;

	public String getItemName() {
		return itemName;
	}
	
	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
