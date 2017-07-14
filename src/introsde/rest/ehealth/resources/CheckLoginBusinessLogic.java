package introsde.rest.ehealth.resources;

import java.io.IOException;
import java.net.URI;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.parsers.ParserConfigurationException;

import org.glassfish.jersey.client.ClientConfig;
import org.json.JSONObject;
import org.xml.sax.SAXException;

@Stateless
@LocalBean
@Path("/checkLoginBusiness")
public class CheckLoginBusinessLogic {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	ClientConfig clientConfig = new ClientConfig();

	// RICEVERE RICHIESTA GET DA SOPRA
	// FUNZIONE CHIAMATA PER VEDERE SE NON SONO NULLI E SE SONO DEL TIPO GIUSTO
	@GET
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String getCalories(
			@DefaultValue("") @QueryParam("username") String username,
			@DefaultValue("") @QueryParam("password") String password,
			@DefaultValue("") @QueryParam("height") String height,
			@DefaultValue("") @QueryParam("sleephours") String sleephours,
			@DefaultValue("") @QueryParam("steps") String steps,
			@DefaultValue("") @QueryParam("maxbloodpressure") String maxbloodpressure,
			@DefaultValue("") @QueryParam("minbloodpressure") String minbloodpressure,
			@DefaultValue("") @QueryParam("heartrate") String heartrate,
			@DefaultValue("") @QueryParam("weight") String weight)

			throws ParserConfigurationException, SAXException, IOException {
		
		System.out.println("username"+username);
		System.out.println("password"+password);
		System.out.println("height"+height);
		System.out.println("username"+username);
		System.out.println("sleephours"+sleephours);
		System.out.println("steps"+steps);
		System.out.println("maxbloodpressure"+maxbloodpressure);
		System.out.println("minbloodpressure"+minbloodpressure);
		System.out.println("heartrate"+heartrate);
		System.out.println("weight"+weight);

		String ok = "ok";

		if (username.equals(""))
			return "error1";
		if (password.equals(""))
			return "error2";
		if (height.equals(""))
			return "error3";
		if (sleephours.equals(""))
			return "error4";
		if (steps.equals(""))
			return "error5";
		if (maxbloodpressure.equals(""))
			return "erro6r";
		if (minbloodpressure.equals(""))
			return "error7";
		if (heartrate.equals(""))
			return "error8";
		if (weight.equals(""))
			return "error9";

		try {
			Double.parseDouble(height);
			Double.parseDouble(weight);
			Integer.parseInt(steps);
			Integer.parseInt(sleephours);
			Integer.parseInt(minbloodpressure);
			Integer.parseInt(maxbloodpressure);
			Integer.parseInt(heartrate);
		} catch (Exception e) {
			return "error10";
		}

		return ok;

	}

}
