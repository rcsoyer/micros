rootProject.name = "parent"
include(":service-discovery")
include(":web-app")
include(":cmd")
project(":service-discovery").projectDir = file("servicediscovery")
project(":web-app").projectDir = file("webapp")

