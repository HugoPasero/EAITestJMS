/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classesTests;

import donnes.date.DateConvention;
import donnes.preconvention.Diplome;
import donnes.preconvention.Etudiant;
import donnes.preconvention.PreConvention;
import java.text.ParseException;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author marieroca
 */
public class Sender {
    public static void main(String[] args) throws NamingException, InterruptedException, ParseException, JMSException {
        
        System.setProperty("java.naming.factory.initial","com.sun.enterprise.naming.SerialInitContextFactory"); 
        System.setProperty("org.omg.CORBA.ORBInitialHost", "127.0.0.1");
        System.setProperty("org.omg.CORBA.ORBInitialPort", "3700"); 
        InitialContext context = new InitialContext();
        
        ConnectionFactory factory = null;
        Connection connection = null;
        String factoryName = "jms/__defaultConnectionFactory";
        String destName = "ConventionEnCours";
        Destination dest = null;
        int count = 1;
        Session session = null;
        MessageProducer sender = null;
        /*TitreBoursier goog = new TitreBoursier("GOOG", "Google inc", 1194.64F, 14.15F);
        TitreBoursier aapl = new TitreBoursier("AAPL", "Apple inc", 224.95F, 4.53F);*/
        Diplome d = new Diplome(1L, Diplome.Niveau.M2, "MIAGE");
        Etudiant e1 = new Etudiant(1L, "Murillo--Cantié", "Emma", "MAIF", "maif1", d);
        Etudiant e2 = new Etudiant(2L, "Pasero", "Hugo", "MAIF", "maif2", d);
        Etudiant e3 = new Etudiant(3L, "Roca", "Marie", "MAAF", "maaf1", d);
        PreConvention p1 = new PreConvention(1L, new DateConvention("01/06/2018"), new DateConvention("31/08/2018"), 1200.0F, "Chargé d'affaire", "Sharepoint", "ENEDIS GRDF", "François Guiraud", "ERDF1234", e1);
        PreConvention p2 = new PreConvention(2L, new DateConvention("01/06/2018"), new DateConvention("31/08/2018"), 1100.0F, "Développeur", "Web", "Banque populaire", "M. Jean-Claude", "BP1234", e2);
        PreConvention p3 = new PreConvention(3L, new DateConvention("01/06/2016"), new DateConvention("31/08/2018"), 1150.0F, "Assistance MOA", "Tuleap", "Orange", "Aurélie", "O1234", e3);
        

        try {
            // create the JNDI initial context.
            context = new InitialContext();

            // look up the ConnectionFactory
            factory = (ConnectionFactory) context.lookup(factoryName);

            // look up the Destination
            dest = (Destination) context.lookup(destName);

            // create the connection
            connection = factory.createConnection();

            // create the session
            session = connection.createSession(
                false, Session.AUTO_ACKNOWLEDGE);

            // create the sender
            sender = session.createProducer(dest);

            // start the connection, to enable message sends
            connection.start();
            while(true){
                for (int i = 0; i < count; i++) {
                    ObjectMessage message = session.createObjectMessage();
                    message.setObject(p1);
                    sender.send(message);
                    
                    System.out.println("Sent: " + message.getObject() );
                    message = session.createObjectMessage();
                    message.setObject(p2);
                    sender.send(message);
                    System.out.println("Sent: " + message.getObject());
                }
                Thread.sleep(1000);
            }
            
        } catch (JMSException exception) {
            exception.printStackTrace();
        } catch (NamingException exception) {
            exception.printStackTrace();
        } finally {
            // close the context
            if (context != null) {
                try {
                    context.close();
                } catch (NamingException exception) {
                    exception.printStackTrace();
                }
            }

            // close the connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException exception) {
                    exception.printStackTrace();
                }
            }
        }
    
    }
    
}
