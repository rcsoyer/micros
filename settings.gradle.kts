rootProject.name = "parent"
include(":service-discovery")
include(":web-app")
include(":cmd")
include(":authorization-server")
project(":service-discovery").projectDir = file("servicediscovery")
project(":web-app").projectDir = file("webapp")

