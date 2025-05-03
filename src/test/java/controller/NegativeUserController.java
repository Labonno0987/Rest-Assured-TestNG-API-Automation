package controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import setup.UserModel;

import java.util.Properties;
import static io.restassured.RestAssured.given;

public class NegativeUserController {
    Properties prop;

    public NegativeUserController(Properties prop){
        this.prop=prop;
    }
    
}
