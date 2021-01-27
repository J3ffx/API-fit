
package org.uha.ensisa.fanfan.APIfit;

import java.util.ArrayList;

import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

	ArrayList<Challenge> chals;
	ArrayList<User> users;
	ArrayList<Suggestion> sugs;

	public MyResource() {
		this.chals = new ArrayList<Challenge>();
		this.chals.add(new Challenge(0, "Raid de Kessel", null));
		this.chals.add(new Challenge(1, "De la Comté au Mordor", null));
		this.users = new ArrayList<User>();
		this.users.add(new User(0, "Jean", "naej42"));
		this.users.get(0).addChallenge(chals.get(0));
		this.users.get(0).setAdmin(true);

		this.sugs = new ArrayList<Suggestion>();
		this.sugs.add(new Suggestion(0, "Jean", "Hunger Games"));
	}

	/*************************** UTILITY *************************************/

	public String arrayToJson(ArrayList list) {
		String result = "{";
		for (Object obj : list) {
			result += obj.toString();
		}
		result += "}";
		return result;
	}

	public User getUser(String username) {
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}

	public User getUser(int id) {
		for (User user : users) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}

	public boolean existUser(String username) {
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}

	public Challenge getChal(Integer chalId) {
		for (Challenge chal : chals) {
			if (chal.getId() == chalId) {
				return chal;
			}
		}
		return null;
	}

	public boolean existChal(Integer chalId) {
		for (Challenge chal : chals) {
			if (chal.getId() == chalId) {
				return true;
			}
		}
		return false;
	}

	public Suggestion getSug(int sugId) {
		for (Suggestion sug : sugs) {
			if (sug.getId() == sugId) {
				return sug;
			}
		}
		return null;
	}

	public boolean existSug(int sugId) {
		for (Suggestion sug : sugs) {
			if (sug.getId() == sugId) {
				return true;
			}
		}
		return false;
	}

	/*************************** PUBLIC **************************************/
	/**
	 * Get all challenges
	 * 
	 * @return all available challenges as an application/json response
	 */
	@GET
	@Path("/challenges/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getChallenges() {
		return arrayToJson(chals);
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
	 */
	@POST
	@Path("/signup/")
	@Produces(MediaType.TEXT_PLAIN)
	public String signUp(@Context HttpServletRequest request, @QueryParam("username") String username,
			@QueryParam("password") String password) {
		if (existUser(username))
			return "username already taken";
		HttpSession session = request.getSession(true);
		session.setAttribute("username", username);
		users.add(new User(users.size(), username, password));
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
	 */
	@DELETE
	@Path("/profile/")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteProfile(@Context HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		if (username == null)
			return "you're not signed in";
		session.removeAttribute("username");
		if (existUser(username))
			users.remove(getUser(username));
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
	 */
	@PUT
	@Path("/profile/")
	@Produces(MediaType.TEXT_PLAIN)
	public String updateProfile(@Context HttpServletRequest request, @QueryParam("username") String username,
			@QueryParam("password") String password) {
		HttpSession session = request.getSession(true);
		if (session.getAttribute("username") == null)
			return "you're not signed in";
		if (existUser(username))
			getUser(username).setPassword(password);
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
	 */
	@POST
	@Path("/subscribe/")
	@Produces(MediaType.APPLICATION_JSON)
	public String subChallenge(@Context HttpServletRequest request, @QueryParam("challenge") int chalId) {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		if (username == null)
			return "you're not signed in";
		if (existChal(chalId)) {
			if (existUser(username))
				getUser(username).addChallenge(getChal(chalId));
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
		return getUser(username).chalsToString();
	}

	// POST APIfit/challenges/suggest?theme={suggestion} : suggestion d’un thème de
	// challenge
	/**
	 * Suggestion of a theme for a challenge
	 * 
	 * @param request (context request for session handling)
	 * @param theme   (theme query parameter)
	 * 
	 *                Add the suggestion to suggestion list
	 * 
	 * @return the suggestion as an application/json object
	 */
	@POST
	@Path("/challenges/suggest")
	@Produces(MediaType.APPLICATION_JSON)
	public String suggest(@Context HttpServletRequest request, @QueryParam("theme") String theme) {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		if (username == null)
			return "you're not signed in";
		sugs.add(new Suggestion(sugs.get(sugs.size() - 1).getId() + 1, username, theme));
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
			return arrayToJson(users);
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
	 */
	@DELETE
	@Path("/users/{id}/")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteUser(@Context HttpServletRequest request, @PathParam("id") int id) {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		if (username == null)
			return "you're not signed in";
		if (getUser(username).isAdmin()) {
			users.remove(getUser(id));
			return arrayToJson(users);
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
	 */
	@PUT
	@Path("/users/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateUser(@Context HttpServletRequest request, @PathParam("id") int id,
			@QueryParam("password") String password) {
		HttpSession session = request.getSession(true);
		String name = (String) session.getAttribute("username");
		if (name == null)
			return "you're not signed in";
		if (getUser(name).isAdmin()) {
			getUser(id).setPassword(password);
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
	 */
	@POST
	@Path("/challenges/create")
	@Produces(MediaType.APPLICATION_JSON)
	public String addChallenge(@Context HttpServletRequest request, @QueryParam("name") String name,
			@QueryParam("desc") String desc) {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		if (username == null)
			return "you're not signed in";
		if (getUser(username).isAdmin()) {
			chals.add(new Challenge((chals.get(chals.size() - 1).getId() + 1), name, desc));
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
	 */
	@PUT
	@Path("/challenges/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String modifyChallenge(@Context HttpServletRequest request, @PathParam("id") int id,
			@QueryParam("players") int players) {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		if (username == null)
			return "you're not signed in";
		if (getUser(username).isAdmin()) {
			getChal(id).setPlayers(players);
			return getChal(id).toString();
		} else
			return "not authorized";
	}	
}