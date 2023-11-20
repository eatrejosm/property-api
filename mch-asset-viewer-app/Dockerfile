FROM --platform=linux/x86_64 public.ecr.aws/docker/library/amazoncorretto:17-alpine-jdk as builder
COPY ./build/libs/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM --platform=linux/x86_64 public.ecr.aws/docker/library/amazoncorretto:17-alpine-jdk
RUN apk add --no-cache curl
RUN apk add --no-cache tzdata
ENV TZ America/New_York

COPY --from=builder dependencies/ ./
RUN true
COPY --from=builder snapshot-dependencies/ ./
RUN true
COPY --from=builder spring-boot-loader/ ./
RUN true
COPY --from=builder application/ ./
RUN true

RUN mkdir -p /logs
RUN mkdir -p /config
#App Port here
EXPOSE 8080
#Monitoring Port here
EXPOSE 8181
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} org.springframework.boot.loader.JarLauncher"]

