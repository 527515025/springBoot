#ident  "%W%"
#!/bin/sh -x

#find out the absolute path of the script when being executed
if cd "`dirname $0`"; then
    SCRIPT_PATH=${PWD}
    echo "[INFO] SCRIPT_PATH=${SCRIPT_PATH}"
    cd "${OLDPWD}" || exit 1
else
    echo "[ERROR] cannot find script folder path"
    exit 1
fi



#call setenv
. ${SCRIPT_PATH}/setenv.sh


mylog "[INFO] calling start.sh"


HOSTNAME=`hostname`
APPNAME=${prop_package}
APPROOT=${prop_appRoot}
APPHOME=${APPROOT}/${APPNAME}
LOG=/tmp/start-${APPNAME}.log



PROCESS_ID=`ps -ef | grep "${prop_dockerProcessKeyWord}" | grep -v grep | awk '{ print $2 }'`
isNumber ${PROCESS_ID}
#$? represents the result of last unix command
numberCheck=$?
if [ numberCheck ]; then
    #kill -0 will send a signal to the process to check if the process exists, 0 menas exists and 1 vice visa
    if kill -0 ${PROCESS_ID} > /dev/null 2>&1; then
      mylog ""
    else
      mylog "[ERROR]  docker daemon is not running on server <<${HOSTNAME}>>, pleae start it first"
      exit 1
    fi
fi

#check docker image:version running or not 
count=`docker ps --no-trunc=true | grep "${prop_dockerImageName}" | grep "${prop_dockerImageVersion}" | wc -l`
if [ ${count} = ${prop_dockerContainerCount} ]; then
    mylog "[WARN]  ${prop_dockerContainerCount} containers for ${APPNAME} are already running on server <<${HOSTNAME}>>, will do nothing "
    exit 0
elif [ ${count} = 0 ]; then
    mylog "[INFO]  checked no any ${APPNAME} is running on server <<${HOSTNAME}>>"
else
    mylog "[ERROR] ${count} containers for ${APPNAME} are already running, but ${prop_dockerContainerCount} is expected. Please check why"
    exit 0
fi






#check docker image name and version existing or not in doc repo
count=`docker images | grep "${prop_dockerImageName}" | grep "${prop_dockerImageVersion}" | wc -l`
if [ ${count} != 0 ]; then
    mylog "[INFO] image ${prop_dockerImageName} with version ${prop_dockerImageVersion} already exists in docker repo on host <<${HOSTNAME}>>"
    
    
    remove="yes"
    mylog "[????] Do you want to remove it and reload the image (default ${remove}):"
    read removeTmp
    if [ "${removeTmp}" != "" ]; then
	  	remove=${removeTmp}
	fi
	
	
	if [[ "yes" = "${remove}" || "YES" = "${remove}" ]]; then
	  	mylog "[COMMAND] docker rmi -f  ${prop_dockerImageName}:${prop_dockerImageVersion}"
      	docker rmi -f  ${prop_dockerImageName}:${prop_dockerImageVersion}
      
      	#check remove result
	    count=`docker images | grep "${prop_dockerImageName}" | grep "${prop_dockerImageVersion}" | wc -l`
	    if [ ${count} != 0 ]; then
	        mylog "[ERROR] failed to remove image, please check why or remove it manually"
	        exit 1
	    fi
	fi
    
    
fi


count=`docker images | grep "${prop_dockerImageName}" | grep "${prop_dockerImageVersion}" | wc -l`
if [ ${count} = 0 ]; then
    imageTar=${prop_dockerImageName}-${prop_dockerImageVersion}.img.tar
    mylog "[WARN]  image ${prop_dockerImageName} with version ${prop_dockerImageVersion} does not exist in docker repo on host <<${HOSTNAME}>>"
    mylog "[COMMAND] docker load < ${APPHOME}/current/${imageTar}"
    docker load < ${APPHOME}/current/${imageTar}
    
    #check loading result
    count=`docker images | grep "${prop_dockerImageName}" | grep "${prop_dockerImageVersion}" | wc -l`
    if [ ${count} != 1 ]; then
        mylog "[ERROR] failed to load image, please check why or load it manually"
        exit 1
    fi
fi


DOCKER_PORT_MAPPINGS=${prop_dockerPortMappings}
#replace ',' with space
DOCKER_PORT_MAPPINGS=${DOCKER_PORT_MAPPINGS//,/ }

DOCKER_CONFIG_FOLDER_MAPPING="${APPHOME}/current/${prop_dockerConfigFileMapping}"
DOCKER_LOG_FOLDER_MAPPING="${APPHOME}/${prop_dockerLogFolderMapping}"
DOCKER_UPLOAD_FOLDER_MAPPING="${APPHOME}/${prop_dockerUploadFolderMapping}"

#for port mapping, each time a new container will increate the port no.
#e.g for mapping 9200:9200, container 1 will have 9200:9200, container 2 will 9201:9200
i=0

while [ $i -le `expr ${prop_dockerContainerCount} - 1` ]
do
  #concatenate each port mapping
  FINAL_PORT_MAPPING=""
  
  for k in ${DOCKER_PORT_MAPPINGS}
  do
    SOURCE_PORT=`echo ${k} | awk '{ split($0,array,":")} END {print array[1]}'`
    TARGET_PORT=`echo ${k} | awk '{ split($0,array,":")} END {print array[2]}'`
    FINAL_PORT_MAPPING="${FINAL_PORT_MAPPING} -p `expr ${SOURCE_PORT} + ${i}`:${TARGET_PORT}"
  done
  
  DOCKER_NODE_NAME="Node${i}-${HOSTNAME}"
  
  #replace xxx with node name, each node will have seperate data folder/log folder
  FINAL_LOG_FOLDER_MAPPING=${DOCKER_LOG_FOLDER_MAPPING//nodexxx/${DOCKER_NODE_NAME}}
  
  
  #create the mapping folder if not existing in host 
  # and change folder mode to 777 in case that docker containers cannot write to host folder
  SOURCE_LOG_FOLDER=`echo ${FINAL_LOG_FOLDER_MAPPING} | awk '{ split($0,array,":")} END {print array[1]}'`
  
  #create source folder is not existing
  myexecute "mkdir -p ${SOURCE_LOG_FOLDER}"
  
  
  #change mode in case that docker container cannot write data to host folder
  myexecute "chmod -R 777 ${SOURCE_LOG_FOLDER}"
  
  
  
  mylog "[INFO] starting new docker container for ${APPNAME} on host <<${HOSTNAME}>>"
  myexecute "docker run \
      --net=host \
	  -d \
	  ${FINAL_PORT_MAPPING} \
	  -v ${prop_javaHome}:/opt/apps/java \
	  -v ${DOCKER_CONFIG_FOLDER_MAPPING} \
	  -v ${FINAL_LOG_FOLDER_MAPPING} \
	  -v ${DOCKER_UPLOAD_FOLDER_MAPPING} \
	  ${prop_dockerImageName}:${prop_dockerImageVersion} \
     "
  
  
  #i++
  i=`expr $i + 1`
  
  #sleep to wait server process up
  myexecute "sleep ${prop_startOneSleep}"
  mylog "------------------------------------"
  mylog ""
done




count=`docker ps --no-trunc=true | grep "${prop_dockerImageName}" | grep "${prop_dockerImageVersion}" | wc -l`
if [ ${count} = ${prop_dockerContainerCount} ]; then
    mylog "[INFO]  ${prop_dockerContainerCount} docker containers for ${APPNAME} started successfully on server <<${HOSTNAME}>>"
elif [ ${count} = 0 ]; then
	mylog "[ERROR] All containers for ${APPNAME} failed to start on server <<${HOSTNAME}>>, yon can check log by /var/lib/docker/containers/xxx/*.log"
else
    mylog "[ERROR] ${prop_dockerContainerCount} containers for ${APPNAME} are expected, but only ${count} started on server <<${HOSTNAME}>>, yon can check log by /var/lib/docker/containers/xxx/*.log"
fi

exit 0





