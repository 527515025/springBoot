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


#check java home
JAVA_HOME=${prop_javaHome}
if [ "${JAVA_HOME}" = "" ]; then
    mylog "[ERROR] JAVA_HOME cannot be null"
    exit 1
elif [ ! -d ${JAVA_HOME} ]; then
    mylog "[ERROR] ${JAVA_HOME} does not exist as JAVA_HOME"
    exit 1
fi


#check if springboot process is up or not
PROCESS_ID=`ps -ef | grep java | grep "${prop_springbootProcessKeyWord}" | grep -v grep | awk '{ print $2 }'`
if [ ${PROCESS_ID} ]; then
  mylog "[WARN] ${APPNAME} is already running with process id ${PROCESS_ID} on server <<${HOSTNAME}>>"
  exit 0
else
  mylog "[INFO] checked no ${APPNAME} running process on server <<${HOSTNAME}>>, will start it"
fi

#start up new process
springbootExecutableWar=${APPHOME}/current/${prop_springbootWarName}-${prop_springbootWarVersion}.war
myexecute2 "nohup ${JAVA_HOME}/bin/java -jar  ${springbootExecutableWar} --spring.config.location=${APPHOME}/current/env/application.properties >/dev/null 2>&1 &"

myexecute2 "sleep ${prop_startOneSleep}"
PROCESS_ID=`ps -ef | grep java | grep "${prop_springbootProcessKeyWord}" | grep -v grep | awk '{ print $2 }'`
if [ ${PROCESS_ID} ]; then
  mylog "[INFO] ${APPNAME} started successfully with process id ${PROCESS_ID} on server <<${HOSTNAME}>>"
else
  mylog "[ERROR] ${APPNAME} failed to start up on server <<${HOSTNAME}>>, you can check log ${LOG}"
fi

exit 0





