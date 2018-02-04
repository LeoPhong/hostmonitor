FROM alpine:3.7
LABEL LeoPhong="<jack.feng.liu@gmail.com>"
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.tuna.tsinghua.edu.cn/g' /etc/apk/repositories \
    && apk add --update --no-cache  openjdk8-jre-base
# Set environment for jre
ENV JAVA_HOME /usr/lib/jvm/default-jvm
ENV PATH ${PATH}:${JAVA_HOME}/bin
# Set environment for jre time zone
ENV TZ="Asia/Shanghai"
ADD ./target/hostmonitor-0.1.5.jar hostmonitor.jar
ENTRYPOINT ["java", "-jar", "/hostmonitor.jar"]
