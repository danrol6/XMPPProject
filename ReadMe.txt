Spring 2019
Daniel Roldan

****For the code documentation, please refer to the project document named "Enable Private and Independent Communication Using XMPP Protocol" under the Documentation folder. The document will explain the purpose of this application, description of the application, behavioral expectations, and code breakdown.

****For a demo and presentation of the project, please refer to the Powerpoint presentation "Enable Private and Independent Communications Using The XMPP" under the Documentation folder.




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


