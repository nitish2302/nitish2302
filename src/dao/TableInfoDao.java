package dao;

import java.util.List;

import entities.Order;
import entities.TableInfo;

public interface TableInfoDao {
	
	public void addOrderToTable(TableInfo tableInfo);
	public TableInfo fetchTableInfo(String tableNumber);
	public void deleteOrder(TableInfo tableInfo, String orderId);
	public void modifOrderQuantity(TableInfo tableInfo, Order order);
	public void closeTable(TableInfo tableInfo);

}
