package controller;

public class Test {
		
	
	public static void main(String[] args) {
		
		LogInDAO loginDAO = new LogInDAO();
		
		loginDAO.checkUsernameExists("asen");
		
		//loginDAO.validateUsernameAndPassword("asen"	, "asen");
	}

}
