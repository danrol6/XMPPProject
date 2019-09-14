/*
Spring 2019
Daniel Roldan
The following code was modified from the tutorial created by JavaProficiency
( https://www.javaproficiency.com/2015/12/how-to-send-message-from-one-user-to-html/ ) - Send Message
( https://www.javaproficiency.com/2015/12/how-to-add-roster-in-xmpp-using-smack-html/ ) - Add a Roster

OpenFire is a Real Time Collaboration (RTC) Server that is used for instant messaging. Openfire allows different
users to work together, and see changes, in real time. This is especially useful for chat applications as it will
display messages to all users as they are sent, received and processed in the server instead of using other methods,
such as push and get, that sends and receives new changes over a specified period of time.

For a step by step instruction on how to install and setup OpenFire please refer to the project documentation
named "Installation and Setup of OpenFire"


- Does XMPP allows sending JSON objects?
    - Yes, the Smack library supports the use of JSON object using the following import:

        import org.jivesoftware.smackx.json.packet.JsonPacketExtension;

    and defining its variables as follows:

        JsonPacketExtension jsonPacketExtension = JsonPacketExtension.from(stanza);
        String contentJson = jsonPacketExtension.getJson();

( https://stackoverflow.com/questions/38755335/accessing-json-object-inside-message-stanza-using-smack-library )


- Does XMPP allows sending a XML Object?
    - Yes, the sending and receiving of XML objects is supported by XMPP. However, it requires the use of custom
    libraries. This is difficult to implement because XMPP works using stanzas which requires the use of tags. Since
    XMPP uses XML tags and XML reads the entire XML document before doing any execution, it requires that all open
    tags have a closing tag. When sending a XML object, the object is send before the tag can be close and it is read
    before a closing tag is reach. This problem can be solve by parsing the XML incrementally and adding closing tags
    depending on the tags that were left open. More less, this can be fixed using a form of Stacks and implementing it
    into the code.
( https://stackoverflow.com/questions/11393048/how-to-send-parsed-xml-to-xmpp-server )


For the code documentation, please refer to the project document named "Enable Private and Independent Communication
Using XMPP Protocol". The document will explain the purpose of this application, description of the application,
behavioral expectations, and code breakdown.
 */


import java.util.*;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class SendMessageInChat {

    public static void main(String[] args) {

        AddRoster addRoster = new AddRoster(); //Creates a new Roster

        XMPPConnection connection = addRoster.Connect(); //Creates an XMPP connection using the Roster to verify username and password
        try {
            //Once the Roster was created on the server, XMPP uses roster and creates a connection using the pre-existing username and password
            //to log into the server
            connection.login("[Enter Server Username]", "[Enter Server Password]");
            System.out.println("Login");
            System.out.print("Enter your message to send: ");
            System.out.println();
            Chat chat = connection.getChatManager().createChat("[Enter User Username]@[Name of Server]", new MessageListener() {

            //Once logged in, the application specifies the user it wants to connect with and creates a connection "tunnel" within the
            //network that will allow the two users to transfer information back and forth between the two of them.
            //To do this, the application needs the username and the server that username is connected to. With this information,
            //The application creates a listener that processes any incoming messages and displays it to the appropriate recipient
            //Chat chat = connection.getChatManager().createChat("testuser1@172.20.13.92", new MessageListener() {

                //Creates an anonymous subclass method that receives a message from the sender user, process the stanza and retrieves
                //the information from the stanza object. The retrieved information is stored into variables used to display the message
                //to the recipient
                @Override
                public void processMessage(Chat chat, Message message) { //Anonymous subclass method. Gets the chat object and the message
                    String from = message.getFrom(); // stores the sender information
                    String body = message.getBody(); //stores the message sent
                    if (body != null) { //checks if the message sent is not empty, if it is not process the message by printing the message
                        System.out.println();
                        System.out.println(from + ": " + body);
                        System.out.println("Enter your message to send: ");
                    }
                }

            });
            try {

                Scanner input = new Scanner(System.in); //Allows the user to type onto the console a message so that the recipient is able to receive the message

                while (input.hasNext()) { //Stores the user's message into a String object
                    System.out.println("Enter your message to send: ");
                    String msg = input.nextLine();
                    chat.sendMessage(msg); //sends the user's message
                }


                System.out.println("Send Message succesfully");

                //The following loops keeps the connection between the two users open so that the two users can communicate
                while (true) {
                    Thread.sleep(50); //makes a pause so that the application does not crash for using all the network resources.
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            connection.disconnect();

        } catch (XMPPException e) {

            e.printStackTrace();
        }
    }

    //The following method creates the connection between the device, XMPP/server and the application.
    //For it, it needs the host ip address where it is going to connect to. The ip address is the destination of the server.
    //Then it needs the port number required for the application to communicate.
    public XMPPConnection Connect() {

        ConnectionConfiguration config = new ConnectionConfiguration("[Enter host IP]", 00000 );
        //ConnectionConfiguration config = new ConnectionConfiguration("172.20.13.92", 5222);//Creates the XMPP connection using the destination of the server ip and the port number to specify the destination of the application

        XMPPConnection connection = new XMPPConnection(config); //Creates a connection using the destination ip and the port number
        try {
            connection.connect();
        } catch (XMPPException e) {

            e.printStackTrace();
        }
        return connection;
    }

}