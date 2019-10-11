FROM docker-base.artifactory.resource.bank/azul/zulu-openjdk-alpine:11.0.1

VOLUME /tmp

ARG JAR_PATH

#ADD build/image /opt/jdk
ADD ${JAR_PATH} app.jar

#ENV JAVA_HOME=/opt/jdk
#ENV PATH="$PATH:$JAVA_HOME/bin"
ENV LANG=C.UTF-8

EXPOSE 8080

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
