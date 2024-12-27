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
	private static boolean VERBOSE = true;

	private void smtpWaitforAnswer(BufferedReader in) throws IOException {
		UtilVerbose.println(VERBOSE, "<" + in.readLine());		
		while (in.ready()) {
			UtilVerbose.println(VERBOSE, "<" + in.readLine());		
		}		
	}
	
	private void smtpCommand(PrintWriter out, BufferedReader in, String cmd) throws IOException {
		UtilVerbose.println(VERBOSE, ">" + cmd);		
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
			UtilVerbose.println(VERBOSE, "echoPing failed with " + e.toString());
			throw new RuntimeException(e);
		}
		
		
		try {
			// smtpWaitforAnswer(in);
			UtilVerbose.println(VERBOSE, "<" + in.readLine());		
			smtpCommand(out, in, "EHLO bluewin.ch"); //  + smtpServer);
			smtpCommand(out, in, "QUIT");
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
			UtilVerbose.println(VERBOSE, "echoPing failed with " + e.toString());
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
        UtilVerbose.println(VERBOSE, output);
        UtilVerbose.println(VERBOSE, "Going to open a socket to Mailserver");
        // echoPing("smtp.bluewin.ch", 25);
        echoPing("smtpauths.bluewin.ch", 25);
        UtilVerbose.println(VERBOSE, "Socket was possible, but we did not do a E-Mail Verification yet");
        return Response.status(200).entity(output).build();
    }

}
