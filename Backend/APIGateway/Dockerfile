# Choose a base image
FROM openjdk

#Reffering to working Directory of your image
WORKDIR usr/lib
EXPOSE 8080

#copy executable file and put it your working directory
#add source destination
ADD ./target/APIGateway-0.0.1-SNAPSHOT.jar usr/lib/APIGateway-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","usr/lib/APIGateway-0.0.1-SNAPSHOT.jar"]