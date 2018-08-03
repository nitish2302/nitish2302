package dao;

import java.util.List;

import entities.MenuItems;

public interface MenuDao {
	
	public List<MenuItems> fetchMenuList();

}
