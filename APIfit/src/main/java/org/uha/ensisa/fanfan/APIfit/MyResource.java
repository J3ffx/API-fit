
package org.uha.ensisa.fanfan.APIfit;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.uha.ensisa.fanfan.APIfit.model.Challenge;
import org.uha.ensisa.fanfan.APIfit.model.Suggestion;
import org.uha.ensisa.fanfan.APIfit.model.User;

/**
 * Root resource (exposed at "/" path)
 */
@Path("/")
public class MyResource {

	public MyResource() {
	}

	/*************************** UTILITY *************************************/

	@SuppressWarnings("rawtypes")
	public String arrayToJson(List list) {
		String result = "{";
		for (Object obj : list) {
			result += obj.toString();
		}
		result += "}";
		return result;
	}

	public User getUser(String username) {
		for (User user : DAO.getUsers()) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}

	public User getUser(int id) {
		for (User user : DAO.getUsers()) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}

	public boolean existUser(String username) {
		for (User user : DAO.getUsers()) {
			if (user.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}

	public Challenge getChal(Integer chalId) {
		for (Challenge chal : DAO.getChals()) {
			if (chal.getCid() == chalId) {
				return chal;
			}
		}
		return null;
	}

	public boolean existChal(Integer chalId) {
		for (Challenge chal : DAO.getChals()) {
			if (chal.getCid() == chalId) {
				return true;
			}
		}
		return false;
	}

	public Suggestion getSug(int sugId) {
		for (Suggestion sug : DAO.getSugs()) {
			if (sug.getId() == sugId) {
				return sug;
			}
		}
		return null;
	}

	public boolean existSug(int sugId) {
		for (Suggestion sug : DAO.getSugs()) {
			if (sug.getId() == sugId) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Challenge> chalIdsToChals(ArrayList<Integer> cids) {
		ArrayList<Challenge> cs = new ArrayList<Challenge>();
		for (Integer cid : cids) {
			for (Challenge chal : DAO.getChals()) {
				if (chal.getCid() == cid)
					cs.add(chal);
			}
		}
		return cs;
	}

	/**************************** PUBLIC **************************************/

	/**
	 * Home page
	 * 
	 * @return an example of route
	 * @throws SecurityException
	 * @throws IllegalStateException
	 * @throws NotSupportedException
	 * @throws SystemException
	 * @throws RollbackException
	 * @throws HeuristicMixedException
	 * @throws HeuristicRollbackException
	 * @throws NamingException
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getHome() {
		return "try APIfit/challenges";
	}

	/**
	 * Get all challenges
	 * 
	 * @return all available challenges as an application/json response
	 */
	@GET
	@Path("/challenges/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getChallenges() {
		return arrayToJson(DAO.getChals());
	}

	/**
	 * Get a specific challenge
	 * 
	 * @param id (challenge id)
	 * 
	 * @return the challenge as an application/json object
	 */
	@GET
	@Path("/challenges/{id}/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getChallenges(@PathParam("id") int id) {
		return getChal(id).toString();
	}

	/**
	 * Store an username and a password to create an account
	 * 
	 * @param request  (context request for session handling)
	 * @param username (username query parameter)
	 * @param password (password query parameter)
	 * 
	 *                 Check if username in db
	 * 
	 *                 Add session attribute "username" with username value
	 * 
	 *                 Add username and password to db
	 * 
	 * @return a message as text/plain to confirm sign up
	 * @throws NamingException
	 * @throws NotSupportedException
	 * @throws SystemException
	 * @throws HeuristicRollbackException
	 * @throws HeuristicMixedException
	 * @throws RollbackException
	 * @throws IllegalStateException
	 * @throws SecurityException
	 */
	@POST
	@Path("/signup/")
	@Produces(MediaType.TEXT_PLAIN)
	public String signUp(@Context HttpServletRequest request, @QueryParam("username") String username,
			@QueryParam("password") String password)
			throws SecurityException, IllegalStateException, RollbackException, HeuristicMixedException,
			HeuristicRollbackException, SystemException, NotSupportedException, NamingException {
		if (existUser(username))
			return "username already taken";
		HttpSession session = request.getSession(true);
		session.setAttribute("username", username);
		List<User> users = DAO.getUsers();
		DAO.addUser(new User(users.size(), username, password, false, null));
		return "successfully registered and connected as : " + username + ", " + password;
	}

	/**
	 * Check if username and password are stored to sign in
	 * 
	 * @param request  (context request for session handling)
	 * @param username (username query parameter)
	 * @param password (password query parameter)
	 * 
	 *                 Check if username and password in db
	 * 
	 *                 Add session attribute "username" with username value
	 * 
	 * @return a message as text/plain to confirm sign in
	 */
	@PUT
	@Path("/signin/")
	@Produces(MediaType.TEXT_PLAIN)
	public String signIn(@Context HttpServletRequest request, @QueryParam("username") String username,
			@QueryParam("password") String password) {
		if (existUser(username)) {
			User user = getUser(username);
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				HttpSession session = request.getSession(true);
				session.setAttribute("username", username);
				return "successfully connected as : " + username + ", " + password;
			}
		}
		return "wrong username or password";
	}

	/*************************** JOUEUR **************************************/
	/**
	 * Get profile of the connected user
	 * 
	 * @param request (context request for session handling)
	 * 
	 *                Get session attribute "username"
	 * 
	 * @return user profile as an application/json object
	 */
	@GET
	@Path("/profile/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getProfile(@Context HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		if (username == null)
			return "you're not signed in";
		if (existUser(username))
			return getUser(username).toString();
		return "error, no profile found";
	}

	/**
	 * Disconnect the connected user
	 * 
	 * @param request (context request for session handling)
	 * 
	 *                Remove session attribute "username"
	 * 
	 * @return a message as text/plain to confirm sign out
	 */
	@PUT
	@Path("/signout/")
	@Produces(MediaType.TEXT_PLAIN)
	public String signOUt(@Context HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		if (username == null)
			return "you're not signed in";
		session.removeAttribute("username");
		return "successfully disconnected";
	}

	/**
	 * Remove profile of connected user
	 * 
	 * @param request (context request for session handling)
	 * 
	 *                Remove session attribute "username"
	 * 
	 *                Delete user data on db
	 * 
	 * @return a message as text/plain to confirm removing the profile
	 * @throws HeuristicRollbackException
	 * @throws HeuristicMixedException
	 * @throws RollbackException
	 * @throws NamingException
	 * @throws SystemException
	 * @throws NotSupportedException
	 * @throws IllegalStateException
	 * @throws SecurityException
	 */
	@DELETE
	@Path("/profile/")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteProfile(@Context HttpServletRequest request)
			throws SecurityException, IllegalStateException, NotSupportedException, SystemException, NamingException,
			RollbackException, HeuristicMixedException, HeuristicRollbackException {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		if (username == null)
			return "you're not signed in";
		session.removeAttribute("username");
		if (existUser(username))
			DAO.remove(username);
		return "successfully removed profile";
	}

	/**
	 * Modification of profile informations
	 * 
	 * @param request  (context request for session handling)
	 * @param username (username query parameter)
	 * @param password (password query parameter)
	 * 
	 *                 Change profile data in db (here only password)
	 * 
	 * @return a message as text/plain to confirm updating the profile
	 * @throws HeuristicRollbackException
	 * @throws HeuristicMixedException
	 * @throws RollbackException
	 * @throws SystemException
	 * @throws NotSupportedException
	 * @throws NamingException
	 * @throws IllegalStateException
	 * @throws SecurityException
	 */
	@PUT
	@Path("/profile/")
	@Produces(MediaType.TEXT_PLAIN)
	public String updateProfile(@Context HttpServletRequest request, @QueryParam("username") String username,
			@QueryParam("password") String password)
			throws SecurityException, IllegalStateException, NamingException, NotSupportedException, SystemException,
			RollbackException, HeuristicMixedException, HeuristicRollbackException {
		HttpSession session = request.getSession(true);
		if (session.getAttribute("username") == null)
			return "you're not signed in";
		if (existUser(username))
			DAO.setPassword(username, password);
		return "successfully updated : username = " + username + ", password = " + password;
	}

	/**
	 * Subscription to a challenge
	 * 
	 * @param request (context request for session handling)
	 * @param chalId  (id of challenge to subscribe to)
	 * 
	 *                Add challenge to user challenge list
	 * 
	 * @return subscribed challenges as an application/json object
	 * @throws NamingException
	 * @throws HeuristicRollbackException
	 * @throws HeuristicMixedException
	 * @throws RollbackException
	 * @throws SystemException
	 * @throws NotSupportedException
	 * @throws IllegalStateException
	 * @throws SecurityException
	 */
	@POST
	@Path("/subscribe/")
	@Produces(MediaType.APPLICATION_JSON)
	public String subChallenge(@Context HttpServletRequest request, @QueryParam("challenge") int chalId)
			throws SecurityException, IllegalStateException, NotSupportedException, SystemException, RollbackException,
			HeuristicMixedException, HeuristicRollbackException, NamingException {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		if (username == null)
			return "you're not signed in";
		if (existChal(chalId)) {
			if (existUser(username))
				DAO.subChal(chalId, username);
		}
		return getUser(username).chalsToString();
	}

	/**
	 * Retrieve all challenges that the user is subscribed to
	 * 
	 * @param request (context request for session handling)
	 * 
	 * @return subscribed challenges as an application/json object
	 */
	@GET
	@Path("/profile/progression/")
	@Produces(MediaType.APPLICATION_JSON)
	public String progression(@Context HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		if (username == null)
			return "you're not signed in";
		return arrayToJson(chalIdsToChals(getUser(username).getChallenges()));
	}

	/**
	 * Suggestion of a theme for a challenge
	 * 
	 * @param request (context request for session handling)
	 * @param theme   (theme query parameter)
	 * 
	 *                Add the suggestion to suggestion list
	 * 
	 * @return the suggestion as an application/json object
	 * @throws NamingException
	 * @throws HeuristicRollbackException
	 * @throws HeuristicMixedException
	 * @throws RollbackException
	 * @throws SystemException
	 * @throws NotSupportedException
	 * @throws IllegalStateException
	 * @throws SecurityException
	 */
	@POST
	@Path("/suggestion")
	@Produces(MediaType.APPLICATION_JSON)
	public String suggest(@Context HttpServletRequest request, @QueryParam("theme") String theme)
			throws SecurityException, IllegalStateException, NotSupportedException, SystemException, RollbackException,
			HeuristicMixedException, HeuristicRollbackException, NamingException {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		if (username == null)
			return "you're not signed in";
		List<Suggestion> sugs = DAO.getSugs();
		DAO.addSug(new Suggestion(sugs.get(sugs.size() - 1).getId() + 1, username, theme));
		sugs = DAO.getSugs();
		return sugs.get(sugs.size() - 1).toString();
	}

	/*************************** ADMIN **************************************/
	/**
	 * Get all users
	 * 
	 * @param request (context request for session handling)
	 * 
	 * @return all users as an application/json object
	 */
	@GET
	@Path("/users/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUsers(@Context HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		if (username == null)
			return "you're not signed in";
		if (getUser(username).isAdmin())
			return arrayToJson(DAO.getUsers());
		else
			return "not authorized";
	}

	/**
	 * Retrieve targeted user
	 * 
	 * @param request (context request for session handling)
	 * @param id      (user id path parameter)
	 * 
	 * @return the user as an application/json object
	 */
	@GET
	@Path("/users/{id}/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUser(@Context HttpServletRequest request, @PathParam("id") int id) {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		if (username == null)
			return "you're not signed in";
		if (getUser(username).isAdmin())
			return getUser(id).toString();

		else
			return "not authorized";
	}

	/**
	 * Delete targeted user
	 * 
	 * @param request (context request for session handling)
	 * @param id      (user id path parameter)
	 * 
	 * @return remaining users as an application/json response
	 * @throws HeuristicRollbackException
	 * @throws HeuristicMixedException
	 * @throws RollbackException
	 * @throws NamingException
	 * @throws SystemException
	 * @throws NotSupportedException
	 * @throws IllegalStateException
	 * @throws SecurityException
	 */
	@DELETE
	@Path("/users/{id}/")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteUser(@Context HttpServletRequest request, @PathParam("id") int id)
			throws SecurityException, IllegalStateException, NotSupportedException, SystemException, NamingException,
			RollbackException, HeuristicMixedException, HeuristicRollbackException {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		if (username == null)
			return "you're not signed in";
		if (getUser(username).isAdmin()) {
			DAO.remove(id);
			return arrayToJson(DAO.getUsers());
		} else
			return "not authorized";
	}

	/**
	 * Modification of user informations
	 * 
	 * @param request  (context request for session handling)
	 * @param password (password query parameter)
	 * 
	 *                 Change user data in db (here only password)
	 * 
	 * @return the user as an application/json object
	 * @throws NamingException
	 * @throws HeuristicRollbackException
	 * @throws HeuristicMixedException
	 * @throws RollbackException
	 * @throws SystemException
	 * @throws NotSupportedException
	 * @throws IllegalStateException
	 * @throws SecurityException
	 */
	@PUT
	@Path("/users/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateUser(@Context HttpServletRequest request, @PathParam("id") int id,
			@QueryParam("password") String password)
			throws SecurityException, IllegalStateException, NotSupportedException, SystemException, RollbackException,
			HeuristicMixedException, HeuristicRollbackException, NamingException {
		HttpSession session = request.getSession(true);
		String name = (String) session.getAttribute("username");
		if (name == null)
			return "you're not signed in";
		if (getUser(name).isAdmin()) {
			DAO.setPassword(id, password);
			return getUser(id).toString();
		} else
			return "not authorized";
	}

	/**
	 * Create a challenge
	 * 
	 * @param request (context request for session handling)
	 * @param name    (challenge name query parameter)
	 * @param desc    (description query parameter)
	 * 
	 *                Add challenge to list
	 * 
	 * @return created challenge as an application/json object
	 * @throws NamingException
	 * @throws HeuristicRollbackException
	 * @throws HeuristicMixedException
	 * @throws RollbackException
	 * @throws SystemException
	 * @throws NotSupportedException
	 * @throws IllegalStateException
	 * @throws SecurityException
	 */
	@POST
	@Path("/challenges/")
	@Produces(MediaType.APPLICATION_JSON)
	public String addChallenge(@Context HttpServletRequest request, @QueryParam("name") String name,
			@QueryParam("desc") String desc) throws SecurityException, IllegalStateException, NotSupportedException,
			SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException, NamingException {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		if (username == null)
			return "you're not signed in";
		if (getUser(username).isAdmin()) {
			List<Challenge> chals = DAO.getChals();
			DAO.addChal(new Challenge((chals.get(chals.size() - 1).getCid() + 1), name, desc));
			chals = DAO.getChals();
			return chals.get(chals.size() - 1).toString();
		} else
			return "not authorized";
	}

	/**
	 * Modify the number of player for a specific challenge
	 * 
	 * @param request (context request for session handling)
	 * @param id      (challenge id path parameter)
	 * @param players (players number query parameter)
	 * 
	 *                Set the max player number
	 * 
	 * @return the modified challenge as an application/json object
	 * @throws NamingException
	 * @throws HeuristicRollbackException
	 * @throws HeuristicMixedException
	 * @throws RollbackException
	 * @throws SystemException
	 * @throws NotSupportedException
	 * @throws IllegalStateException
	 * @throws SecurityException
	 */
	@PUT
	@Path("/challenges/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String modifyChallenge(@Context HttpServletRequest request, @PathParam("id") int id,
			@QueryParam("players") int players) throws SecurityException, IllegalStateException, NotSupportedException,
			SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException, NamingException {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		if (username == null)
			return "you're not signed in";
		if (getUser(username).isAdmin()) {
			DAO.setPlayers(id, players);
			return getChal(id).toString();
		} else
			return "not authorized";
	}

	/**
	 * Get all suggestions
	 * 
	 * @param request (context request for session handling)
	 * 
	 * @return all suggestions as an application/json response
	 */
	@GET
	@Path("/suggestion/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSugs(@Context HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		if (username == null)
			return "you're not signed in";
		if (getUser(username).isAdmin()) {
			return arrayToJson(DAO.getSugs());
		} else
			return "not authorized";
	}

	/**
	 * Create a PPassage on a challenge
	 * 
	 * @param request (context request for session handling)
	 * @param chalId  (the challenge id path parameter)
	 * @param name    (the name of ppassage query parameter)
	 * 
	 * @return the PPassage list of this challenge as an application/json response
	 * @throws SecurityException
	 * @throws IllegalStateException
	 * @throws NamingException
	 * @throws NotSupportedException
	 * @throws SystemException
	 * @throws RollbackException
	 * @throws HeuristicMixedException
	 * @throws HeuristicRollbackException
	 */
	@POST
	@Path("/challenges/{chalId}/ppassage/")
	@Produces(MediaType.APPLICATION_JSON)
	public String postPpassage(@Context HttpServletRequest request, @PathParam("chalId") int chalId,
			@QueryParam("name") String name)
			throws SecurityException, IllegalStateException, NamingException, NotSupportedException, SystemException,
			RollbackException, HeuristicMixedException, HeuristicRollbackException {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		if (username == null)
			return "you're not signed in";
		if (getUser(username).isAdmin()) {
			DAO.createPpassage(chalId, name);
			return arrayToJson(getChal(chalId).getPps());
		} else
			return "not authorized";
	}

	/**
	 * Get a pp on a challenge
	 * 
	 * @param request (context request for session handling)
	 * @param chalId  (the challenge id path parameter)
	 * @param ppId    (the pp id path parameter)
	 * 
	 * @return the pp of the challenge as an application/json object
	 */
	@GET
	@Path("/challenges/{chalId}/ppassage/{ppId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getPp(@Context HttpServletRequest request, @PathParam("chalId") int chalId,
			@PathParam("ppId") int ppId) {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		if (username == null)
			return "you're not signed in";
		if (getUser(username).isAdmin()) {
			return getChal(chalId).getPp(ppId).toString();
		} else
			return "not authorized";
	}

	/**
	 * Get a pp on a challenge
	 * 
	 * @param request (context request for session handling)
	 * @param chalId  (the challenge id path parameter)
	 * @param ppId    (the pp id path parameter)
	 * 
	 * @return the pp of the challenge as an application/json object
	 * @throws HeuristicRollbackException
	 * @throws HeuristicMixedException
	 * @throws RollbackException
	 * @throws NamingException
	 * @throws SystemException
	 * @throws NotSupportedException
	 * @throws IllegalStateException
	 * @throws SecurityException
	 */
	@DELETE
	@Path("/challenges/{chalId}/ppassage/{ppId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String delPp(@Context HttpServletRequest request, @PathParam("chalId") int chalId,
			@PathParam("ppId") int ppId) throws SecurityException, IllegalStateException, NotSupportedException,
			SystemException, NamingException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		if (username == null)
			return "you're not signed in";
		if (getUser(username).isAdmin()) {
			DAO.removePp(chalId, ppId);
			return arrayToJson(getChal(chalId).getPps());
		} else
			return "not authorized";
	}

	/**
	 * Create a new segment on a challenge with previous pp and next pp
	 * 
	 * @param request (context request for session handling)
	 * @param chalId  (the challenge id path parameter)
	 * @param name    (the segment name query parameter)
	 * @param ppAvId  (the previous pp id query parameter)
	 * @param ppApId  (the next pp id query parameter)
	 * @param dist    (the previous pp id query parameter)
	 * 
	 * @return all the segments of the challenge as application/json objects
	 * @throws SecurityException
	 * @throws IllegalStateException
	 * @throws NamingException
	 * @throws NotSupportedException
	 * @throws SystemException
	 * @throws RollbackException
	 * @throws HeuristicMixedException
	 * @throws HeuristicRollbackException
	 */
	@POST
	@Path("/challenges/{chalId}/segment/")
	@Produces(MediaType.APPLICATION_JSON)
	public String createSeg(@Context HttpServletRequest request, @PathParam("chalId") int chalId,
			@QueryParam("name") String name, @QueryParam("ppAvId") int ppAvId, @QueryParam("ppApId") int ppApId,
			@QueryParam("dist") int dist)
			throws SecurityException, IllegalStateException, NamingException, NotSupportedException, SystemException,
			RollbackException, HeuristicMixedException, HeuristicRollbackException {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		if (username == null)
			return "you're not signed in";
		if (getUser(username).isAdmin()) {
			DAO.createSeg(chalId, name, ppAvId, ppApId, dist);
			return arrayToJson(getChal(chalId).getSegs());
		} else
			return "not authorized";
	}

	/**
	 * Get a segment on a challenge
	 * 
	 * @param request (context request for session handling)
	 * @param chalId  (the challenge id path parameter)
	 * @param segId   (the segment id path parameter)
	 * 
	 * @return the segment as an application/json object
	 */
	@GET
	@Path("/challenges/{chalId}/segment/{segId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSeg(@Context HttpServletRequest request, @PathParam("chalId") int chalId,
			@PathParam("segId") int segId) {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		if (username == null)
			return "you're not signed in";
		if (getUser(username).isAdmin()) {
			return getChal(chalId).getSeg(segId).toString();
		} else
			return "not authorized";
	}

	/**
	 * Delete a segment on a challenge
	 * 
	 * @param request (context request for session handling)
	 * @param chalId  (the challenge id path parameter)
	 * @param segId   (the segment id path parameter)
	 * 
	 * @return remaining segments of the challenge as application/json objects
	 * @throws NamingException
	 * @throws HeuristicRollbackException
	 * @throws HeuristicMixedException
	 * @throws RollbackException
	 * @throws SystemException
	 * @throws NotSupportedException
	 * @throws IllegalStateException
	 * @throws SecurityException
	 */
	@DELETE
	@Path("/challenges/{chalId}/segment/{segId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String delSeg(@Context HttpServletRequest request, @PathParam("chalId") int chalId,
			@PathParam("segId") int segId) throws SecurityException, IllegalStateException, NotSupportedException,
			SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException, NamingException {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		if (username == null)
			return "you're not signed in";
		if (getUser(username).isAdmin()) {
			DAO.removeSeg(chalId, segId);
			return arrayToJson(getChal(chalId).getSegs());
		} else
			return "not authorized";
	}

	/**
	 * Create an obstacle on a segment on a challenge
	 * 
	 * @param request (context request for session handling)
	 * @param chalId (the challenge id path parameter)
	 * @param segId  (the segment id path parameter)
	 * @param name (the obstacle name query parameter)
	 * @param dist (the obstacle distance on the segment query parameter)
	 * @param description (the obstacle description query parameter)
	 * 
	 * @return the obstacle on the segment on the challenge
	 * @throws SecurityException
	 * @throws IllegalStateException
	 * @throws NamingException
	 * @throws NotSupportedException
	 * @throws SystemException
	 * @throws RollbackException
	 * @throws HeuristicMixedException
	 * @throws HeuristicRollbackException
	 */
	@POST
	@Path("/challenges/{chalId}/segment/{segId}/obstacle/")
	@Produces(MediaType.APPLICATION_JSON)
	public String addOb(@Context HttpServletRequest request, @PathParam("chalId") int chalId,
			@PathParam("segId") int segId, @QueryParam("name") String name, @QueryParam("dist") int dist,
			@QueryParam("description") String description) throws SecurityException, IllegalStateException, NamingException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		if (username == null)
			return "you're not signed in";
		if (getUser(username).isAdmin()) {
			DAO.createOb(chalId, segId, name, dist, description);
			return getChal(chalId).getSeg(segId).getOb().toString();
		} else
			return "not authorized";
	}
	
	/**
	 * Get an obstacle on a segment on a challenge
	 * 
	 * @param request (context request for session handling)
	 * @param chalId  (the challenge id path parameter)
	 * @param segId   (the segment id path parameter)
	 * 
	 * @return the obstacle as an application/json object
	 */
	@GET
	@Path("/challenges/{chalId}/segment/{segId}/obstacle/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getOb(@Context HttpServletRequest request, @PathParam("chalId") int chalId,
			@PathParam("segId") int segId) {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		if (username == null)
			return "you're not signed in";
		if (getUser(username).isAdmin()) {
			if(getChal(chalId).getSeg(segId).getOb()!=null)
				return getChal(chalId).getSeg(segId).getOb().toString();
			else
				return "no obstacle on this segment";
		} else
			return "not authorized";
	}

	/**
	 * Delete a segment on a challenge
	 * 
	 * @param request (context request for session handling)
	 * @param chalId  (the challenge id path parameter)
	 * @param segId   (the segment id path parameter)
	 * 
	 * @return remaining segments of the challenge as application/json objects
	 * @throws NamingException
	 * @throws HeuristicRollbackException
	 * @throws HeuristicMixedException
	 * @throws RollbackException
	 * @throws SystemException
	 * @throws NotSupportedException
	 * @throws IllegalStateException
	 * @throws SecurityException
	 */
	@DELETE
	@Path("/challenges/{chalId}/segment/{segId}/obstacle/")
	@Produces(MediaType.APPLICATION_JSON)
	public String delOb(@Context HttpServletRequest request, @PathParam("chalId") int chalId,
			@PathParam("segId") int segId) throws SecurityException, IllegalStateException, NotSupportedException,
			SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException, NamingException {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		if (username == null)
			return "you're not signed in";
		if (getUser(username).isAdmin()) {
			DAO.removeOb(chalId, segId);
			return "" + (getChal(chalId).getSeg(segId).getOb() == null);
		} else
			return "not authorized";
	}
}