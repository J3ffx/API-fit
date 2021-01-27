
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

/**
 * Root resource (exposed at "/" path)
 */
@Path("/")
public class MyResource {

	ArrayList<String> chals;

	public MyResource() {
		this.chals = new ArrayList<String>();
		this.chals.add("Raid de Kessel");
		this.chals.add("De la Comté au Mordor");
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
		for (int i = 0; i < chals.size(); i++) {
			result += "{" + "chalNum: " + i + ", " + "chalName: " + chals.get(i) + "}";
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
		for (int i = 0; i < chals.size(); i++) {
			if (id == i)
				result += "{" + "chalNum: " + i + ", " + "chalName: " + chals.get(i) + "}";
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
		// TODO check if username in db
		HttpSession session = request.getSession(true);
		session.setAttribute("username", username);
		// TODO DATABASE ADD USER
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
		// TODO check if username and password in db
		HttpSession session = request.getSession(true);
		session.setAttribute("username", username);
		return "successfully connected as : " + username + ", " + password;
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
		String username = "{" + "username: " + (String) session.getAttribute("username") + "}";
		return username;
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
		session.removeAttribute("username");
		// TODO DATABASE REMOVE USER
		return "successfully removed profile";
	}

}