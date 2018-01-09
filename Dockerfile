FROM openjdk:8
LABEL LeoPhong="<jack.feng.liu@gmail.com>"
RUN  cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
ENV TZ="Asia/Shanghai"
ADD ./target/hostmonitor-0.1.2.jar hostmonitor.jar
ENTRYPOINT ["java", "-jar", "/hostmonitor.jar"]
