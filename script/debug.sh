sh env.sh
cd ..
mvn -Denv=dev spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005" -Dspring-boot.run.arguments="--server.port=8080"