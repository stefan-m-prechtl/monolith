# REXT Monolith(Java EE 8)

REXT als Monolith

## Infrastruktur

Über Docker (siehe Ordner docker) werden verwendet:

- Payara 5 (full)
- MySQL 8 (Version 8.0.12)

## Build

Build erfolgt über Gradle mit Tasks für

- Start/Stop Infrastruktur
- Deployment des WAR in den autodeploy Ordner

## Debug

In Eclipse per Remote-Debug Konfiguration

- Host: localhost
- Port: 9009

## URL (mit Docker-Container)

### Projekt-Management

- http://localhost:8080/monolith/rext/projectmgmt/projects (GET)

### User-Management

- http://localhost:8080/monolith/rext/usermgmt/users (GET)
- http://localhost:8080/monolith/rext/usermgmt/users/search?login=<value> (GET)
