wxs-examples
============

WebSphere eXtreme Scale examples.

Packaged as OSGI-bundles.

Run on WebSphere Application Server Version 8.5.5.3 Liberty Profile and WebSphere eXtreme Scale V8.6 for Developers - Liberty Profile.


## Usage ##

1. Install WebSphere Application Server Version 8.5.5.3 Liberty Profile
2. Install WebSphere eXtreme Scale V8.6 for Developers - Liberty Profile
3. Install WebSphere eXtreme Scale V8.6 Trial, i.e. the standalone version
4. Execute mvn install:install-file -Dfile=<path _to_your_wxs_standalone_installation>/ObjectGrid/lib/ogclient.jar -DgroupId=com.ibm.websphere -DartifactId=ogclient -Dversion=8.6.0 -Dpackaging=jar
5. Execute mvn install:install-file -Dfile=<path _to_your_wxs_standalone_installation>/ObjectGrid/lib/objectgrid.jar -DgroupId=com.ibm.websphere -DartifactId=objectgrid -Dversion=8.6.0 -Dpackaging=jar
6. Create a server as described in the WebSphere Application Server documentation
7. Update the grids.directory property in pom-parent/pom.xml to the grids catalog in the server you created
8. Execute mvn install in modules
9. Start the server
10. Execute mvn exec:java in distributed-preloaded-cache-loader to preload the distributed-preloaded-cache
11. Run and explore the IT tests in the distributed-inline-cache, distributed-preloaded-cache and distributed-side-cache modules
12. Run and explore the tests the cache-wxs module


## Recommendations ##

1. Use a separate Git repository for each Maven module
