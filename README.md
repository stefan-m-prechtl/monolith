# REXT Monolith(Java EE 8)
REXT als Monolith

## Infrastruktur
Über Docker (siehe Ordner docker) werden verwendet:
- Payara 5 (full)
- MySQL 8

## Build
Build erfolgt über Gradle mit Tasks für
- Start/Stop Infrastruktur
- Deployment des WAR in den autodeploy Ordner

## URL (mit Docker-Container)
- http://localhost:8080/monolith/rext/projectmgmt/projects (GET)
- http://localhost:8080/monolith/rext/usermgmt/users (GET)

