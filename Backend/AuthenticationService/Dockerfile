# Choose a base image
FROM openjdk

#Reffering to working Directory of your image
WORKDIR usr/lib
EXPOSE 9000

#copy executable file and put it your working directory
#add source destination
ADD ./target/AuthenticationService-0.0.1-SNAPSHOT.jar usr/lib/AuthenticationService-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","usr/lib/AuthenticationService-0.0.1-SNAPSHOT.jar"]