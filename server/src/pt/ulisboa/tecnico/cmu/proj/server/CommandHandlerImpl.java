package pt.ulisboa.tecnico.cmu.proj.server;

import java.util.ArrayList;

import algorithm_data.Questions.AnswerQuiz;
import algorithm_data.Questions.Monument;
import algorithm_data.Questions.Question;
import algorithm_data.Questions.QuestionsByMonument;
import algorithm_data.Questions.Ranking;
import algorithm_data.Questions.RankingToSend;
import authentication.SessionId;
import authentication.User;
import pt.ulisboa.tecnico.cmu.proj.command.AnotherHello;
import pt.ulisboa.tecnico.cmu.proj.command.CheckRankingCommand;
import pt.ulisboa.tecnico.cmu.proj.command.Command;
import pt.ulisboa.tecnico.cmu.proj.command.CommandHandler;
import pt.ulisboa.tecnico.cmu.proj.command.DownloadQuizCommand;
import pt.ulisboa.tecnico.cmu.proj.command.GetRankingCommand;
import pt.ulisboa.tecnico.cmu.proj.command.HelloCommand;
import pt.ulisboa.tecnico.cmu.proj.command.ListLocationsCommand;
import pt.ulisboa.tecnico.cmu.proj.command.LogOutCommand;
import pt.ulisboa.tecnico.cmu.proj.command.LoginCommand;
import pt.ulisboa.tecnico.cmu.proj.command.SignInCommand;
import pt.ulisboa.tecnico.cmu.proj.command.UploadQuizCommand;
import pt.ulisboa.tecnico.cmu.proj.response.CheckrankingResponse;
import pt.ulisboa.tecnico.cmu.proj.response.DownloadQuizResponse;
import pt.ulisboa.tecnico.cmu.proj.response.GetRankingResponse;
import pt.ulisboa.tecnico.cmu.proj.response.HelloResponse;
import pt.ulisboa.tecnico.cmu.proj.response.ListLocationsResponse;
import pt.ulisboa.tecnico.cmu.proj.response.LogOutResponse;
import pt.ulisboa.tecnico.cmu.proj.response.LoginResponse;
import pt.ulisboa.tecnico.cmu.proj.response.Response;
import pt.ulisboa.tecnico.cmu.proj.response.SignInResponse;
import pt.ulisboa.tecnico.cmu.proj.response.UploadQuizResponse;

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
		 *           - Yes - GIVE BACK THE SAME SESSION ID
		 *           - No  - 2
		 *      2- User in the system?
		 *           - Yes - Create Session ID
		 *                   Insert in the login list
		 *           - No  - REJECT 
		 *           
		 */
				
		if( Server.loggedInUsers.userAlreadyLogin(user_code[0], user_code[1])==true ) {
			id = Server.loggedInUsers.getSessionId(new User(user_code[0], user_code[1]));
			message = "";
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
		 *                              No  - Update Authentication
		 *                                  - Insert User
		 *                                  - Update TotalRanking 
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
					Server.ranking.insert(tmp);
					id = "1"; //dummy id to preserve the protocol JsonHandler
					message = "User created";
				}
		
				
		String response = JsonHandler.LoginFromServer(id, message);

		return new SignInResponse(response);
	}
	@Override
	public Response handle(ListLocationsCommand hc) {
		/* Algorithm
		 * 1- Username-SessionID Pair is valid?
		 *    -No - REJECT
		 *    -YES- give locations
		 * 
		 * */
		ArrayList<String> message = new ArrayList<String>();
		String[] user_code = JsonHandler.ListCommandFromClient(hc.getMessage());
		
		User u = Server.users.checkExistance(user_code[1]);
		if(u==null) {
			message.add("Invalid User");
		}
		else {
			if( Server.loggedInUsers.userSessionIDcompare(u, user_code[0]) ){ //check same session id
				for(int i=0; i<Server.monuments.length; ++i) {
					message.add( Server.monuments[i].getMonumentDescription() );//add the description of each monument
				}
				
			}
		}
		
		String[] stockArr = new String[message.size()]; //ArrayList to array
		stockArr = message.toArray(stockArr);		
		String response = JsonHandler.ListCommandFromServer(user_code[0], user_code[1], stockArr );
		return new ListLocationsResponse(response);
	}
		
	@Override
	public Response handle(LogOutCommand hc) {
		/* Algorithm
		 * Remove client from loggins
		 * 
		 * */
		String[] user_code = JsonHandler.LogOutCommandFromClient( hc.getMessage() );
		
		User u = Server.users.checkExistance(user_code[1]);
		String message;
		if(u==null) {
			message = "Invalid user";
		}
		else {
			Server.loggedInUsers.removeFromLog(u); //Remove from loggins
			message = "Sucessful LogOut";
		}
		String response = JsonHandler.LogOutCommandFromServer("", "", message);
		return new LogOutResponse(response);
	}
	
	
	
	@Override
	public Response handle(CheckRankingCommand hc) {
		/*Algorithm
		 * 1- Is session id valid?
		 *    -NO - REJECT
		 *    -YES - Get correct answers by monument
		 *           Get Ranking by monument
		 *           Get Global ranking
		 * 
		 * */
		ArrayList<String> monuments = new ArrayList<String>();
		ArrayList<String> correctAnswers = new ArrayList<String>();
		ArrayList<String> rankByMonument = new ArrayList<String>();
		
		String global_rank = "0";
		
		String[] user_code = JsonHandler.CheckRankingCommandFromClient( hc.getMessage() );
		User u = Server.users.checkExistance(user_code[1]);
		
		
		String message;
		if(u==null) {
			message = "Invalid user";
		}
		else {
			if( Server.loggedInUsers.userSessionIDcompare(u, user_code[0]) ){ //check same session id
				for(int i=0; i<Server.monuments.length; ++i) {
					int points = u.getPoints().getNumberOfCorrectQuestionsAnswered(Server.monuments[i]);
					if( points==0 ) {
						continue;
					}
					else {
						monuments.add( Server.monuments[i].getMonumentDescription() );
						correctAnswers.add(String.valueOf(points));
						Ranking[] tmp = Server.ranking.sortByMonument(Server.monuments[i]);
						for(int j=0; j<tmp.length;++j) {
							if(tmp[j].getUser().equals(u.getName())) {
								rankByMonument.add( String.valueOf(tmp[j].getPoints()) );
							}
						}	
					}
				}
				Ranking[] tmp = Server.ranking.global();
				for(int i=0; i<tmp.length;++i) {
					if(tmp[i].getUser().equals(user_code[1])) {
						global_rank = String.valueOf(i);
						break;
					}
				}	
				
			}
		}
		
		String[] monumentsArr = new String[monuments.size()]; //ArrayList to array
		String[] correctAnswersArr = new String[correctAnswers.size()]; //ArrayList to array
		String[] rankByMonumentArr = new String[rankByMonument.size()]; //ArrayList to array
		
		monumentsArr = monuments.toArray(monumentsArr);
		correctAnswersArr = correctAnswers.toArray(correctAnswersArr);
		rankByMonumentArr = rankByMonument.toArray(rankByMonumentArr);
		String response = JsonHandler.CheckRankingCommandFromServer(user_code[0], user_code[0], monumentsArr, correctAnswersArr, rankByMonumentArr, global_rank);
		return new CheckrankingResponse(response);
	}
	
	
	
	@Override
	public Response handle(DownloadQuizCommand hc) {
		/*Algorithm
		 * 1- Is session id valid?
		 *    -NO  - REJECT
		 *    -YES - Get Quiz
		 * 
		 * */
		String[] user_code = JsonHandler.DownloadQuizCommandFromClient( hc.getMessage() );
		User u = Server.users.checkExistance(user_code[1]);
		String message;
		if(u==null) {
			message = "Invalid user";
		}
		else {
			if( Server.loggedInUsers.userSessionIDcompare(u, user_code[0]) ){ //check same session id
				QuestionsByMonument qbm = Server.quizzes[ Integer.parseInt(user_code[2]) ];
				message = JsonHandler.DownloadQuizCommandFromServer(user_code[0], user_code[1], qbm);
			}
			else {
				message = "Invalid Session Id";
			}
		}
				
		return new DownloadQuizResponse(message);
	}
	
	
	@Override
	public Response handle(UploadQuizCommand hc) {
		/*Algorithm
		 * 1- Is session id valid?
		 *    -NO  - REJECT
		 *    -YES - Calculate Rank
		 * 
		 * */
		AnswerQuiz user_code = JsonHandler.UploadAnswerQuizFromClient(hc.getMessage());
		User u = Server.users.checkExistance(user_code.getUsername());//transform in user object
		String message;
		if(u==null) {
			message = "Invalid user";
		}
		else {
			if( Server.loggedInUsers.userSessionIDcompare(u, user_code.getSessionID()) ){ //check same session id
				//Get monument
				QuestionsByMonument qbm =  Server.quizzes[ Integer.parseInt(user_code.getPlaceOfDownload())-1 ];
				
				int total = 0;
				for(int i=0; i<qbm.getSize(); ++i) {
					Question q_tmp = qbm.getQuestion(i);
					total += q_tmp.getNumCorrectAnswers(user_code.getVals(i));
				}
				//Update ranking
				u.getPoints().addPoints_byMonument(Server.monuments[Integer.parseInt(user_code.getPlaceOfDownload())-1], qbm.getSize(), total, user_code.getTimeStamp());
				Server.ranking.insert(u);//ranking updated
				Server.users.insert(u);//user updated
				message = JsonHandler.UploadAnswerQuizFromServer(user_code.getSessionID(), user_code.getUsername(), Server.monuments[Integer.parseInt(user_code.getPlaceOfDownload())-1].getMonumentDescription(), String.valueOf(qbm.getSize()), String.valueOf(total), Long.toString(user_code.getTimeStamp()));
			}
			else {
				message = "Invalid Session Id";
			}
		}
				
		return new UploadQuizResponse(message);
	}
	
	@Override
	public Response handle(GetRankingCommand hc) {
		/*Algorithm
		 * 1- Is session id valid?
		 *    -NO  - REJECT
		 *    -YES - Calculate Rank
		 * 
		 * */
		String[] user_code = JsonHandler.GetRankingFromClient(hc.getMessage());
		User u = Server.users.checkExistance(user_code[1]);//transform in user object
		String message = "";
		RankingToSend rts = null;
		
		if(u==null) {
			message = "Invalid user";
		}
		else {
			if( Server.loggedInUsers.userSessionIDcompare(u, user_code[0]) ){ //check same session id
				//Sort by monument
				
				Ranking[] r = Server.ranking.global(); //Sorted global
				for(int j=0; j<r.length; ++j) {
					if(r[j].getUser().equals(u.getName())) {
						rts = new RankingToSend( String.valueOf(j+1) ); //globalvalue
						break;
					}
				}
				
				
				if(rts==null) { //not existent
					rts = new RankingToSend( "-" ); //globalvalue
					rts.insert("-", "-", "-", "-");
				}
				else {
					boolean flag = false;
					for(int i=0; i<Server.monuments.length; ++i) {
						r = Server.ranking.sortByMonument(Server.monuments[i]); //Sorted by monument
						for(int j=0; j<r.length; ++j) {
							if(r[j].getUser().equals(u.getName())) {
								rts.insert(Server.monuments[i].getMonumentDescription(), String.valueOf(u.getPoints().getNumberOfQuestionsAnswered(Server.monuments[i])), String.valueOf(u.getPoints().getNumberOfCorrectQuestionsAnswered(Server.monuments[i])), String.valueOf(r[j].getPoints()));
								flag = true;
								break;
							}
						}
						if(flag) {
							break;
						}
					}
				}
				message = JsonHandler.GetRankingFromServer(user_code[0], user_code[1], rts);
				
			}
		}
				
		return new GetRankingResponse(message);
	}
}
