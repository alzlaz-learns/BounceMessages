# BounceMessages
12/2023 first major push
I wanted to make an android app for fun and thought a cool idea would be to have an app the receives
SMS and forwards it to an program on your desktop and then giving the ability for the user to respond
back to from the desktop back all in background. Something simple to see whats changed since my college 
courses years ago. I was thinking about implementing it over TCP connection in the background since 
it was just going to be an emulator app. The long and short of it though is that it wont really work
the way I intended it for receiving messages. so I'm currently looking up other ways to handle that.


12/2023 second major push
I've got a rough code sprawl of what I want to do other than the final part of sending received data
from the desktop client as a sms back to target phone number Im currently reading how to hand that
with Android studios smsManager. 

How it works right now is that upon opening the app you can connect to a client through a websocket 
to the desktop client over this connection the client can alerts the app to open a tcp connection to 
receive a message from the client. Currently to establish the websocket connection it is a button 
press because I was having difficulties originally remaking the connection after after anything closed 
the connection. It had something to do with how the intent was launched on startup of the android app and
i wasnt to clear on how to handle it but since it wasnt the point of the project I just decided to 
simplify it by adding a button, and save that as a research goal for later. 

Alot of the bigger stuff happened on desktop client side which i havent uploaded yet and will eventually
I started using vert.x to create a desktop as a websocket/ tcp server. Its a simple CLI version of 
things. It currently handles 2 user inputs quit and send. quit is straight forward just type quit to 
shut things down. send comes in the format of <send> <phone number string> <message string> which is
then split and put into an queue that is of Pair<String, string> (phonenumber, message) and when a connection
is made it is sent to the app as a string displayed in logcat.