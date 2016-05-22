# deegree-cli-utility

[![Build Status](https://travis-ci.org/JuergenWeichand/deegree-cli-utility.svg?branch=master)](https://travis-ci.org/JuergenWeichand/deegree-cli-utility)

Command line utility based on [deegree3](https://github.com/deegree/deegree3) to generate `DDL` and `deegree SQLFeatureStore` from GML application schemas. 

You can download the latest release [here](https://github.com/JuergenWeichand/deegree-cli-utility/releases) or build it yourself. 

    git clone https://github.com/JuergenWeichand/deegree-cli-utility.git
    cd deegree-cli-utility/
    mvn clean compile assembly:single


## Usage

```
Usage: java -jar deegree-cli-utility.jar [options] schema_url

options:
 --format={deegree/ddl}
 --srid=<epsg_code>
```

### Example: Generate ddl for INSPIRE Cadastral Parcels 4.0

    java -jar deegree-cli-utility.jar --srid=25832 --format=ddl http://inspire.ec.europa.eu/schemas/cp/4.0/CadastralParcels.xsd

### Example: Generate deegree SQLFeatureStore for INSPIRE Cadastral Parcels 4.0

    java -jar deegree-cli-utility.jar --srid=25832 --format=deegree http://inspire.ec.europa.eu/schemas/cp/4.0/CadastralParcels.xsd


## Behind http proxy

Set the `http.proxyHost`, `http.proxyPort` and `http.nonProxyHosts` config properties.

    java -Dhttp.proxyHost=your-proxy.net -Dhttp.proxyPort=80 -jar deegree-cli-utility.jar --format=ddl http://inspire.ec.europa.eu/schemas/cp/4.0/CadastralParcels.xsd

