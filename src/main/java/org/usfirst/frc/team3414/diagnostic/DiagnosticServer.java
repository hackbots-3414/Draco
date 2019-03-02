package org.usfirst.frc.team3414.diagnostic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class DiagnosticServer {
	// Special Thanks to
	// https//www.careerbless.com/samplecodes/java/beginners/socket/SocketBasic1.php
	private static Socket socket;
	static String userinput;
	static ServerSocket serverSocket;
	public static void init() {
		int port = 5800; // Following previous years, team ports have been between 5800-5810 for team use
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Server Started and listening to the port " + port);
		execute();

	}

	public static void execute() {
		while (true) {
			try {
				// Server is running always. This is done using this while(true) loop
				// everything below this line to the next comment was a while loop originally.

				// Reading the message from the client
				socket = serverSocket.accept();
				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String input = br.readLine();
				System.out.println("Message received from client is " + input);
				// input = input.toLowerif(DiagnosticServer.getInput().equalsIgnoreCase(();
				userinput = input;
				String returnMessage = input;

				/*
				 * try { int numberInIntFormat = Integer.parseInt(input); int returnValue =
				 * numberInIntFormat*2; returnMessage = String.valueOf(returnValue) + "\n"; }
				 * catch(NumberFormatException e) { //Input was not a number. Sending proper
				 * message back to client. returnMessage = "Please send a proper number\n"; }
				 */

				// Sending the response back to the client.
				OutputStream os = socket.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os);
				BufferedWriter bw = new BufferedWriter(osw);
				bw.write(returnMessage);
				System.out.println("Message sent to the client is " + returnMessage);
				bw.flush();
			}

			catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					socket.close();
				} catch (Exception e) {
				}
			}
		}

	}

	public static void sendMessage(String message) {
		try {
			socket = serverSocket.accept();
			OutputStream os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write(message);
			System.out.println("Message sent to the client is " + message);
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getInput() {
		return userinput;
	}
	/*
	 * public static void executeLoop(String[] args) { //DON'T USE while(true) { try
	 * {
	 * 
	 * int port = 5800; // Following previous years, team ports have been between
	 * 5800-5810 for team use ServerSocket serverSocket = new ServerSocket(port);
	 * System.out.println("Server Started and listening to the port " + port);
	 * 
	 * // Server is running always. This is done using this while(true) loop //
	 * everything below this line to the next comment was a while loop originally.
	 * 
	 * // Reading the message from the client socket = serverSocket.accept();
	 * InputStream is = socket.getInputStream(); InputStreamReader isr = new
	 * InputStreamReader(is); BufferedReader br = new BufferedReader(isr); String
	 * input = br.readLine(); System.out.println("Message received from client is "
	 * + input); input =
	 * input.toLowerif(DiagnosticServer.getInput().equalsIgnoreCase((); String
	 * returnMessage = input;
	 * 
	 * 
	 * try { int numberInIntFormat = Integer.parseInt(input); int returnValue =
	 * numberInIntFormat*2; returnMessage = String.valueOf(returnValue) + "\n"; }
	 * catch(NumberFormatException e) { //Input was not a number. Sending proper
	 * message back to client. returnMessage = "Please send a proper number\n"; }
	 * 
	 * 
	 * // Sending the response back to the client. OutputStream os =
	 * socket.getOutputStream(); OutputStreamWriter osw = new
	 * OutputStreamWriter(os); BufferedWriter bw = new BufferedWriter(osw);
	 * bw.write(returnMessage); System.out.println("Message sent to the client is "
	 * + returnMessage); bw.flush(); }
	 * 
	 * catch (Exception e) { e.printStackTrace(); } finally { try { socket.close();
	 * } catch (Exception e) { } } } }
	 */

}
