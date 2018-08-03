package serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import dao.MenuDao;
import dao.TableInfoDao;
import entities.MenuItems;
import entities.Order;
import entities.TableInfo;
import enums.TableStatus;
import service.MenuJpaService;

@Service("menuservice")
public class Menuserviceimpl implements MenuJpaService {

	@Autowired
	@Qualifier("menudao")
	private MenuDao menuDao;

	@Autowired
	@Qualifier("tabledao")
	private TableInfoDao tableInfoDao;

	public TableInfoDao getTableInfoDao() {
		return tableInfoDao;
	}

	public void setTableInfoDao(TableInfoDao tableInfoDao) {
		this.tableInfoDao = tableInfoDao;
	}

	public MenuDao getMenuDao() {
		return menuDao;
	}

	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	public Menuserviceimpl() {
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public List<MenuItems> fetchMenuItems() {
		List<MenuItems> menuList = new ArrayList<MenuItems>();
		try {

			menuList = menuDao.fetchMenuList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return menuList;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void addOrder(TableInfo tableInfo) {

		TableInfo tableDetailsFromDb = tableInfoDao.fetchTableInfo(tableInfo.getTableNumber());

		// Insert new active table if table size is zero
		if (tableDetailsFromDb == null) {
			tableInfo.setStatus(TableStatus.A);
			List<Order> ordersFetched = tableInfo.getOrders();

			for (Order order : ordersFetched) {

				order.setTableInfo(tableInfo);

			}

			tableInfoDao.addOrderToTable(tableInfo);
		} else {

			// update orders of existing table
			List<Order> ordersFetched = tableDetailsFromDb.getOrders();

			List<Order> ordersFetchedFromUi = tableInfo.getOrders();

			for (Order order : ordersFetchedFromUi) {

				order.setTableInfo(tableDetailsFromDb);

			}

			ordersFetched.addAll(ordersFetchedFromUi);
			tableInfoDao.addOrderToTable(tableDetailsFromDb);
		}

	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void removeOrder(TableInfo tableInfo) {

		TableInfo tableDetails = tableInfoDao.fetchTableInfo(tableInfo.getTableNumber());
		List<Order> ordersFetched = tableDetails.getOrders();
		
		Order orderToBeRemoved = tableInfo.getOrders().get(0);
		for (Order order : ordersFetched) {

			if (orderToBeRemoved.getItemCode().equalsIgnoreCase(order.getItemCode())) {

				tableInfoDao.deleteOrder(tableDetails, orderToBeRemoved.getItemCode());
			}
		}
		
		tableInfoDao.addOrderToTable(tableDetails);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void modifOrderQuantity(TableInfo tableInfo) {
		
		TableInfo tableDetails = tableInfoDao.fetchTableInfo(tableInfo.getTableNumber());
		Order order = tableInfo.getOrders().get(0);
		tableInfoDao.modifOrderQuantity(tableDetails,order);
		
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public TableInfo fetchTableInfo(String tableNumber) {
		// TODO Auto-generated method stub
		
		return tableInfoDao.fetchTableInfo(tableNumber);
		
		
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void closeTable(TableInfo tableInfo) {
		
		tableInfoDao.closeTable(tableInfo);
		
	}

}
