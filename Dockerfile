FROM openjdk:11.0-jre-slim

ADD target/scala-2.12/kubernetes-health-checks.jar kubernetes-health-checks.jar

EXPOSE 8080

ENTRYPOINT exec java $JAVA_OPTS -jar /kubernetes-health-checks.jar