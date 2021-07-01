
# Integration with AWS SQS e Gloogle Maps APIs :: GeoData

* Para executar localmente

(anteriormente você tem que executar ***mvn clean install*** e exportar as variáveis de ambiente manualmente **envars** )
~~~~
java -XX:+UnlockExperimentalVMOptions -Xms128m -Xmx256m -XX:MaxMetaspaceSize=512m -XX:CompressedClassSpaceSize=64m -Xss16m -Xmn32m -XX:InitialCodeCacheSize=64m -XX:ReservedCodeCacheSize=64m -XX:MaxDirectMemorySize=128m -jar target/cleiton-geodata.jar
~~~~

<h4>Spring Boot Maven Goals</h4>

* Runs your Spring Boot application.
~~~~
$ mvn spring-boot:run
~~~~

* Repackages your **jar** to be executable.
~~~~
$ mvn spring-boot:repackage 
~~~~

* To manage the lifecycle of your Spring Boot application (i.e. for integration tests).
~~~~
$ mvn spring-boot:start
$ mvn and spring-boot:stop
~~~~

* Generates build information that can be used by the Actuator.
~~~~
$ spring-boot:build-info
~~~~


