FROM azul/zulu-openjdk:11

COPY target/backend-0.0.1-SNAPSHOT.jar backend.jar

EXPOSE 8080
ENTRYPOINT java -jar /backend.jar --database.url=$URL --database.name=$NAME --database.user=$USER database.password=$PASSWORD omdb.apikey=$APIKEY
