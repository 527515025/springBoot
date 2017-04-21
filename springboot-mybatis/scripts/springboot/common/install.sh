#ident  "%W%"
#!/bin/sh -x


###############################
##  used to install package
###############################

#the path of this script when running
if cd "`dirname $0`"; then
    export SCRIPT_PATH=${PWD}
    echo "[INFO] export SCRIPT_PATH=${SCRIPT_PATH}"
    cd "${OLDPWD}" || exit 1
else
    exit 1
fi


#call setenv
. ${SCRIPT_PATH}/setenv.sh


APPVERSION=`date +%Y%m%d-%H%M%S`
LOG=/tmp/install-${prop_package}-${APPVERSION}.log

mylog "[INFO] Begin install.sh"

#check it is a single node installation or a cluster installation
if [ "${prop_isCluster}" = "true" ]; then
  mylog "[INFO] checked you are doing CLUSTER installation"
  myexecute "${SCRIPT_PATH}/install-cluster.sh"
else
  mylog "[INFO] checked you are doing SINGLE NODE installation"
  myexecute "${SCRIPT_PATH}/install-single.sh"
fi

exit 0
