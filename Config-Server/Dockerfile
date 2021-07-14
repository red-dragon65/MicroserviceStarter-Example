
# This file builds a custom docker image to run the specified spring boot app

# This is the java base image
FROM openjdk:latest

# This adds the jar file built by maven into the images app folder
ADD ./target/config-server.jar /app/

# This tells the container how to run the jar file
ENTRYPOINT ["java", "-jar", "/app/config-server.jar"]

# The option '-Xmx' specifies the maximum memory to allocate
# to the jar. '200m' specifies the memory size as 200 megabytes.
# ENTRYPOINT ["java", "-Xmx200m", "-jar", "/app/springboot.jar"]

# Tells the docker host the health of this container
HEALTHCHECK --interval=30s --timeout=3s CMD curl -f http://localhost:8888/actuator/health || exit 1

# This opens the containers port to the docker host
# Note: The docker-compose.yml maps this port from the internal docker network to the localhost
EXPOSE 8888