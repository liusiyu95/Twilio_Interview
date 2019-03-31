# Twilio_Interview
This ListenUp project is done in java v1.8.It is implemented by using java spark web framework.

Web API:
Java Main function listens to the port 8005, takes get request of
http://localhost:8005/users and http://localhost:8005/users/:username.
It returns json object by using Gson library.
 
BackEnd:
ServiceManager gets user data from plays and friends services by using http client. It processes user data, 
then store it into java object.
 
 



