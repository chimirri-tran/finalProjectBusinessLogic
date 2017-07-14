package introsde.rest.ehealth.resources;

import java.io.FileOutputStream;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.glassfish.jersey.client.ClientConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.SAXException;

@Stateless
@LocalBean
@Path("/caloriesBusiness")
public class CaloriesBusinessLogic {
	
	static String serverUri = "http://localhost:5702/sdelab/caloriesStorage";
	
	public static Response makeRequest(String path, String mediaType, String method,
			String paramName, String paramValue){
		URI server = UriBuilder.fromUri(serverUri).build();
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(server);
		Response response = null;
		if(method=="get")
		response = service.path(path)
				.queryParam(paramName, paramValue)
				.request(mediaType).accept(mediaType)
				.get(Response.class);
		return response;

	}
	
	public String responseToString(Response response){
		
		String content = response.readEntity(String.class);
		
		/*if too many calories than exercize*/
		JSONObject obj = new JSONObject(content);
        int numCalories = (Integer) obj.get("calories");
		if(numCalories > 100){
	        System.out.println("3"+"y");
			obj.append("introsdeExercize", "yes");
		}
		else{
			obj.append("introsdeExercize", "no");
		}
		content = obj.toString();
		
		return content;
	}
	
    @GET
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public String getCalories(@DefaultValue("apple") @QueryParam("food") String food) 
    		throws ParserConfigurationException, SAXException, IOException {

		String paramName = "food";		
		String paramValue = food;	
		String path = "";
        System.out.println(this.getClass()+"pre");	
        Response response = makeRequest(path, MediaType.APPLICATION_JSON, "get",
        		paramName, paramValue);
        String caloriesString = responseToString(response);

        System.out.println(this.getClass()+"pos");	
        return caloriesString;

    }

}
