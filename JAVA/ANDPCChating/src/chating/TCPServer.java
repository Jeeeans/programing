package chating;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TCPServer {
	public static final int ServerPort = 9999;
	public static final String ServerIP = "118.219.188.71";
	static String msg;
	static Scanner scanner = new Scanner(System.in);

	static readThread rt;

	public static void main(String[] args) {
		// Thread desktopServerThread = new Thread(new TCPServer());
		// desktopServerThread.run();
		try {
			System.out.println("S: Connecting...");
			ServerSocket serverSocket = new ServerSocket(ServerPort);
			while (true) {

				System.out.println("S: Waiting ");
				Socket client = serverSocket.accept();
				System.out.println("S: Accept!");
				rt = new readThread(client);
				rt.start();

				PrintWriter out = new PrintWriter(new OutputStreamWriter(
						client.getOutputStream()), true);

				try {
					msg = scanner.nextLine();
					while (!msg.equalsIgnoreCase("exit")) {
						out.println(msg);
						msg = scanner.nextLine();
					}
				} catch (Exception e) {
					System.out.println("S: Client disconnected");
					e.printStackTrace();
				} finally {
					client.close();
					System.out.println("S: Done.");
				}
			}
		} catch (Exception e) {
			System.out.println("S: " + e);
			e.printStackTrace();
		}
	}
}

class readThread extends Thread {
	Socket client;
	String msg;
	Scanner read;

	public readThread(Socket client) {
		this.client = client;
		try {
			read = new Scanner(client.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

	public void run() {
		while (true) {
			if (read.hasNext()) {
				System.out.println("2");
				msg = read.nextLine();
				if (msg.equalsIgnoreCase("exit"))
					break;
				System.out.println("3");
				System.out.println("Client Message : " + msg);
			}
		}
	}
}