package UI.InputCheck;

public class inputCheck{	
	
	private static Boolean checkIfMatch(String checkValue, String pattern){
		if((checkValue.toString().matches(pattern)))
			return true;
		return false;	
	}
	
	public static void checkPhone(String phoneNumber) throws inputException{
		String pattern = "05(0|2|3|4|5|8)[-]?\\d{3}[-]?\\d{4}";
		if(!checkIfMatch(phoneNumber, pattern)){
			throw new inputException("illegal action. Phone number must contain only legal phone number!");
		}
	}
	
	public static void checkEnoughAmount(int amountToCheck,int maxAmount) throws inputException{
		if(amountToCheck > maxAmount)
		{
			throw new inputException(String.format("illegal action. Max amount is %d!",maxAmount));
		}
	}
	
	public static void checkName(String name) throws inputException{
		String pattern = "([a-zA-Z\\s]+)";
		if(!checkIfMatch(name, pattern)){
			throw new inputException("illegal action. Name must contain only letters and spaces!");
		}
	}
	//checks id, bank number
	public static void checkId(String id, String field) throws inputException{
		String pattern = "([0-9]+)";
		if(!checkIfMatch(id, pattern)){
			throw new inputException("illegal action in field :" + field + ". must contain only digits!");
		}
	}
	
	public static void checkAmount(String amount) throws inputException{
		String pattern = "([0-9]{1,9})";
		if(!checkIfMatch(amount, pattern)){
			throw new inputException("illegal action. amount must contain only digits!");
		}
	}
	
	public static void checkDiscount(String discount) throws inputException{
		String pattern = "([0-9]{1,2})";
		if(!checkIfMatch(discount, pattern)){
			throw new inputException("illegal action. Discount % must contain only numbers between 0-99!");
		}
	}
	public static void checkPass(String pass) throws inputException{
		String pattern = "[\\w]{3,8}";
		if(!checkIfMatch(pass, pattern)){
			throw new inputException("illegal action. Password must contain 3-8 digits or letters!");
		}
	}
}//end of inputCheck
