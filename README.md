## Nginx proxying demo using query_string for GET queries or body content for POST queries

### Preparation, build and run

First, you should build a scala application and publish it into a local docker repository. Then just run docker-compose.
```
cd ./scala-app
sbt publishLocal

cd ..
docker-compose up --build
```

### Some query samples

```
curl -v -X POST --data 'vterm_id=AERU&id=12501234567890123456' http://localhost:8080/action

curl -v -X GET "http://localhost:8080/hello/Sergey?param1=value1&id=12501234567890123456"
```