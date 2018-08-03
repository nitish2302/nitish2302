package entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TableOrders")
@EntityListeners(value = { EntityListener.class })
public class Order extends BaseEntity {

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
	
	@Column(name = "quantity")
	private String quantity;
	
	@ManyToOne(cascade = CascadeType.ALL, optional = true)
	private TableInfo tableInfo;
	
	@Column(name = "total")
	private String total;
	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
	

	public TableInfo getTableInfo() {
		return tableInfo;
	}

	public void setTableInfo(TableInfo tableInfo) {
		this.tableInfo = tableInfo;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
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

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

}
