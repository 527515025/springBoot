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


mylog "[INFO] calling stop.sh"


APPNAME=${prop_package}
HOSTNAME=`hostname`
STOP_TIMEOUT=${prop_stopNodeTimeout}


mylog "[INFO] Stopping containers for  ${APPNAME} on host ${HOSTNAME}"

PROCESS_ID=`ps -ef | grep "${prop_dockerProcessKeyWord}" | grep -v grep | awk '{ print $2 }'`
isNumber ${PROCESS_ID}
#$? represents the result of last unix command
numberCheck=$?
if [ numberCheck ]; then
    #kill -0 will send a signal to the process to check if the process exists, 0 menas exists and 1 vice visa
    if kill -0 ${PROCESS_ID} > /dev/null 2>&1; then
      mylog "[DEBUG]  checked docker daemon is running well on server <<${HOSTNAME}>> with pid: ${PROCESS_ID}"
    else
      mylog "[ERROR]  docker daemon is not running on server <<${HOSTNAME}>>, pleae start it first"
      exit 0
    fi
fi

#check nginx container existing or not 
count=`docker ps | grep "${prop_dockerImageName}" | grep "${prop_dockerImageVersion}" | wc -l`
if [ ${count} != 0 ]; then
    mylog "[INFO] checked total ${count} containers for ${APPNAME} are running, gonna stop them"
    
    CONTAINER_IDS=`docker ps | grep "${prop_dockerImageName}" | grep "${prop_dockerImageVersion}" | awk '{ print $1 }'`
    
    for i in $CONTAINER_IDS
    do
      mylog "[INFO]  stopping container id <<${i}>>"
      
      myexecute "docker stop ${i}"
      
      myexecute "sleep ${STOP_TIMEOUT}"
      
      #check again if the container is stopped successfully
      count=`docker ps | grep "${prop_dockerImageName}" | grep "${prop_dockerImageVersion}" | grep ${i} | wc -l`
      if [ ${count} != "0" ]; then
      
        mylog "[WARN] failed to stop the container <<${i}>>, gonna kill it"
        myexecute "docker kill ${i}"
      fi
      
    done
else
    mylog "[INFO] no any container for ${APPNAME} is running, will do nothing"
    exit 0;
fi


#check finally
count=`docker ps | grep "${prop_dockerImageName}" | grep "${prop_dockerImageVersion}" | wc -l`
if [ ${count} != "0" ]; then
	mylog "[ERROR] failed to stop/kill all containers for ${APPNAME}, please stop it manually"
fi

mylog "[INFO] all containers for ${APPNAME} are stopped successfully on server <<${HOSTNAME}>>"
exit 0


