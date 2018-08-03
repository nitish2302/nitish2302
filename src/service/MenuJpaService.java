package service;

import java.util.List;

import entities.MenuItems;
import entities.TableInfo;

public interface MenuJpaService {
	
	public List<MenuItems> fetchMenuItems();

	public void addOrder(TableInfo tableInfo);

	public void removeOrder(TableInfo tableInfo);

	public void modifOrderQuantity(TableInfo tableInfo);
	
	public TableInfo fetchTableInfo(String tableNumber);

	public void closeTable(TableInfo tableInfo);

}
