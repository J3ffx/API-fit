package org.uha.ensisa.fanfan.APIfit;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

import org.uha.ensisa.fanfan.APIfit.model.Challenge;
import org.uha.ensisa.fanfan.APIfit.model.Suggestion;
import org.uha.ensisa.fanfan.APIfit.model.User;

public class DAO implements ServletContextListener {

	@PersistenceUnit(unitName = "APIfit")
	private static EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("APIfit");

	@PersistenceContext(unitName = "APIfit")
	private static EntityManager em;

	@Transactional(dontRollbackOn = Exception.class)
	private static EntityManager getEntityManager() {
		em = emfactory.createEntityManager();
		return em;
	}

	public static List<Challenge> getChals() {
		EntityManager entitymanager = getEntityManager();
		List<Challenge> chals = entitymanager.createQuery("from Challenge c", Challenge.class).getResultList();
		entitymanager.close();
		return chals;
	}

	public static List<User> getUsers() {
		EntityManager entitymanager = getEntityManager();
		List<User> users = entitymanager.createQuery("from User u", User.class).getResultList();
		entitymanager.close();
		return users;
	}

	public static List<Suggestion> getSugs() {
		EntityManager entitymanager = getEntityManager();
		List<Suggestion> sugs = entitymanager.createQuery("from Suggestion s", Suggestion.class).getResultList();
		entitymanager.close();
		return sugs;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		emfactory.close();
	}

	public static void addChal(Challenge challenge)
			throws NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException,
			HeuristicMixedException, HeuristicRollbackException, NamingException {
		UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
		transaction.begin();
		EntityManager entitymanager = getEntityManager();
		entitymanager.persist(challenge);
		transaction.commit();
		entitymanager.close();
	}

	public static Challenge getChal(Integer chalId) {
		for (Challenge chal : getChals()) {
			if (chal.getCid() == chalId) {
				return chal;
			}
		}
		return null;
	}

	public static void setPlayers(int id, int players)
			throws NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException,
			HeuristicMixedException, HeuristicRollbackException, NamingException {
		UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
		transaction.begin();
		EntityManager entitymanager = getEntityManager();
		Challenge challenge = getChal(id);
		challenge.setPlayers(players);
		transaction.commit();
		entitymanager.close();
	}

	public static User getUser(String username) {
		for (User user : DAO.getUsers()) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}

	public static User getUser(int id) {
		for (User user : getUsers()) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}

	public static void setPassword(int id, String password)
			throws NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException,
			HeuristicMixedException, HeuristicRollbackException, NamingException {
		UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
		transaction.begin();
		EntityManager entitymanager = getEntityManager();
		User user = getUser(id);
		user.setPassword(password);
		transaction.commit();
		entitymanager.close();

	}

	public static void remove(int id) throws NotSupportedException, SystemException, NamingException, SecurityException,
			IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
		transaction.begin();
		EntityManager entitymanager = getEntityManager();
		User user = getUser(id);
		if (!entitymanager.contains(user)) {
			user = entitymanager.merge(user);
		}
		entitymanager.remove(user);
		transaction.commit();
		entitymanager.close();

	}

	public static void addUser(User user)
			throws SecurityException, IllegalStateException, RollbackException, HeuristicMixedException,
			HeuristicRollbackException, SystemException, NotSupportedException, NamingException {
		UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
		transaction.begin();
		EntityManager entitymanager = getEntityManager();
		entitymanager.persist(user);
		transaction.commit();
		entitymanager.close();

	}

	public static void remove(String username)
			throws NotSupportedException, SystemException, NamingException, SecurityException, IllegalStateException,
			RollbackException, HeuristicMixedException, HeuristicRollbackException {
		UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
		transaction.begin();
		EntityManager entitymanager = getEntityManager();
		User user = getUser(username);
		if (!entitymanager.contains(user)) {
			user = entitymanager.merge(user);
		}
		entitymanager.remove(user);
		transaction.commit();
		entitymanager.close();

	}

	public static void setPassword(String username, String password)
			throws NamingException, NotSupportedException, SystemException, SecurityException, IllegalStateException,
			RollbackException, HeuristicMixedException, HeuristicRollbackException {
		UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
		transaction.begin();
		EntityManager entitymanager = getEntityManager();
		User user = getUser(username);
		user.setPassword(password);
		transaction.commit();
		entitymanager.close();

	}

	public static void subChal(int chalId, String username)
			throws NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException,
			HeuristicMixedException, HeuristicRollbackException, NamingException {
		UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
		transaction.begin();
		EntityManager entitymanager = getEntityManager();
		User user = getUser(username);
		ArrayList<Integer> chals = user.getChallenges();
		chals.add(chalId);
		user.setChallenges(chals);
		transaction.commit();
		entitymanager.close();

	}

	public static void addSug(Suggestion suggestion)
			throws NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException,
			HeuristicMixedException, HeuristicRollbackException, NamingException {
		UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
		transaction.begin();
		EntityManager entitymanager = getEntityManager();
		entitymanager.persist(suggestion);
		transaction.commit();
		entitymanager.close();

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
