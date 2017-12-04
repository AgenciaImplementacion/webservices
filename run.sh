#!/bin/bash -ex

RED="\e[31m"
ENDC="\e[0m"
BOLD="\e[1m"

# rationale: set a default DEPLOY_SECRETS
if [ -z "$JENKINS_DEPLOY" ]; then
  JENKINS_DEPLOY='no'
fi

if [ "$JENKINS_DEPLOY" == "yes" ]
then
  # Opcional, configura la aplicación descifrando secretos.
  rsaconfigcipher -P $JENKINS_SECRET_KEY src/main/resources/services/cartagena.properties.rsa
fi

if gradle build ; then
    java -jar build/libs/webservices.jar
else
    echo -e "\n${RED}${BOLD}Error Gradle Build${ENDC}"
fi

