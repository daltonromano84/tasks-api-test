package br.ce.dalton.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ApiTest {
	
	
	@BeforeClass
	public static void setup() {
		
		RestAssured.baseURI="http://localhost:8081/tasks-backend";
	}
	
	@Test
	public void deveRetornarTarefas() {
		
		
		RestAssured.given()
			        	
				   .when()
				   		 .get("/todo")				   
				   .then()
				   	.statusCode(200);
	}
	
	
	@Test
	public void deveAdicionarTarefaComSucesso() {
		
		
		RestAssured.given()
					.body("{\"task\":\"teste via API\",\"dueDate\":\"2020-10-23\"}")
					.contentType(ContentType.JSON)
				   .when()
				   		.post("/todo")		   
				   .then()
				   	.statusCode(201);
	}
	
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		
		
		RestAssured.given()
					.body("{\"task\":\"teste via API\",\"dueDate\":\"2010-10-23\"}")
					.contentType(ContentType.JSON)
				   .when()
				   		.post("/todo")		   
				   .then()
				   .log().all()
				   	.statusCode(400)
				   	.body("message",CoreMatchers.is("Due date must not be in past"));
	}
	@Test
	public void deveRemoverTarefaComSucesso() {
		
		//inserir
		Integer id =  RestAssured.given()
		.body("{\"task\":\"Tarefa para remoçao\",\"dueDate\":\"2020-10-23\"}")
		.contentType(ContentType.JSON)
	   .when()
	   		.post("/todo")		   
	   .then()
	   //	.log().all()
	   	.statusCode(201)
	   	.extract().path("id");
		
		System.out.println(id);
		
		//remover
		
		RestAssured.given()
					.when()
					.delete("/todo/"+id)
					.then()
					.statusCode(204);
		
		
	}
	

}
