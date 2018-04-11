package pt.ulisboa.tecnico.cmu.proj.server;

import authentication.SessionId;
import authentication.User;
import pt.ulisboa.tecnico.cmu.proj.command.AnotherHello;
import pt.ulisboa.tecnico.cmu.proj.command.Command;
import pt.ulisboa.tecnico.cmu.proj.command.CommandHandler;
import pt.ulisboa.tecnico.cmu.proj.command.HelloCommand;
import pt.ulisboa.tecnico.cmu.proj.command.LoginCommand;
import pt.ulisboa.tecnico.cmu.proj.command.SignInCommand;
import pt.ulisboa.tecnico.cmu.proj.response.HelloResponse;
import pt.ulisboa.tecnico.cmu.proj.response.LoginResponse;
import pt.ulisboa.tecnico.cmu.proj.response.Response;
import pt.ulisboa.tecnico.cmu.proj.response.SignInResponse;

public class CommandHandlerImpl implements CommandHandler {

	@Override
	public Response handle(HelloCommand hc) {
		System.out.println("Received: " + hc.getMessage());
		return new HelloResponse("Hi from Server!");
	}
	@Override
	public Response handle(AnotherHello hc) {
		//System.out.println("Received: " + hc.getMessage());
		//User tmp = Server.u; //Test
		return new HelloResponse("Hello from Server!");
	}
	@Override
	public Response handle(LoginCommand hc) {
		String[] user_code = JsonHandler.LoginFromClient(hc.getMessage());
		
		//For JSON response
		String message;
		String id;
		/*Algorithm:
		 *      1- User already login?
		 *           - Yes - REJECT
		 *           - No  - 2
		 *      2- User in the system?
		 *           - Yes - Create Session ID
		 *                   Insert in the login list
		 *           - No  - REJECT 
		 *           
		 */
				
		if( Server.loggedInUsers.userAlreadyLogin(user_code[0], user_code[1])==true ) {
			message = "User Already Log In";
			id = "0";
		}
		else {
			User tmp = new User(user_code[0], user_code[1]);
			if( Server.users.get( tmp ) >=0 ){
				id = String.valueOf( SessionId.getAnotherSession() );
				message = "";
				Server.loggedInUsers.insertLogIn(tmp, id);
			}
			else {
				message = "User Not In the System";
				id = "0";
			}
		}
		
		String response = JsonHandler.LoginFromServer(id, message);
		
		return new LoginResponse(response);
	}
	@Override
	public Response handle(SignInCommand hc) {
		String[] user_code = JsonHandler.LoginFromClient(hc.getMessage());
		
		//For JSON response
		String message;
		String id = "0";
		/*Algorithm:
		 *      1- User in the system?
		 *           - No - UserName already exists?
		 *                      - Yes - REJECT
		 *                        No  - Password already exists?
		 *                              Yes - REJECT
		 *                              No  - Insert User 
		 *           
		 */
				
		
		User tmp = new User(user_code[0], user_code[1]);
		if( Server.users.get( tmp ) >=0 ){
			message = "User already Exists";
		}
		else
			if( Server.collectionUsersPasswordInSystem.checkUserName(user_code[0])==true ) {
				message = "Username already taken";
			}
			else
				if(Server.collectionUsersPasswordInSystem.checkCode(user_code[1])==true) {
					message = "Code already in the system";
				}
				else {
					Server.collectionUsersPasswordInSystem.insertUser(user_code[0], user_code[1]);
					Server.users.insert(tmp);
					id = "1";
					message = "User created";
				}
		
				
		String response = JsonHandler.LoginFromServer(id, message);

		return new SignInResponse(response);
	}
}
