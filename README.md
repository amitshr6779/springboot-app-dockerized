# springboot-dockerized

mvn package -Dmaven.test.skip=true

docker run --name mysql-standalone -e MYSQL_ROOT_PASSWORD=admin -e MYSQL_DATABASE=HMS -e MYSQL_PASSWORD=admin -d mysql:8.0

docker run -p 8086:8086 --name springboot-web-app --link mysql-standalone:mysql -d as6779/webapp:v1

docker compose up -d
