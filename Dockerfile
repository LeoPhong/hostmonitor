FROM anapsix/alpine-java
LABEL LeoPhong="<jack.feng.liu@gmail.com>"
ENV TZ="Asia/Shanghai"
ADD ./target/hostmonitor-0.1.3.jar hostmonitor.jar
ENTRYPOINT ["java", "-jar", "/hostmonitor.jar"]
