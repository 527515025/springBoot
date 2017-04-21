#!/bin/sh

#########################
# based on docker bin

packageName=$1
imageVersion=$2
dockerDestFolder=$3
env=$4
packageTZ=Asia/Shanghai

if [ "${packageName}" = "" ]; then
    echo [ERROR] "Please provide image name as first param"
    exit 1
fi

if [ "${imageVersion}" = "" ]; then
    echo [ERROR] "Please provide image version as second param"
    exit 1
fi

if [ "${dockerDestFolder}" = "" ]; then
    echo [ERROR] "Please provide docker dest folder"
    exit 1
fi

if [ "${env}" = "" ]; then
    echo [ERROR] "Please provide env folder"
    exit 1
fi


COUNT=`docker -v 2>&1 | grep 'version' |  grep -v grep | wc -l`
if [ ${COUNT} != 1 ]; then
    echo [ERROR] "Cannot find docker command"
    exit 1
fi

echo [INFO] "Remove old image from docker repository..."
count=`docker images | grep "${packageName}" | grep "${imageVersion}" | wc -l`
if [ ${count} != 1 ]; then
  echo [WARN] "image ${packageName} with version ${imageVersion} does not exist in docker repo"
else
  docker rmi -f ${packageName}:${imageVersion}
fi

#generate Dockerfile dynamically
echo [INFO] "generating Dockerfile..."
warName=${packageName}-${imageVersion}.war
dateVersion=`date +%Y%m%d-%H%M%S`

#cat out to Dockerfile with content begin/end with EOF
cat > target/Dockerfile <<EOF
FROM debian:jessie
MAINTAINER Cliff Ma
COPY target/${warName} ${dockerDestFolder}/${dateVersion}/
ENV LANG C.UTF-8
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/${packageTZ} /etc/localtime && echo ${packageTZ} > /etc/timezone
RUN cd ${dockerDestFolder} \
    && ln -svnf ${dateVersion} current \
    && cd current \
    && touch docker-entrypoint.sh \
    && chmod +x docker-entrypoint.sh \
    && echo "/opt/apps/java/bin/java -jar ${dockerDestFolder}/current/*.war --spring.config.location=${dockerDestFolder}/current/env/application.properties >/dev/null 2>&1" > docker-entrypoint.sh
CMD .${dockerDestFolder}/current/docker-entrypoint.sh
EOF

# echo $TZ > /etc/timezone   TO someString to one file.
# To set the right time.
# ENV TZ=Asia/Shanghai
# RUN ln -snf /usr/share/zoneinfo/${TZ} /etc/localtime && echo ${TZ} > /etc/timezone
# COPY conf/${env}/*.cer ${dockerDestFolder}/${dateVersion}/


#read Dockerfile
echo [INFO] "Building image..."
docker build -t ${packageName}:${imageVersion} -f target/Dockerfile . || exit 1


echo [INFO] "Exporting image to target folder..."
docker save ${packageName}:${imageVersion} > target/${packageName}-${imageVersion}.image


