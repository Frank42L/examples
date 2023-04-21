package com.javahelps.jerseydemo.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
// import JavaMail;

@Path("/emailverification")
public class EMailVerificationService {


	private void smtpWaitforAnswer(BufferedReader in) throws IOException {
		System.out.println("<" + in.readLine());		
		while (in.ready()) {
			System.out.println("<" + in.readLine());		
		}		
	}
	
	private void smtpCommand(PrintWriter out, BufferedReader in, String cmd) throws IOException {
		System.out.println(">" + cmd);		
		out.println(cmd);
		smtpWaitforAnswer(in);
	}
	
	private void echoPing(String smtpServer, int port)
	{

		Socket socket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		
		try {
			socket = new Socket(smtpServer, port);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.out.println("echoPing failed with " + e.toString());
			throw new RuntimeException(e);
		}
		
		
		try {
			// smtpWaitforAnswer(in);
			System.out.println("<" + in.readLine());		
			smtpCommand(out, in, "EHLO bluewin.ch"); //  + smtpServer);
			smtpCommand(out, in, "QUIT");
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
			System.out.println("echoPing failed with " + e.toString());
			throw new RuntimeException(e);
		}

	}
	
	/*
	 * Server : smtp.bluewin.ch (Port 25) oder 
	 *          smtpauths.bluewin.ch (Port 465)
	 *          
	 */
    @GET
    @Path("/{email}")
    public Response verifyEMail(@PathParam("email") String eMail) {
        String output = "Going to Verify E-Mail , " + eMail + "...";
        System.out.println(output);
        System.out.println("Going to open a socket to Mailserver");
        // echoPing("smtp.bluewin.ch", 25);
        echoPing("smtpauths.bluewin.ch", 25);
        System.out.println("Socket was possible, but we did not do a E-Mail Verification yet");
        return Response.status(200).entity(output).build();
    }

}
