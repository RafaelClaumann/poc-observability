# Observability

Estrutura
![image](https://github.com/user-attachments/assets/82b9f9f7-07a9-41d4-b877-2b04768e8e4e)


https://docs.spring.io/spring-boot/reference/actuator/metrics.html#actuator.metrics.getting-started

Spring Boot auto-configures a composite MeterRegistry and adds a registry to the composite for each of the supported implementations that it finds on the classpath. Having a dependency on micrometer-registry-{system} in your runtime classpath is enough for Spring Boot to configure the registry.

https://docs.spring.io/spring-boot/reference/actuator/metrics.html#actuator.metrics.export.otlp

By default, metrics are exported over the OpenTelemetry protocol (OTLP) to a consumer running on your local machine.
