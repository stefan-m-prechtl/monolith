# Basis-Image
# FROM payara/server-full:5.193
FROM payara/server-full:5.2020.5-jdk11

# Debug-Modus aktivieren
ENV PAYARA_ARGS --debug

# MYSQL-JDBC-Driver 
COPY mysql-connector-java-8.0.22.jar /opt/payara/appserver/glassfish/domains/production/lib
COPY guava-28.0-jre.jar /opt/payara/appserver/glassfish/domains/production/lib
 
# ASADMIN Commands to create JDBC-Resource
COPY --chown=payara:payara ./post-boot-commands.asadmin /opt/payara/config/

