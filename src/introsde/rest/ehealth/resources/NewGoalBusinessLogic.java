package introsde.rest.ehealth.resources;

import java.io.IOException;
import java.net.URI;
import javax.ws.rs.core.UriInfo;
import javax.xml.parsers.ParserConfigurationException;
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
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.json.JSONObject;
import org.xml.sax.SAXException;

@Stateless
@LocalBean
@Path("/newGoalBusiness")
public class NewGoalBusinessLogic {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	/*
	private String storageServiceURL;

	private static String mediaType = MediaType.APPLICATION_XML;
	*/
	ClientConfig clientConfig = new ClientConfig();

	static String serverUri = " http://localhost:5800/sdelab/goalStorage";	
	/*
	private String errorMessage(Exception e) {
		return "{ \n \"error\" : \"Error in Business Logic Services, due to the exception: "
				+ e + "\"}";
	}
	private String externalErrorMessage(String e) {
		return "{ \n \"error\" : \"Error in External services, due to the exception: "
				+ e + "\"}";
	}
	*/

	
	/**
	 * POST /businessLogic-service/person 
	 * 
	 * This method calls a createPerson method in Storage Services Module
	 * @return
	 */
	/*
	@POST
	@Produces({ MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response insertNewPerson(String inputPersonJSON) {
		try {

			System.out
					.println("insertNewPerson: Inserting a new Person from Storage Services Module in Business Logic Services");

			String path = "/goal";

			Client client = ClientBuilder.newClient(clientConfig);
			WebTarget service = client.target(storageServiceURL + path);
			Builder builder = service.request(mediaType);

			Response response = builder.post(Entity.json(inputPersonJSON));

			String result = response.readEntity(String.class);

			if (response.getStatus() != 201) {
				System.out
						.println("Storage Service Error response.getStatus() != 201");
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity(externalErrorMessage(response.toString()))
						.build();
			} else {
				return Response.ok(result).build();
			}
		} catch (Exception e) {
			System.out
					.println("Business Logic Service Error catch response.getStatus() != 201");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(errorMessage(e)).build();
		}
	}
	*/
	
	
	// MANDARE RICHIESTA GET SOTTO


	public static Response makeRequest(String server, String path, String mediaType, 
			String method, JSONObject obj){

		URI uri = UriBuilder.fromUri(serverUri).build();
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(uri);
		
		Response response = service.path(path)
		.queryParam("steps", obj.get("steps"))
		.queryParam("heartrate", obj.get("heartrate"))
		.queryParam("height", obj.get("height"))
		.queryParam("sleephours", obj.get("sleephours"))
		.queryParam("minbloodpressure", obj.get("minbloodpressure"))
		.queryParam("maxbloodpressure", obj.get("maxbloodpressure"))
		.queryParam("username", obj.get("username"))
		.queryParam("password", obj.get("password"))
		.queryParam("idperson", obj.get("idperson"))
		.queryParam("weight", obj.get("weight"))
		.queryParam("actualweight", obj.get("actualweight"))
		.queryParam("finalweight", obj.get("finalweight"))
		.queryParam("lostweight", obj.get("lostweight"))
		.queryParam("initialweight", obj.get("initialweight"))
		.queryParam("savegoal", obj.get("savegoal"))
		.request(mediaType)
		.accept(mediaType)
		.get(Response.class);
		/*
		WebTarget wt =  service.path(path);
	    for (Object key : obj.keySet()) {
	        String keyStr = (String)key;
	        String keyvalue = (String) obj.get(keyStr);
			wt.queryParam(keyStr, keyvalue);			
		}
		Response response = wt
				.request(mediaType)
				.accept(mediaType)
				.get(Response.class);*/

		return response;

	}
	
	
	
	//CALCULATION OF BMI

	public double calculateBMI(double height, double weight){
		
		double bmi =0.0;
		bmi = ((weight * 703)/(height * height));
		return bmi;
	}
	
	public int goalFromBmi(double bmi){

		int goal = 0;
		
		if (bmi<18.5){
			goal = -1;
		} else if (bmi<24.9){
			goal = 0;
		}else if (bmi<29.9){
			goal = 1;
		}else{
			goal = 1;
		}
		
		return goal;
	}
	
	public String messageFromBmi(double bmi, int goal, double finalWeight){

		String message = "";
		
		if (bmi<18.5){
			message = "You are underweight. You should eat more!! Try to reach " + finalWeight;
		} else if (bmi<24.9){
			message = "You are normal, but you can still improve!! Try to reach " + finalWeight;
		}else if (bmi<29.9){
			message = "You are overweight. Here a good program to loose some weight!! Try to reach " + finalWeight;
		}else{
			message = "You are obese. With this program you will have a perfect body!! Try to reach " + finalWeight;
		}
		
		
		return message;
	}
	
	public double calculateFinalWeight(double weight, int goal, double bmi){
		double finalWeight = weight;
		if(goal == -1){
			finalWeight = weight + weight/10.0;
		}
		else if(goal == 1){
			finalWeight = weight - weight/10.0;
		}
		else {
			if(bmi>21) finalWeight = weight -1;
			else finalWeight = weight + 1;			
		}
		return finalWeight;		
	}

    @GET
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public String getCalories(
    		@DefaultValue("") @QueryParam("steps") String steps,
    		@DefaultValue("") @QueryParam("heartrate") String heartrate,
    		@DefaultValue("") @QueryParam("weight") String weight,
    		@DefaultValue("") @QueryParam("sleephours") String sleephours,
    		@DefaultValue("") @QueryParam("minbloodpressure") String minbloodpressure,
    		@DefaultValue("") @QueryParam("maxbloodpressure") String maxbloodpressure,
    		@DefaultValue("") @QueryParam("username") String username,
    		@DefaultValue("") @QueryParam("password") String password,
    		@DefaultValue("") @QueryParam("height") String height,
    		@DefaultValue("") @QueryParam("idperson") String idperson  		
    		)
    		throws ParserConfigurationException, SAXException, IOException {
	
		String path = "";
		String method = "get";
        System.out.println(this.getClass()+"pre");	

		JSONObject params = new JSONObject();
		params.put("steps",steps);
		params.put("heartrate",heartrate);
		params.put("weight",weight);
		params.put("sleephours",sleephours);
		params.put("minbloodpressure",minbloodpressure);
		params.put("maxbloodpressure",maxbloodpressure);
		params.put("username",username);
		params.put("password",password);
		params.put("height",height);
		params.put("idperson",idperson);

        double bmi = calculateBMI(Double.parseDouble(height),Double.parseDouble(weight));
        Integer goal = goalFromBmi(bmi);
        Double finalWeight = calculateFinalWeight(Double.parseDouble(weight),goal,bmi);
        String message = messageFromBmi(bmi,goal,finalWeight);
        
        params.put("actualweight", weight);
        params.put("finalweight", finalWeight.toString());
        params.put("lostweight", goal.toString());//-1 guadagnare, 0 rimanere uguale, 1 perdere
        params.put("initialweight", weight);    
        params.put("savegoal", "yes");    
        

        Response response = makeRequest(
        		"",
        		path, 
        		MediaType.APPLICATION_JSON,
        		method,	        		
        		params);

        System.out.println(this.getClass()+" pos "+ response.readEntity(String.class));
    	
    	//MANDARE UNA RISPOSTA AL LIVELLO SOPRA
        return message;

    }

}
