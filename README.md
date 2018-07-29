mvn clean -Dtest=DocumentGenerate* test package -P pdf

mvn -DskipTests package -P pdf 

mvn -DskipTests clean package source:jar javadoc:jar 

java -jar samples-swagger2-doc.jar

http://bcho.tistory.com/954

