
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class AddRoster {

    public static void main(String[] args) {

        AddRoster addRoster = new AddRoster(); //Creates a Roster object

        XMPPConnection connection = addRoster.Connect(); //Creates a connection using the roster
        try {

            connection.login("[Enter Server Username]", "[Enter Server Password]"); //using the Roster, it connects to the server using an username and a password. The server checks if the username and password exists on the server, if it is the user is logged into it.
            System.out.println("Login");

            Roster roster = connection.getRoster(); //Retreives a list of users from the database. This will allow the application to talk/connect to other exisiting users on the server. Ex. pre-existing user1 will be able to connect and chat with pre-existing user2
            roster.createEntry("[Enter User Username]", "[Enter User Password]", null); //adds an entry to the roster. This means that the user will be entered in a pool of addresses that is stored on the server. The server will then keep this Roster so that other users can retrieve an user from this list

            System.out.println("send roster request");
            connection.disconnect(); //After the entry in the "pool of addresses" has been entered, the connection ends

        } catch (XMPPException e) {

            e.printStackTrace();
        }
    }

    //The following method creates the connection between the device, XMPP/server and the application.
    //For it, it needs the host ip address where it is going to connect to. The ip address is the destination of the server.
    //Then it needs the port number required for the application to communicate.
    public XMPPConnection Connect() {

        ConnectionConfiguration config = new ConnectionConfiguration("[Enter host IP]", 00000 ); //Creates the XMPP connection using the destination of the server ip and the port number to specify the destination of the application

        XMPPConnection connection = new XMPPConnection(config); //Creates a connection using the destination ip and the port number
        try {
            connection.connect();
        } catch (XMPPException e) {

            e.printStackTrace();
        }
        return connection;
    }

}