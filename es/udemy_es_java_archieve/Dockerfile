FROM amazoncorretto:11-alpine
RUN addgroup -S spring && adduser -S spring -G spring
RUN apk add curl
USER spring:spring
ARG DEPENDENCY=build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","com.udemy_sergii_java.spring_boot_es.SpringBootEsApplication"]
