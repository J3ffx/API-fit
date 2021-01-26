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
	 * 
	 * Method handling HTTP GET requests to /challenges path. The returned object
	 * will be sent to the client as "application/json" media type.
	 *
	 * @return String that will be returned as an application/json response.
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

	@POST
	@Path("/signup/")
	@Produces(MediaType.TEXT_PLAIN)
	public String signUp(@Context HttpServletRequest request, @QueryParam("username") String username,
			@QueryParam("password") String password) {
		HttpSession session = request.getSession(true);
		session.setAttribute("username", username);
		// TODO DATABASE ADD USER
		return "successfully registered and connected as : " + username + ", " + password;
	}

	@PUT
	@Path("/signin/")
	@Produces(MediaType.TEXT_PLAIN)
	public String signIn(@Context HttpServletRequest request, @QueryParam("username") String username,
			@QueryParam("password") String password) {
		HttpSession session = request.getSession(true);
		session.setAttribute("username", username);
		return "successfully connected as : " + username + ", " + password;
	}

	/****************************** JOUEUR ***********************************/

	@GET
	@Path("/profile/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getProfile(@Context HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		return username;
	}

	@PUT
	@Path("/signout/")
	@Produces(MediaType.APPLICATION_JSON)
	public String signOUt(@Context HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		session.removeAttribute("username");
		return "successfully disconnected";
	}

	@DELETE
	@Path("/profile/")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteProfile(@Context HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		session.removeAttribute("username");
		// TODO DATABASE REMOVE USER
		return "successfully removed profile";
	}

}
