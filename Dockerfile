FROM maven AS maven
WORKDIR /java-web-app
COPY . ./
RUN mvn package -Dmaven.test.skip=true

FROM tomcat:9.0.24
COPY --from=maven /java-web-app/target/docker-springboot.war /usr/local/tomcat/webapps/docker-springboot.war
EXPOSE 8086
CMD ["java", "-jar", "/usr/local/tomcat/webapps/docker-springboot.war"]
