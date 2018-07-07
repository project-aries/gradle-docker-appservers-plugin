### Version 0.9.3 (TBA)

### Version 0.9.2 (July 7, 2018)
* Bump `gradle-docker-applications-plugin` to `0.9.7`

### Version 0.9.1 (July 1, 2018)
* Support for `tomcat`, `wildfly (jboss)`, and `websphere`
* Added extension point `appservers` with initial option `randomPorts` which if set to true will assign random ports to all exposed ports of dockerized applications. Default is to expose ports exactly as they are to host.
