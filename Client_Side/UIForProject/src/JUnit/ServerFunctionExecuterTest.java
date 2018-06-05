package JUnit;

import static org.junit.Assert.*;
import java.io.IOException;
import org.json.JSONException;
import org.junit.Test;
import Customer.CreateCustomerException;
import ServerFunctions.FromServerException;
import ServerFunctions.ServerFunctionExecuter;
import UI.InputCheck.inputCheck;
import Workers.Cashier;
import Workers.CreateWorkerException;
import Workers.eBranches;

public class ServerFunctionExecuterTest {

	@Test
	public void checkPhoneTest(){
		
		//----- check incorrect phone number ----
		String phone = new String("6503793797");
		String expected = "illegal action. Phone number must contain only legal phone number!";
		try{
			 inputCheck.checkPhone(phone);
		}
		catch(Exception exc){
			assertEquals(expected, exc.getMessage());
		}
		//----- check correct phone number ----	
		String correctPhone = new String("0526669999");
		try{
			 inputCheck.checkPhone(correctPhone);
		}
		catch(Exception exc){
			assertEquals(expected, exc.getMessage());
		}
		assertEquals("Worked", "Worked");
	}
	
	@Test
	public void checkNameTest(){
		
		//----- check incorrect name ----
		String name = new String("Y3ossi Aba");
		String expected = "illegal action. Name must contain only letters and spaces!";
		try{
			 inputCheck.checkName(name);
		}
		catch(Exception exc){
			assertEquals(expected, exc.getMessage());
		}
		//----- check correct name ----	
		String correctName = new String("Yossi Shalom");
		try{
			 inputCheck.checkName(correctName);
		}
		catch(Exception exc){
			assertEquals(expected, exc.getMessage());
		}
		assertEquals("Worked", "Worked");
	}
	
	@Test
	public void checkAmountTest(){
		
		//----- check incorrect amount ----
		String amount = new String("333a");
		String expected = "illegal action. amount must contain only digits!";
		try{
			 inputCheck.checkAmount(amount);
		}
		catch(Exception exc){
			assertEquals(expected, exc.getMessage());
		}
		//----- check correct amount ----	
		String correctAmount = new String("33");
		try{
			 inputCheck.checkName(correctAmount);
		}
		catch(Exception exc){
			assertEquals(expected, exc.getMessage());
		}
		assertEquals("Worked", "Worked");
	}
	
	@Test
	public void checkIdTest(){
		String field = "id";
		//----- check incorrect id ----
		String id = new String("Aba");
		String expected = "illegal action in field :" + field + ". must contain only digits!";
		try{
			 inputCheck.checkId(id, field);
		}
		catch(Exception exc){
			assertEquals(expected, exc.getMessage());
		}
		//----- check correct id ----	
		String correctId = new String("333");
		try{
			 inputCheck.checkName(correctId);
		}
		catch(Exception exc){
			assertEquals(expected, exc.getMessage());
		}
		assertEquals("Worked", "Worked");
	}
	
	@Test
	public void checkDiscountTest(){
		
		//----- check incorrect discount ----
		String discount = new String("100");
		String expected = "illegal action. Discount % must contain only numbers between 0-99!";
		try{
			 inputCheck.checkDiscount(discount);
		}
		catch(Exception exc){
			assertEquals(expected, exc.getMessage());
		}
		//----- check correct discount ----	
		String correctDiscount = new String("33");
		try{
			 inputCheck.checkDiscount(correctDiscount);
		}
		catch(Exception exc){
			assertEquals(expected, exc.getMessage());
		}
		assertEquals("Worked", "Worked");
	}
	
	@Test
	public void checkPassTest(){
		
		//----- check incorrect discount ----
		String pass = new String("2a");
		String expected = "illegal action. Password must contain 3-8 digits or letters!";
		try{
			 inputCheck.checkPass(pass);
		}
		catch(Exception exc){
			assertEquals(expected, exc.getMessage());
		}
		//----- check correct discount ----	
		String correctPass = new String("abcd");
		try{
			 inputCheck.checkPass(correctPass);
		}
		catch(Exception exc){
			assertEquals(expected, exc.getMessage());
		}
		assertEquals("Worked", "Worked");
	}
	
	@Test
	public void SignInTest(){
		
		//----  Correct Sign In   ------
		String user = new String("3");
		String Pass = new String("123");
		String jsonExpecteduserId = "202958473";
				try {
			assertEquals(jsonExpecteduserId, ServerFunctionExecuter.getInstance().SignIn(user, Pass).GetJSONObject().getString("ID"));
		}
		catch (JSONException | IOException | FromServerException | CreateWorkerException ignore){}
		
		//logout
		try {
			ServerFunctionExecuter.getInstance().SignOut();
		} 
		catch (IOException | FromServerException ignore){}
		
		//----  Incorrect Sign In   ------
		String IncorrectPass = new String("12");
		String expectedMessage = new String("Worker number or Password are incorrect!");
		try {
			assertEquals(jsonExpecteduserId, ServerFunctionExecuter.getInstance().SignIn(user, IncorrectPass).GetJSONObject().getString("ID"));
		}
		catch (JSONException | IOException | FromServerException | CreateWorkerException e){
			assertEquals(expectedMessage, e.getMessage());
		}
		
		//logout
		try {
			ServerFunctionExecuter.getInstance().SignOut();
		}
		catch (IOException | FromServerException ignore){}
	}
	
	@Test
	public void getCustomerByIdTest(){
		//login
		try{
			ServerFunctionExecuter.getInstance().SignIn("2", "123");
		}
		catch (JSONException | IOException | FromServerException | CreateWorkerException e1){
		}
		//----  Correct getCustomerById  ----
		String id = "205993943";
		String jsonExpecteduserId = "205993943";
		try {
			assertEquals(jsonExpecteduserId, ServerFunctionExecuter.getInstance().GetCustomerById(id).GetJSONObject().getString("ID"));
		} 
		catch (JSONException | IOException | FromServerException | CreateCustomerException ignore) {}
		
		//----  Incorrect getCustomerById  ----
		String incorrectId = "555";
		String expectedMessage = new String("Couldnt Finde The Values :\nID : 555 , ");
		try {
			assertEquals(jsonExpecteduserId, ServerFunctionExecuter.getInstance().GetCustomerById(incorrectId).GetJSONObject().getString("ID"));
		}
		catch (JSONException | IOException | FromServerException | CreateCustomerException e){
			assertEquals(expectedMessage, e.getMessage());
		}
		
		//logout
		try {
			ServerFunctionExecuter.getInstance().SignOut();
		}
		catch (IOException | FromServerException ignore){}
	}
	
	@Test
	public void addNewWorkerTest(){
		//login
		try{
			ServerFunctionExecuter.getInstance().SignIn("2", "123");
		} 
		catch (JSONException | IOException | FromServerException | CreateWorkerException e1) {}
		//---- Trying to add new worker with existing id ----
		
		eBranches branch = null;
		
		try{
			branch = ServerFunctionExecuter.getInstance().GetLoggedInUser().getBranch();
		}
		catch (IOException e){}
		
		Cashier newCashier = new Cashier("203885300","Oren","Hazan","0501774328", branch, "11", "2876334");
		String jsonExpectedMessage = "ID : 203885300 all ready exists!";
		try{
			ServerFunctionExecuter.getInstance().AddWorker(newCashier, "123");
		} 
		catch (IOException | FromServerException e){
			assertEquals(jsonExpectedMessage, e.getMessage());	
		}
				
		//logout
		try{
			ServerFunctionExecuter.getInstance().SignOut();
		}
		catch (IOException | FromServerException ignore){}
	}
		
	@Test
	public void CheckLoggedInUserTest(){
		//login
		try{
			ServerFunctionExecuter.getInstance().SignIn("2", "123");
		} 
		catch (JSONException | IOException | FromServerException | CreateWorkerException e1) {}
		//----  Check Logged In User  ----
		String jsonExpecteduser = "Tom Halfon (204938281)";
		try {
			assertEquals(jsonExpecteduser, ServerFunctionExecuter.getInstance().GetLoggedInUser().toString());
		} 
		catch (IOException e) {}
		
		//logout
		try{
			ServerFunctionExecuter.getInstance().SignOut();
		}
		catch (IOException | FromServerException ignore){}
	}
	@Test
	public void checkSignOutTest(){
		//login
		try{
			ServerFunctionExecuter.getInstance().SignIn("2", "123");
		} 
		catch (JSONException | IOException | FromServerException | CreateWorkerException e1) {}
		//---- Log Out ----
		try {
			ServerFunctionExecuter.getInstance().SignOut();
		}
		catch (IOException | FromServerException e) {}
		String result = "Worked!";
		try {
			if(ServerFunctionExecuter.getInstance().GetLoggedInUser() == null);
				result = "Failed!";
		}
		catch (IOException e){
		}
		assertEquals("Failed!", result);
	}
}//end of ServerFunctionExecuterTest