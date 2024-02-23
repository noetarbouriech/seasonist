# users service

Service dedicated to users. It interacts with Keycloak to create them and store additional information in a database.
It has a REST endpoint to register a user, and a GraphQL service to update the users.
The login and other OIDC endpoint needs to be exposed to the application.

You can export the Keycloak with the command:

```shell
make save-realm
```
