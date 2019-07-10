FROM gradle:5.4

MAINTAINER Ithuan

WORKDIR /opt/han-ji/

COPY . .

EXPOSE 8060
CMD gradle run --stacktrace --info
