# haffner.io-server

## Setup

- Java 11
- PostgreSQL

### Environments

#### Development

> `docker-compose -f src/main/resources/docker/postgres-db.yml up -d`

> `./mvnw spring-boot:run -Pdev`

#### Production (default)

> `./mvnw clean package`

> `java -jar target/haffner.io-server-0.0.1-SNAPSHOT.jar`

### CLI and Helper

> Webapp : `gatsby develop`

---

> Database : `psql -h <hostname> -p <port> -U <username> -d <database> -a -q -f filepath`

- -f -> path to script
- -a -> all echo
- -q -> quiet ?
