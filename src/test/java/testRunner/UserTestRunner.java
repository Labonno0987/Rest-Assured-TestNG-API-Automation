package testRunner;

import controller.UserController;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import setup.Setup;
import setup.UserModel;
import utils.Utils;

import javax.naming.ConfigurationException;
import java.time.LocalDate;
import java.util.List;


public class UserTestRunner extends Setup {

    @Test(priority = 1,description = "User Registration")
    public void userRegistration() throws ConfigurationException, org.apache.commons.configuration.ConfigurationException {
        UserController userController = new UserController(prop);
        UserModel userModel=new UserModel();

       // Faker faker=new Faker();
        String firstName="Mina";
        String lastName="Islam";
        String email="amivalo"+ Utils.RandomNum(1000,9999)+"@test.com";
        String password= "abc123";
        String phoneNumber="01906"+ Utils.RandomNum(100000,999999);
        String address="Dhaka";
        String gender="Female";
        boolean termsAccepted=true;

        userModel.setFirstName(firstName);
        userModel.setLastName(lastName);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setPhoneNumber(phoneNumber);
        userModel.setAddress(address);
        userModel.setGender(gender);
        userModel.setTermsAccepted(termsAccepted);

        Response res = userController.userRegistration(userModel);
        System.out.println(res.asString());

        JsonPath jsonObj = res.jsonPath();
        String Usertoken = jsonObj.get("token");
        System.out.println(Usertoken);
        Utils.setEnvVar("Usertoken", Usertoken);

        String userId=jsonObj.get("_id");
        System.out.println(userId);
        Utils.setEnvVar("userId",userId);

        String userEmail=jsonObj.get("email");
        System.out.println(userEmail);
        Utils.setEnvVar("userEmail",userEmail);

        Assert.assertEquals(res.statusCode(), 201, "Status code should be 201");
    }

    @Test(priority = 2,description = "Admin Login")
        public void adminLogin() throws ConfigurationException, org.apache.commons.configuration.ConfigurationException {
            UserController userController = new UserController(prop);
            UserModel userModel=new UserModel();
            userModel.setEmail("admin@test.com");
            userModel.setPassword("admin123");
            Response res = userController.adminLogin(userModel);

        JsonPath jsonObj =res.jsonPath();
        String Admintoken=jsonObj.get("token");
        System.out.println(Admintoken);
        Utils.setEnvVar("Admintoken",Admintoken);

        Assert.assertEquals(res.statusCode(), 200, "Status code should be 200");
        }
    @Test(priority = 3,description = "Get User List")
    public void userList() throws ConfigurationException, org.apache.commons.configuration.ConfigurationException {
        UserController userController = new UserController(prop);
        Response res = userController.userList();
        System.out.println(res.asString());

        Assert.assertEquals(res.statusCode(), 200, "Status code should be 200");
    }
    @Test(priority = 4,description = "Search User by Id")
    public void searchUser() {
        UserController userController=new UserController(prop);
        Response res=userController.searchUser(prop.getProperty("userId"));
        System.out.println(res.asString());

        Assert.assertEquals(res.statusCode(), 200, "Status code should be 200");
    }
    @Test(priority = 5,description = "Edit User information using FirstName and Phone Number")
    public void editNameAndPhnNo(){
        UserController userController = new UserController(prop);
        Response searchResponse = userController.searchUser(prop.getProperty("userId"));
        JsonPath json = searchResponse.jsonPath();
        UserModel userModel =new UserModel();
        userModel.setFirstName("Anon");
        userModel.setPhoneNumber("01906405364");
        userModel.setLastName(json.getString("lastName"));
        userModel.setEmail(json.getString("email"));
        userModel.setPassword(json.getString("password"));
        userModel.setAddress(json.getString("address"));
        userModel.setGender(json.getString("gender"));
        userModel.setTermsAccepted(json.getBoolean("termsAccepted"));
        Response res = userController.editNameAndPhnNo(userModel,prop.getProperty("userId"));
        System.out.println(res.asString());

        Assert.assertEquals(res.statusCode(), 200, "Status code should be 200");
    }
    @Test(priority = 6,description = "User Login")
    public void userLogin(){
        UserController userController = new UserController(prop);
        UserModel userModel=new UserModel();
        userModel.setEmail(prop.getProperty("userEmail"));
        userModel.setPassword("abc123");
        Response res = userController.userLogin(userModel);
        System.out.println(res.asString());

        Assert.assertEquals(res.statusCode(), 200, "Status code should be 200");
    }
    @Test(priority = 7,description = "Get Item List")
    public void itemList() throws ConfigurationException, org.apache.commons.configuration.ConfigurationException {
        UserController userController = new UserController(prop);
        Response res = userController.itemList();
        System.out.println(res.asString());
        Assert.assertEquals(res.statusCode(), 200, "Status code should be 200");
    }
    @Test(priority = 8,description = "User Add Item")
    public void addItem() throws ConfigurationException, org.apache.commons.configuration.ConfigurationException {
        UserController userController = new UserController(prop);
        UserModel userModel=new UserModel();

        String itemName="Apple";
        Integer quantity=5;
        Double amount=20.0;
        String purchaseDate= "2025-04-30";
        String month="April";
        String remarks="Must be fresh";

        userModel.setItemName(itemName);
        userModel.setQuantity(quantity);
        userModel.setAmount(amount);
        userModel.setPurchaseDate(purchaseDate);
        userModel.setMonth(month);
        userModel.setRemarks(remarks);

        Response res = userController.addItem(userModel);

        System.out.println(res.asString());
        JsonPath jsonObj =res.jsonPath();
        String purchaseId=jsonObj.get("_id");
        System.out.println(purchaseId);
        Utils.setEnvVar("purchaseId",purchaseId);

        String addItem= jsonObj.prettify();
        System.out.println(addItem);
        Utils.setEnvVar("addItem", addItem);
        Assert.assertEquals(res.statusCode(), 201, "Status code should be 201");
    }
    @Test(priority = 9,description = "Edit Item Name")
    public void editItemName(){
        UserController userController = new UserController(prop);
        Response itemListResponse = userController.itemList();
        JsonPath json = itemListResponse.jsonPath();

        UserModel userModel =new UserModel();
        userModel.setItemName("Banana");

        List<Integer> quantityList = json.getList("quantity");
        userModel.setQuantity(quantityList.get(0));

        List<Number> amountList = json.getList("amount");
        userModel.setAmount(amountList.get(0).doubleValue());

        List<String> purchaseDateList = json.getList("purchaseDate");
        userModel.setPurchaseDate(purchaseDateList.get(0));
        userModel.setMonth(json.getString("month"));
        userModel.setRemarks(json.getString("remarks"));

        Response res = userController.editItemName(userModel,prop.getProperty("purchaseId"));
        System.out.println(res.asString());
        Assert.assertEquals(res.statusCode(), 200, "Status code should be 200");
    }
    @Test(priority = 10,description = "Delete Any Item")
    public void deletItem() {
        UserController userController = new UserController(prop);
        Response res=userController.deletItem(prop.getProperty("purchaseId"));
        System.out.println(res.asString());

        JsonPath jsonObj=res.jsonPath();
        String ActualMsg=jsonObj.get("message");
        String ExpectedMsg="Cost deleted successfully";

        SoftAssert softAssert=new SoftAssert();
        softAssert.assertTrue(ActualMsg.contains(ExpectedMsg));
        //Assert.assertEquals(res.statusCode(), 200, "Status code should be 200");
    }

    //All negative test cases:

    @Test(priority = 11,description = "User already exists with this email address")
    public void UserRegistrationWithExistEmail() throws ConfigurationException, org.apache.commons.configuration.ConfigurationException {
        UserController userController = new UserController(prop);
        UserModel userModel=new UserModel();

        // Faker faker=new Faker();
        String firstName="Ciara";
        String lastName="Hammes";
        String email="thik@4645233";
        String password= "123";
        String phoneNumber="01906"+ Utils.RandomNum(100000,999999);
        String address="Dhaka";
        String gender="Female";
        boolean termsAccepted=true;

        userModel.setFirstName(firstName);
        userModel.setLastName(lastName);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setPhoneNumber(phoneNumber);
        userModel.setAddress(address);
        userModel.setGender(gender);
        userModel.setTermsAccepted(termsAccepted);

        Response res = userController.userRegistration(userModel);
        System.out.println(res.asString());

        JsonPath jsonObj=res.jsonPath();
        String ActualMsg=jsonObj.get("message");
        String ExpectedMsg="User already exists with this email address";

        SoftAssert softAssert=new SoftAssert();
        softAssert.assertTrue(ActualMsg.contains(ExpectedMsg));
        Assert.assertEquals(res.statusCode(), 400, "Status code should be 400");
    }
    @Test(priority = 12,description = "Admin Login with Wrong Email")
    public void adminLoginWithWrongEmailOrPassword() throws ConfigurationException, org.apache.commons.configuration.ConfigurationException {
        UserController userController = new UserController(prop);
        UserModel userModel=new UserModel();
        userModel.setEmail("dmin@test.com");
        userModel.setPassword("admin123");
        Response res = userController.adminLoginWithWrongEmailOrPassword(userModel);

        JsonPath jsonObj=res.jsonPath();
        String ActualMsg=jsonObj.get("message");
        String ExpectedMsg="User already exists with this email address";

        SoftAssert softAssert=new SoftAssert();
        softAssert.assertTrue(ActualMsg.contains(ExpectedMsg));
        Assert.assertEquals(res.statusCode(), 401, "Status code should be 401");
    }
    @Test(priority = 13,description = "Get User List With Invalid Admin Token")
    public void userListWithAuthorizedAdmin() {
        UserController userController = new UserController(prop);
        Response res = userController.userListWithWrongToken();
        System.out.println(res.asString());

        JsonPath jsonObj=res.jsonPath();
        String ActualMsg=jsonObj.get("message");
        String ExpectedMsg="Not authorized, token failed";

        SoftAssert softAssert=new SoftAssert();
        softAssert.assertTrue(ActualMsg.contains(ExpectedMsg));

        Assert.assertEquals(res.statusCode(), 401, "Status code should be 401");
    }
    @Test(priority = 14,description = "Search User by Wrong User Id")
    public void searchUserByWrongUserId() {
        UserController userController=new UserController(prop);
        Response res=userController.searchUserByWringId(prop.getProperty("WrongUserID"));
        System.out.println(res.asString());

        JsonPath jsonObj=res.jsonPath();
        String ActualMsg=jsonObj.get("message");
        String ExpectedMsg="User not found";

        SoftAssert softAssert=new SoftAssert();
        softAssert.assertTrue(ActualMsg.contains(ExpectedMsg));

        Assert.assertEquals(res.statusCode(), 404, "Status code should be 404");
    }
    @Test(priority = 15,description = "Edit User information using FirstName and Phone Number Using Invaild Id")
    public void editNameAndPhnNoForInvaildId(){
        UserController userController = new UserController(prop);
        UserModel userModel = new UserModel();

        // The response from UserList() exceeds the server's body size limit, causing the PayloadTooLargeError. So I have to do it in such way

        userModel.setFirstName("Anon");
        userModel.setPhoneNumber("123557676");
        userModel.setLastName(prop.getProperty("lastName"));
        userModel.setEmail(prop.getProperty("email"));
        userModel.setPassword(prop.getProperty("password"));
        userModel.setAddress(prop.getProperty("address"));
        userModel.setGender(prop.getProperty("gender"));
        userModel.setTermsAccepted(Boolean.parseBoolean(prop.getProperty("termsAccepted")));
        Response res = userController.editNameAndPhnNoWithInvaildId(userModel,prop.getProperty("WrongUserID"));
        System.out.println(res.asString());


        JsonPath jsonObj=res.jsonPath();
        String ActualMsg=jsonObj.get("message");
        String ExpectedMsg="User not found";

        SoftAssert softAssert=new SoftAssert();
        softAssert.assertTrue(ActualMsg.contains(ExpectedMsg));

        Assert.assertEquals(res.statusCode(), 404, "Status code should be 404");
    }
   @Test(priority = 16,description = "Invalid User Login using Wrong Email or Password")
    public void userLoginInvalid(){
        UserController userController = new UserController(prop);
        UserModel userModel=new UserModel();
        //userModel.setEmail(prop.getProperty("userEmail"));
        //userModel.setPassword("bc12");
       userModel.setEmail("something@email.com");
       userModel.setPassword("wrongPassword");
        Response res = userController.userLoginInvalid(userModel);
        System.out.println(res.asString());

       JsonPath jsonObj=res.jsonPath();
       String ActualMsg=jsonObj.get("message");
       String ExpectedMsg="Invalid email or password";

       SoftAssert softAssert=new SoftAssert();
       softAssert.assertTrue(ActualMsg.contains(ExpectedMsg));

       Assert.assertEquals(res.statusCode(), 401, "Status code should be 401");
    }
    @Test(priority = 17,description = "Get Item List with Wrong User Token")
    public void itemListWithWrongToken() {
        UserController userController = new UserController(prop);
        Response res = userController.itemListWithWrongToken();
        System.out.println(res.asString());

        JsonPath jsonObj=res.jsonPath();
        String ActualMsg=jsonObj.get("message");
        String ExpectedMsg="Not authorized, token failed";

        SoftAssert softAssert=new SoftAssert();
        softAssert.assertTrue(ActualMsg.contains(ExpectedMsg));

        Assert.assertEquals(res.statusCode(), 401, "Status code should be 401");
    }
    @Test(priority = 18,description = "User Add Item With Invalid User Token")
    public void addItemWithInvalidToken() {
        UserController userController = new UserController(prop);
        UserModel userModel=new UserModel();

        String itemName="Apple";
        Integer quantity=5;
        String amount="11";
        String purchaseDate= "2025-04-30";
        String month="April";
        String remarks="Must be fresh";

        userModel.setItemName(itemName);
        userModel.setQuantity(quantity);
        userModel.setWrongamount(amount);
        userModel.setPurchaseDate(purchaseDate);
        userModel.setMonth(month);
        userModel.setRemarks(remarks);

        Response res = userController.addItemWithInvalidToken(userModel);
        System.out.println(res.asString());

        JsonPath jsonObj=res.jsonPath();
        String ActualMsg=jsonObj.get("message");
        String ExpectedMsg="Not authorized, token failed";

        SoftAssert softAssert=new SoftAssert();
        softAssert.assertTrue(ActualMsg.contains(ExpectedMsg));

        Assert.assertEquals(res.statusCode(), 401, "Status code should be 401");
    }

    @Test(priority = 19,description = "Edit Item Name With Invalid PurchaseID")
    public void editItemNameWithInvalidPurchaseID(){
        UserController userController = new UserController(prop);
        Response itemListResponse = userController.itemList();
        JsonPath json = itemListResponse.jsonPath();

        UserModel userModel =new UserModel();
        userModel.setItemName("Banana");

        userModel.setQuantity(Integer.parseInt(prop.getProperty("quantity")));
        userModel.setAmount(Double.parseDouble(prop.getProperty("amount")));
        userModel.setPurchaseDate(prop.getProperty("purchaseDate"));
        userModel.setMonth(new String(prop.getProperty("month")));
        userModel.setGender(prop.getProperty("remarks"));
        Response res = userController.editItemNameWithInvalidPurchaseID(userModel,prop.getProperty("InvalidpurchaseId"));
        System.out.println(res.asString());

        JsonPath jsonObj=res.jsonPath();
        String ActualMsg=jsonObj.get("message");
        String ExpectedMsg="Cost not found";

        SoftAssert softAssert=new SoftAssert();
        softAssert.assertTrue(ActualMsg.contains(ExpectedMsg));

        Assert.assertEquals(res.statusCode(), 404, "Status code should be 404");
    }
    @Test(priority = 20,description = "Delete Any Item using Invalid Purchase Id")
    public void deletItemusingInvalidPurchaseId() {
        UserController userController = new UserController(prop);
        Response res=userController.deletItemusingInvalidPurchaseId(prop.getProperty("InvalidpurchaseId"));
        System.out.println(res.asString());

        JsonPath jsonObj=res.jsonPath();
        String ActualMsg=jsonObj.get("message");
        String ExpectedMsg="Cost not found";

        SoftAssert softAssert=new SoftAssert();
        softAssert.assertTrue(ActualMsg.contains(ExpectedMsg));

        Assert.assertEquals(res.statusCode(), 404, "Status code should be 404");
    }

}
