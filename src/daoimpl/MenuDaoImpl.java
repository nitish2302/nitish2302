package daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.MenuDao;
import entities.MenuItems;

@Repository("menudao")
public class MenuDaoImpl implements MenuDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public MenuDaoImpl(){}
	public MenuDaoImpl(SessionFactory sessionFactory){
		this.sessionFactory=sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuItems> fetchMenuList() {
		
		List<MenuItems> menuList = new ArrayList<MenuItems>();	
		
		try{
			Session sess = sessionFactory.getCurrentSession();
			Query q = sess.createQuery("from MenuItems");
			menuList = q.list();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return menuList;
	}

}
