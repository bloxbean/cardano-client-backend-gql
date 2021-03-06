# This repository has been archived. All files have been moved under cardano-client-lib project (https://github.com/bloxbean/cardano-client-lib/tree/master/backends/cardano-graphql) #

## All backend implementations will be maintained under [cardano-client-lib](https://github.com/bloxbean/cardano-client-lib) repo ##

[![Java CI with Gradle](https://github.com/bloxbean/cardano-client-backend-gql/actions/workflows/gradle.yml/badge.svg)](https://github.com/bloxbean/cardano-client-backend-gql/actions/workflows/gradle.yml)

# cardano-client-backend-gql

### Cardano GraphQL Backend implementation for [Cardano Client Lib](https://github.com/bloxbean/cardano-client-lib)

## Dependency

**Maven**

```
<dependency>
     <groupId>com.bloxbean.cardano</groupId>
     <artifactId>cardano-client-lib</artifactId>
     <version>0.1.4</version>
</dependency>
<dependency>
     <groupId>com.bloxbean.cardano</groupId>
     <artifactId>cardano-client-backend-gql</artifactId>
     <version>0.1.4</version>
</dependency>
```

**Gradle**

```
  implementation('com.bloxbean.cardano:cardano-client-lib:0.1.4')
  implementation('com.bloxbean.cardano:cardano-client-backend-gql:0.1.4')
```

**Get BackendService instance for Cardano GraphQL backend**

```
BackendService backendService =
                new GqlBackendService(<Cardano GraphQL Url>);
```

**Example:**

BackendService using Dandelion's GraphQL endpoint.

```
BackendService backendService =
                new GqlBackendService("https://graphql-api.testnet.dandelion.link/");
```

**Note:** You can get other services from BackendService instance. For detailed api usage, check [cardano-client-lib](https://github.com/bloxbean/cardano-client-lib) project
