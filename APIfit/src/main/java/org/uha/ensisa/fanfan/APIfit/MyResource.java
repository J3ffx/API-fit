
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
import org.uha.ensisa.fanfan.APIfit.model.User;

/**
 * Root resource (exposed at "/" path)
 */
@Path("/")
public class MyResource {

	ArrayList<Challenge> chals;
	ArrayList<User> users;

	public MyResource() {
		this.chals = new ArrayList<Challenge>();
		this.chals.add(new Challenge(1, "Raid de Kessel"));
		this.chals.add(new Challenge(2, "De la Comté au Mordor"));
		this.users = new ArrayList<User>();
		this.users.add(new User("Jean", "naej42"));
		this.users.get(0).addChallenge(chals.get(0));
	}

	/*************************** UTILITY *************************************/
	
	public User getUser(String username) {
		for(User user : users) {
			if(user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}
	
	public boolean existUser(String username) {
		for(User user : users) {
			if(user.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}
	
	public Challenge getChal(Integer chalId) {
		for(Challenge chal : chals) {
			if(chal.getId()== chalId) {
				return chal;
			}
		}
		return null;
	}
	
	public boolean existChal(Integer chalId) {
		for(Challenge chal : chals) {
			if(chal.getId()== chalId) {
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
		String result = "{";
		for (Challenge chal : chals) {
			result += "{" + "chalNum: " + chal.getId() + ", " + "chalName: " + chal.getName() + "}";
		}
		result += "}";
		return result;
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
		String result = "";
		for (Challenge chal : chals) {
			if (id == chal.getId())
				result += "{" + "chalNum: " + chal.getId() + ", " + "chalName: " + chal.getName() + "}";
		}
		return result;
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
		users.add(new User(username, password));
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
		if(existUser(username)) {
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
	 *                Check if signed in
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
		if(existUser(username))
			return getUser(username).toString();
		return "error, no profile found";
	}

	/**
	 * Disconnect the connected user
	 * 
	 * @param request (context request for session handling)
	 * 
	 *                Check if signed in
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
	 *                Check if signed in
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
		if(existUser(username))
			users.remove(getUser(username));
		return "successfully removed profile";
	}

	/**
	 * Modification of profile informations
	 * 
	 * @param username (username query parameter)
	 * @param password (password query parameter)
	 * 
	 *                 Check if signed in
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
		if(existUser(username))
			getUser(username).setPassword(password);
		return "successfully updated : username = " + username + ", password = " + password;
	}

	// POST APIfit/subscribe?challenge={idC}
	@POST
	@Path("/subscribe/")
	@Produces(MediaType.APPLICATION_JSON)
	public String subChallenge(@Context HttpServletRequest request, @QueryParam("challenge") int chalId) {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		if (username == null)
			return "you're not signed in";
		if(existChal(chalId)) {
			if(existUser(username))
				getUser(username).addChallenge(getChal(chalId));
		}
		return getUser(username).toString();
	}

}