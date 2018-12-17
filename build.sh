#!/bin/bash -ex

RED="\e[31m"
ENDC="\e[0m"
BOLD="\e[1m"

# rationale: set a default DEPLOY_SECRETS
if [ -z "$JENKINS_DEPLOY" ]; then
  JENKINS_DEPLOY='no'
fi

if [ -z "$IGAC_DEPLOY" ]; then
  IGAC_DEPLOY='no'
fi

if [ "$JENKINS_DEPLOY" == "yes" ]
then
  # Opcional, configura la aplicación descifrando secretos.
  rsaconfigcipher -P $JENKINS_SECRET_KEY src/main/resources/services/*.rsa
fi

if gradle build ; then
    echo -e "\n${RED}${BOLD}Artifact in ./build/libs/webservices.jar${ENDC}" 
else
    echo -e "\n${RED}${BOLD}Error Gradle Build${ENDC}"
fi

if [ "$IGAC_DEPLOY" == "yes" ]
then
  scp build/libs/webservices.jar build.sh web:/opt/webservices/webservices.jar
fi

