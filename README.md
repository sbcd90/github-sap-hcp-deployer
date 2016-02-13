github-sap-hcp-deployer
=======================

- Installation Guidelines

1) Install maven from the [link](https://maven.apache.org/download.cgi). Extract it.

2) Set `MAVEN_HOME` environment variable to the Maven home directory i.e. the path in which maven is extracted.

3) Clone this github repository using the following command

```
git clone https://github.com/sbcd90/github-sap-hcp-deployer.git
```

4) Navigate to the repository directory & Build the repository with the following command

```
$MAVEN_HOME/bin/mvn clean install
```

5) Download SAP Hana Cloud Platform SDK from [link](https://tools.hana.ondemand.com/#cloud). Extract it.

6) Set `NEO_SDK` environment variable to the SDK home directory i.e. the path in which SDK is extracted.

7) Navigate to the repository directory & Run the application using the following command

```
$MAVEN_HOME/bin/mvn jetty:run
```

8) Open the application at `http://<hostname>:8080`

- OS compatibility

The tool works both on Windows & Linux distros. So, the tool can be hosted on Windows or Linux servers.

- To do Lists

Upload the war to JFrog Bintray to simplify the installation process.