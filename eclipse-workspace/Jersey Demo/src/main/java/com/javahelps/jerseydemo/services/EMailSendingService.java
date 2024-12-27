package com.javahelps.jerseydemo.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/emailsending")
public class EMailSendingService {
	private static boolean VERBOSE = true;

	private void smtpWaitforAnswer(BufferedReader in) throws IOException {
		UtilVerbose.println(VERBOSE, "<" + in.readLine());		
		while (in.ready()) {
			UtilVerbose.println(VERBOSE, "<" + in.readLine());		
		}		
	}
	
	private void smtpCommandNoWait(PrintWriter out, BufferedReader in, String cmd) throws IOException {
		UtilVerbose.println(VERBOSE, ">" + cmd);		
		out.println(cmd);
	}
	
	private void smtpCommand(PrintWriter out, BufferedReader in, String cmd) throws IOException {
		smtpCommandNoWait(out, in, cmd);
		smtpWaitforAnswer(in);
	}

	private void sendMsg(String smtpServer, int port, String emailTo, String subject, String content)
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
	        String stringDate = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", new Locale("en", "UK"))
					.format(new Date());
	        
			UtilVerbose.println(VERBOSE, "<" + in.readLine());		
			smtpCommand(out, in, "EHLO bluewin.ch"); //  + smtpServer);
			
			
			smtpCommand(out, in, "MAIL FROM:<frank.loeliger@bluewin.ch>"); 
			smtpCommand(out, in, "RCPT TO:<mail@frank.loeliger.name>"); 
			smtpCommand(out, in, "DATA");
			smtpCommandNoWait(out, in, "From: <frank.loeliger@bluewin.ch>");
			smtpCommandNoWait(out, in, "To: <mail@frank.loeliger.name>");
			smtpCommandNoWait(out, in, "Subject: " + subject);
			smtpCommandNoWait(out, in, "Date: " + stringDate);
			smtpCommandNoWait(out, in, content);
			smtpCommand(out, in, ".");
			
			smtpCommand(out, in, "QUIT");
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
			UtilVerbose.println(VERBOSE, "sendMsg failed with " + e.toString());
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
    public Response sendEMail(@PathParam("email") String eMail) {
    	String emailContent = "Hallo Frank, \n Gerne mal wieder bei einem Bier, \n Gruss Markus";
        String output = "Going to Send E-Mail to, " + eMail + "...";
        UtilVerbose.println(VERBOSE, output);
        UtilVerbose.println(VERBOSE, "ACHTUNG: MAILFORMAT IST NICHT VERIIFZIERT UND GGF: SPAM ANFAELLIG");

        sendMsg("smtpauths.bluewin.ch", 25, eMail, "Testmail", emailContent);
        return Response.status(200).entity(output).build();
    }

}
