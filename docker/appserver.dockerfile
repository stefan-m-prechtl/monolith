# Basis-Image
FROM payara/server-full:5.191

# Hostname für Datenbankserver
ENV PAYARA_ARGS --debug

# MYSQL-JDBC-Driver 
COPY mysql-connector-java-8.0.12.jar /opt/payara/appserver/glassfish/domains/production/lib
COPY guava-28.0-jre.jar /opt/payara/appserver/glassfish/domains/production/lib
 
# ASADMIN Commands to create JDBC-Resource
COPY --chown=payara:payara ./post-boot-commands.asadmin /opt/payara/config/

