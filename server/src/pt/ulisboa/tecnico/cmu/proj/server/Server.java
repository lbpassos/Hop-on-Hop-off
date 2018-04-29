package pt.ulisboa.tecnico.cmu.proj.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import algorithm_data.Questions.Monument;
import algorithm_data.Questions.QuestionsByMonument;
import algorithm_data.Questions.TotalRanking;
import authentication.Authentication;
import authentication.LoggedUsers;
import authentication.User;
import authentication.UsersInSystem;
import pt.ulisboa.tecnico.cmu.proj.command.Command;
import pt.ulisboa.tecnico.cmu.proj.response.Response;

public class Server {
	
	private static final int PORT = 9090;
	
	/* Global variables */
	public static LoggedUsers loggedInUsers; //Users Logged
	public static Authentication collectionUsersPasswordInSystem; //Users(string) and associate passwords
	public static UsersInSystem users; //Users(objects) 
	public static Monument[] monuments;
	public static QuestionsByMonument[] quizzes;
	public static TotalRanking ranking;

	public static void main(String[] args) throws Exception {
		CommandHandlerImpl chi = new CommandHandlerImpl();
		ServerSocket socket = new ServerSocket(PORT);
		Socket client = null;
		
		loggedInUsers = new LoggedUsers(); //Users Logged
		collectionUsersPasswordInSystem = new Authentication(); //Users(string) and associate passwords
		users = new UsersInSystem(); //Users(objects) 
		
		/* Init Monuments */
		monuments = Init_Monuments.initialize();
		if(monuments==null) {
			socket.close();
			return;
		}
		/* Init Quizzes */
		quizzes = Init_Quizzes.initialize(monuments);
		if(quizzes==null) {
			socket.close();
			return;
		}
		
		/* Init Total Ranking */
		ranking = new TotalRanking();
		
		
		
		/* TEST insert user */
		User u1 = new User("hi","123");
		users.insert(u1);
		collectionUsersPasswordInSystem.insertUser("hi", "123");
		
		

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("Server now closed.");
				try { socket.close(); }
				catch (Exception e) { }
			}
		});
		
		System.out.println("Server is accepting connections at " + PORT);
		
		while (true) {
			try {
			client = socket.accept();
			
			
			ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
			Command cmd =  (Command) ois.readObject();
			Response rsp = cmd.handle(chi);
			
			ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
			oos.writeObject(rsp);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (client != null) {
					try { client.close(); }
					catch (Exception e) {}
				}
			}
		}
	}	
}
