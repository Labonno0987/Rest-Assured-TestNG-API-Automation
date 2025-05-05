package controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import setup.UserModel;

import java.util.Properties;
import static io.restassured.RestAssured.given;

public class UserController {
    Properties prop;

    public UserController(Properties prop) {
        this.prop = prop;
    }

    public Response userRegistration(UserModel userModel) {
        RestAssured.baseURI = prop.getProperty("baseURL");
        return given().contentType("application/json")
                .body(userModel)
                .when().post("/api/auth/register");
    }

    public Response adminLogin(UserModel userModel) {
        RestAssured.baseURI = prop.getProperty("baseURL");
        return given().contentType("application/json")
                .body(userModel)
                .when().post("/api/auth/login");
    }

    public Response userList() {
        RestAssured.baseURI = prop.getProperty("baseURL");
        return given().contentType("application/json")
                .header("Authorization", "Bearer " + prop.getProperty("Admintoken"))
                .when().get("/api/user/users");
    }

    public Response searchUser(String userId) {
        RestAssured.baseURI = prop.getProperty("baseURL");
        return given().contentType("application/json")
                .header("Authorization", "Bearer " + prop.getProperty("Admintoken"))
                .when().get("/api/user/" + userId);
    }

    public Response editNameAndPhnNo(UserModel userModel, String userId) {
        RestAssured.baseURI = prop.getProperty("baseURL");
        return given().contentType("application/json")
                .body(userModel)
                .header("Authorization", "Bearer " + prop.getProperty("Admintoken"))
                .when().put("/api/user/" + userId);
    }

    public Response userLogin(UserModel userModel) {
        RestAssured.baseURI = prop.getProperty("baseURL"); 
        return given().contentType("application/json")
                .body(userModel)
                .when().post("/api/auth/login");
    }

    public Response itemList() {
        RestAssured.baseURI = prop.getProperty("baseURL");
        return given().contentType("application/json")
                .header("Authorization", "Bearer " + prop.getProperty("Usertoken"))
                .when().get("/api/costs");
    }

    public Response addItem(UserModel userModel) {
        RestAssured.baseURI = prop.getProperty("baseURL");
        return given().contentType("application/json")
                .body(userModel)
                .header("Authorization", "Bearer " + prop.getProperty("Usertoken"))
                .when().post("/api/costs");
    }

    public Response editItemName(UserModel userModel, String purchaseId) {
        RestAssured.baseURI = prop.getProperty("baseURL");
        return given().contentType("application/json")
                .body(userModel)
                .header("Authorization", "Bearer " + prop.getProperty("Usertoken"))
                .when().put("/api/costs/" + purchaseId);
    }

    public Response deletItem(String purchaseId) {
        RestAssured.baseURI = prop.getProperty("baseURL");
        return given().contentType("application/json")
                .header("Authorization", "Bearer " + prop.getProperty("Usertoken"))
                .when().delete("/api/costs/" + purchaseId);
    }
    public Response adminLoginWithWrongEmailOrPassword(UserModel userModel) {
        RestAssured.baseURI = prop.getProperty("baseURL");
        return given().contentType("application/json")
                .body(userModel)
                .when().post("/api/auth/login");
    }
    public Response userListWithWrongToken() {
        RestAssured.baseURI = prop.getProperty("baseURL");
        return given().contentType("application/json")
                .header("Authorization", "Bearer " + prop.getProperty("AdmintokenInvalid"))
                .when().get("/api/user/users");
    }

    public Response searchUserByWringId(String WrongUserID) {
        RestAssured.baseURI = prop.getProperty("baseURL");
        return given().contentType("application/json")
                .header("Authorization", "Bearer " + prop.getProperty("Admintoken"))
                .when().get("/api/user/" + WrongUserID);
    }

    public Response editNameAndPhnNoWithInvaildId(UserModel userModel, String WrongUserID) {
        RestAssured.baseURI = prop.getProperty("baseURL");
        return given().contentType("application/json")
                .body(userModel)
                .header("Authorization", "Bearer " + prop.getProperty("Admintoken"))
                .when().put("/api/user/" + WrongUserID);
    }
    public Response userLoginInvalid(UserModel userModel) {
        RestAssured.baseURI = prop.getProperty("baseURL");  //manually call
        return given().contentType("application/json")
                .body(userModel)
                .when().post("/api/auth/login");
}
    public Response itemListWithWrongToken() {
        RestAssured.baseURI = prop.getProperty("baseURL");
        return given().contentType("application/json")
                .header("Authorization", "Bearer " + prop.getProperty("UsertokenInvalid"))
                .when().get("/api/user/users");
    }
    public Response addItemWithInvalidToken(UserModel userModel) {
        RestAssured.baseURI = prop.getProperty("baseURL");
        return given().contentType("application/json")
                .body(userModel)
                .header("Authorization", "Bearer " + prop.getProperty("UsertokenInvalid"))
                .when().post("/api/costs");
    }
    public Response editItemNameWithInvalidPurchaseID(UserModel userModel, String InvalidpurchaseId) {
        RestAssured.baseURI = prop.getProperty("baseURL");
        return given().contentType("application/json")
                .body(userModel)
                .header("Authorization", "Bearer " + prop.getProperty("Usertoken"))
                .when().put("/api/costs/" + InvalidpurchaseId);
    }
    public Response deletItemusingInvalidPurchaseId(String InvalidpurchaseId) {
        RestAssured.baseURI = prop.getProperty("baseURL");
        return given().contentType("application/json")
                .header("Authorization", "Bearer " + prop.getProperty("Usertoken"))
                .when().delete("/api/costs/" + InvalidpurchaseId);
    }
}
