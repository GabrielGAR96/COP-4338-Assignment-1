import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
	
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	private ArrayList<ClientHandler> clients;

	public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients) 
			throws IOException
	{
		this.client = clientSocket;
		this.clients = clients;
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		out = new PrintWriter(client.getOutputStream(), true);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			String id = in.readLine();
			while(true) 
				{
				String request = in.readLine();
				
				if(request == null || request.equals("LOGOUT")) return;
					outToAll(id,  request); 
				
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("IOException in client handler!");
			e.printStackTrace();
			} finally {
				out.close();
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	
	private void outToAll(String id, String request) {
		// TODO Auto-generated method stub
		for (ClientHandler clientHandler : clients) {
			clientHandler.out.println(id + " says: " + request);
		}
	}

}
