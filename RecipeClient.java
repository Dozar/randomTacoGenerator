import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class RecipeClient {
	public static void main(String[] args) {

		 System.out.println("Recipe Client");

		 try {
			 System.out.println("Waiting for connection.....");
			 InetAddress localAddress = InetAddress.getLocalHost();

			 try {
				 Socket clientSocket = new Socket(localAddress, 9000);
				 BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

				 System.out.println("Connected to server");
				 Scanner scanner = new Scanner(System.in);

				 while (true) {
					 System.out.print("Enter term to search, number of recipes to return, and number of recipes to skip (burger,10,5):  ");
					 String inputLine = scanner.nextLine();
						if ("quit".equalsIgnoreCase(inputLine)) {
							break;
						}
						out.println(inputLine);

						String line = "", response = "";

						line = br.readLine();
						String[] recipes = line.split("`");
						for (String recipe : recipes) {
							response += recipe;
							response += "\n";
						}

						System.out.println("Server response:\n\n" + response);
					}
				 scanner.close();
				 clientSocket.close();
			 } catch (IOException ex) {
				 System.out.println(ex.getMessage());
			 }
		 } catch (IOException ex) {
			 System.out.println(ex.getMessage());
		 }
	 }
}
