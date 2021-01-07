FROM gradle:6.6.1-jdk14
COPY . /var/www/java
WORKDIR /var/www/java
EXPOSE 9090 9000
CMD ["gradle", "-q", "run"]