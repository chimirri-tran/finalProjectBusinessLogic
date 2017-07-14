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

import introsde.rest.ehealth.model.Goal;

@Stateless
@LocalBean
@Path("/checkGoalBusiness")
public class CheckGoalBusinessLogic {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	private String storageServiceURL;

	private static String mediaType = MediaType.APPLICATION_JSON;

	ClientConfig clientConfig = new ClientConfig();

	static String serverUri = " http://localhost:5800/sdelab/goalStorage";	

	// MANDARE RICHIESTA GET SOTTO

	public static Response makeRequest(String server, String path, String mediaType, 
			String method, JSONObject obj){

		URI uri = UriBuilder.fromUri(serverUri).build();
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(uri);
		
		Response response = null;
		
		if(obj.get("savegoal").equals("no")){
		response = service.path(path)
				.queryParam("savegoal", "no")
				.queryParam("idperson",obj.get("idperson"))
				.request(mediaType)
				.accept(mediaType)
				.get(Response.class);
		} else {			
			response = service.path(path)
					.queryParam("savegoal", "yes")
					.queryParam("idperson",obj.get("idperson"))
					.queryParam("steps",obj.get("steps"))
					.queryParam("heartrate",obj.get("heartrate"))
					.queryParam("weight",obj.get("weight"))
					.queryParam("sleephours",obj.get("sleephours"))
					.queryParam("minbloodpressure",obj.get("minbloodpressure"))
					.queryParam("maxbloodpressure",obj.get("maxbloodpressure"))
					.request(mediaType)
					.accept(mediaType)
					.get(Response.class);
		}
		
		/*
		WebTarget wt =  service.path(path);
	    for (Object key : obj.keySet()) {
	        String keyStr = (String)key;
			wt.queryParam(keyStr, obj.get(keyStr));			
		}
		Response response = wt
				.request(mediaType)
				.accept(mediaType)
				.get(Response.class);
		*/

		return response;

	}
	
	/*

	private String errorMessage(Exception e) {
		return "{ \n \"error\" : \"Error in Business Logic Services, due to the exception: " + e + "\"}";
	}

	private String externalErrorMessage(String e) {
		return "{ \n \"error\" : \"Error in External services, due to the exception: " + e + "\"}";
	}

	*/

	/**
	 * POST /businessLogic-service/person
	 * 
	 * This method calls a createPerson method in Storage Services Module
	 * @return 
	 * 
	 * @return
	 */
	/*
	@POST
	@Produces({ MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response insertNewPerson(String inputPersonJSON) {
		try {

			System.out.println(
					"insertNewPerson: Inserting a new Person from Storage Services Module in Business Logic Services");

			String path = "/goal";

			Client client = ClientBuilder.newClient(clientConfig);
			WebTarget service = client.target(storageServiceURL + path);
			Builder builder = service.request(mediaType);

			Response response = builder.post(Entity.json(inputPersonJSON));

			String result = response.readEntity(String.class);

			if (response.getStatus() != 201) {
				System.out.println("Storage Service Error response.getStatus() != 201");
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity(externalErrorMessage(response.toString())).build();
			} else {
				return Response.ok(result).build();
			}
		} catch (Exception e) {
			System.out.println("Business Logic Service Error catch response.getStatus() != 201");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage(e)).build();
		}
	}
	*/

	/*
	public Goal CheckGoal(Goal ExistedGoal, Goal NewGoal) {
		Goal updateGoal = new Goal();
		if (NewGoal.getActualWeight() == 0) {
			updateGoal.setActualWeight(ExistedGoal.getActualWeight());
		} else {
			updateGoal.setActualWeight(NewGoal.getActualWeight());
		}
		if (NewGoal.getHeartRate() == 0) {
			updateGoal.setHeartRate(ExistedGoal.getHeartRate());
		} else {
			updateGoal.setHeartRate(NewGoal.getHeartRate());
		}
		if (NewGoal.getMaxBloodPressure() == 0) {
			updateGoal.setMaxBloodPressure(ExistedGoal.getMaxBloodPressure());
		} else {
			updateGoal.setMaxBloodPressure(NewGoal.getMaxBloodPressure());
		}
		if (NewGoal.getMinBloodPressure() == 0) {
			updateGoal.setMinBloodPressure(ExistedGoal.getMinBloodPressure());
		} else {
			updateGoal.setMinBloodPressure(NewGoal.getMinBloodPressure());
		}
		if (NewGoal.getSleepHours() == 0) {
			updateGoal.setSleepHours(ExistedGoal.getSleepHours());
		} else {
			updateGoal.setSleepHours(NewGoal.getSleepHours());
		}
		if (NewGoal.getSteps() == 0) {
			updateGoal.setSteps(ExistedGoal.getSteps());
		} else {
			updateGoal.setSteps(NewGoal.getSteps());
		}

		return updateGoal;
	}

	public void ShowFeedback(Goal ExistedGoal, Goal NewGoal) {

		if (NewGoal.getActualWeight() >= ExistedGoal.getActualWeight()) {
			double n = NewGoal.getActualWeight() - ExistedGoal.getActualWeight();
			System.out.println("From last time you got: " + n + ". Try to do these exercises.");
		} else {
			double n = ExistedGoal.getActualWeight() - NewGoal.getActualWeight();
			System.out.println("From last time you lost: " + n + ". Keep going like that!");
		}

		if (NewGoal.getHeartRate() > 80) {
			System.out.println("You should relax and do some sport/hobbies to reduce it.");
		}
		if (NewGoal.getHeartRate() < 40) {
			System.out.println("You should go to the doctor to see if everything is fine.");
		} else {
			System.out.println("Your heart rate is good");
		}

		if (NewGoal.getMaxBloodPressure() >= 140 && NewGoal.getMinBloodPressure() >= 90) {
			System.out.println("Your blood pressure is high. We suggest you to eat less salt");
		} else {
			if (NewGoal.getMaxBloodPressure() >= 160 && NewGoal.getMinBloodPressure() >= 100) {
				System.out.println("Your blood pressure is really high. We suggest you to go to the doctor. He will recommend you a cure.");
			}
		}
		if (NewGoal.getMaxBloodPressure() <= 120 && NewGoal.getMinBloodPressure() >= 70) {
			System.out.println("Your blood pressure is low. We suggest you to eat food with sugar and salty.");
		} else {
			if (NewGoal.getMaxBloodPressure() >= 100 && NewGoal.getMinBloodPressure() >= 60) {
				System.out.println("Your blood pressure is really low. We suggest you to visit a doctor. He will recommend you a remedy.");
			}
		}

		if (NewGoal.getSleepHours() >= ExistedGoal.getSleepHours()){
			if (NewGoal.getSleepHours() < 8) {
				System.out.println("You should sleep more. At least 8 hours.");
			}
			if (NewGoal.getSleepHours() <= 9) {
				System.out.println("You should sleep less. 8 hours are enough.");
			} else {
				System.out.println("You sleep the right number of hours.");
			}
		}

		if (NewGoal.getSteps() >= ExistedGoal.getSteps()) {
			if (NewGoal.getSteps() < 8000) {
				System.out.println("You should walk more. Try to do a stroll.");
			}
			if (NewGoal.getSteps() >= 8000) {
				System.out.println("Keep do it like that!! Be proud of yourself");
			}
		}
	}
	*/
	
	public JSONObject feedback = new JSONObject();
	
	public JSONObject compareGoals(JSONObject params, JSONObject olds){	
		
		feedback.put("accomplished","no");	

		String feedBackSteps = "Let us know how many steps you walked";
		String feedBackHeartrate = "Let us know how many steps your hearthrate";
		String feedBackWeight = "Let us know your weight";
		String feedBackSleephours = "Let us know how many hours you slept";
		String feedBackMinbloodpressure = "Let us know your min blood pressure";
		String feedBackMaxbloodpressure = "Let us know your max blood pressure";
		
		String newweight = (String) params.get("weight");
		String oldweight = (String) olds.get("weight");
		if(!params.get("weight").equals("")){
			try{
				if(Double.parseDouble(newweight)==Double.parseDouble((String) olds.get("finalweight"))){
					feedback.put("accomplished","no");	
				}
				if (Double.parseDouble(oldweight) >= Double.parseDouble(newweight)) {
					double n = Double.parseDouble(newweight) - Double.parseDouble(oldweight);
					if(olds.get("lostweight").equals("1"))
					feedBackWeight="From last time you got: " + n + ". Try to do these exercises.";
					else feedBackWeight="From last time you got: " + n + ". Keep going like that!";
				} else {
					double n = Double.parseDouble(oldweight) - Double.parseDouble(newweight);
					if(olds.get("lostweight").equals("1"))
					feedBackWeight="From last time you lost: " + n + ". Keep going like that!";
					else feedBackWeight="From last time you lost: " + n + ".you should instead try and get some weight";
				}
				params.put("weight", newweight);
			} catch(Exception e){}
		}

		String newheartrate= (String) params.get("heartrate");
		String oldheartrate = (String) olds.get("heartrate");
		if(!params.get("heartrate").equals("")){
			try{
			if (Integer.parseInt(newheartrate) > 80) {
				feedBackHeartrate="You should relax and do some sport/hobbies to reduce it.";
			}
			if (Integer.parseInt(newheartrate) < 40) {
				feedBackHeartrate="You should go to the doctor to see if everything is fine.";
			} else {
				feedBackHeartrate="Your heart rate is good";
			}
			params.put("heartrate", newheartrate);
			} catch(Exception e){}
		}
		
		

		String newminbloodpressure = (String) params.get("minbloodpressure");
		String oldminbloodpressure = (String) olds.get("minbloodpressure");
		String newmaxbloodpressure = (String) params.get("maxbloodpressure");
		String oldmaxbloodpressure = (String) olds.get("maxbloodpressure");
		if(!params.get("minbloodpressure").equals("") && !params.get("maxbloodpressure").equals("")){
			try{
				if (Integer.parseInt(newmaxbloodpressure) >= 140 && Integer.parseInt(newminbloodpressure) >= 90) {
					feedBackMinbloodpressure="Your blood pressure is high. We suggest you to eat less salt";
					feedBackMaxbloodpressure=feedBackMinbloodpressure;
				} else {
					if (Integer.parseInt(newmaxbloodpressure) >= 160 && Integer.parseInt(newminbloodpressure) >= 100) {
						feedBackMinbloodpressure="Your blood pressure is really high. We suggest you to go to the doctor. He will recommend you a cure.";
						feedBackMaxbloodpressure=feedBackMinbloodpressure;
					}
				}
				if (Integer.parseInt(newmaxbloodpressure) <= 120 && Integer.parseInt(newminbloodpressure) >= 70) {
					feedBackMinbloodpressure="Your blood pressure is low. We suggest you to eat food with sugar and salty.";
					feedBackMaxbloodpressure=feedBackMinbloodpressure;
				} else {
					if (Integer.parseInt(newmaxbloodpressure) >= 100 && Integer.parseInt(newminbloodpressure) >= 60) {
						feedBackMinbloodpressure="Your blood pressure is really low. We suggest you to visit a doctor. He will recommend you a remedy.";
						feedBackMaxbloodpressure=feedBackMinbloodpressure;
					}
				}
				params.put("minbloodpressure", newminbloodpressure);
				params.put("maxbloodpressure", newmaxbloodpressure);
			} catch(Exception e){}
		}
	

		String newsleephours = (String) params.get("sleephours");
		String oldsleephours = (String) olds.get("sleephours");
		if(!params.get("sleephours").equals("")){
			//if (NewGoal.getSleepHours() >= ExistedGoal.getSleepHours()){
			try{
				if (Integer.parseInt(newsleephours) < 8) {
					feedBackSleephours="You should sleep more. At least 8 hours.";
				}
				if (Integer.parseInt(newsleephours) <= 9) {
					feedBackSleephours="You should sleep less. 8 hours are enough.";
				} else {
					feedBackSleephours="You sleep the right number of hours.";
				}
				params.put("sleephours", newsleephours);
			} catch(Exception e){}
			//}
		}

		String newsteps = (String) params.get("steps");
		String oldsteps = (String) olds.get("steps");
		if(!params.get("steps").equals("")){
			try{
			//if (Integer.parseInt(newsteps) >= Integer.parseInt(oldsteps)) {
				if (Integer.parseInt(newsteps) < 8000) {
					feedBackSteps="You should walk more. Try to do a stroll.";
				}
				if (Integer.parseInt(newsteps) >= 8000) {
					feedBackSteps="Keep do it like that!! Be proud of yourself";
				}
			//}
				params.put("steps", newsteps);
			} catch(Exception e){}
		}
		

		feedback.put("feedBackSteps",feedBackSteps);
		feedback.put("feedBackHeartrate",feedBackHeartrate);
		feedback.put("feedBackWeight",feedBackWeight);
		feedback.put("feedBackSleephours",feedBackSleephours);
		feedback.put("feedBackMinbloodpressure",feedBackMinbloodpressure);
		feedback.put("feedBackMaxbloodpressure",feedBackMaxbloodpressure);
		
		
		return params;
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
		params.put("idperson",idperson);
		params.put("savegoal", "no");
        System.out.println(this.getClass()+params.toString());	

        Response oldValues = makeRequest(
        		"",
        		path, 
        		MediaType.APPLICATION_JSON,
        		method,	        		
        		params);
        System.out.println(this.getClass()+oldValues.toString());	
        
        JSONObject olds = new JSONObject(oldValues);
        JSONObject newParams = compareGoals(params,olds);
        newParams.put("savegoal", "yes");
		makeRequest(
        		"",
        		path, 
        		MediaType.APPLICATION_JSON,
        		method,	        		
        		newParams);

        System.out.println(this.getClass()+" pos ");
    	
    	//MANDARE UNA RISPOSTA AL LIVELLO SOPRA
        return feedback.toString();

    }
}
