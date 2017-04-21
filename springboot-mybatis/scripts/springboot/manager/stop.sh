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


mylog "[INFO] Stopping ${APPNAME} on host ${HOSTNAME}"

#check if springboot process is up or not
PROCESS_ID=`ps -ef | grep java | grep "${prop_springbootProcessKeyWord}" | grep -v grep | awk '{ print $2 }'`
if [ ${PROCESS_ID} ]; then
  mylog "[INFO] found running ${APPNAME} process with id = ${PROCESS_ID} on server <<${HOSTNAME}>>, will stop it"
  #stop running process
  myexecute2 "kill -9 ${PROCESS_ID}"
  myexecute2 "sleep ${prop_stopNodeTimeout}"
else
  mylog "[WARN] no running ${APPNAME} process on server <<${HOSTNAME}>>"
  exit 0
fi

#check again
PROCESS_ID=`ps -ef | grep java | grep "${prop_springbootProcessKeyWord}" | grep -v grep | awk '{ print $2 }'`
if [ ${PROCESS_ID} ]; then
  mylog "[ERROR] failed to stop running ${APPNAME} process with id = ${PROCESS_ID} on server <<${HOSTNAME}>>, you can check log ${LOG}"
  exit 1
else
  mylog "[INFO] ${APPNAME} is stopped successfully on server <<${HOSTNAME}>>"
fi

exit 0


