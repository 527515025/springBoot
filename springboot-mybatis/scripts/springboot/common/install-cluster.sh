#ident  "%W%"
#!/bin/sh -x


###############################
##  used for cluster install
###############################

#the path of this script when running
if cd "`dirname $0`"; then
    SCRIPT_PATH=${PWD}
    cd "${OLDPWD}" || exit 1
else
    exit 1
fi



#call setenv
. ${SCRIPT_PATH}/setenv.sh

# The preinstall.sh has been lost.(It is unused.)
if [ "${prop_runPreinstall}" = "true" ]; then
    myexecute "${SCRIPT_PATH}/preinstall.sh"
    if [ $? != 0 ]; then
      exit 1
    fi
fi

mylog "[INFO] Begin install-cluster.sh"

APPVERSION=`date +%Y%m%d-%H%M%S`
LOG=/tmp/install-${prop_package}-${APPVERSION}.log
APPROOT=${prop_appRoot}
APPNAME=${prop_package}
APPHOME=${APPROOT}/${APPNAME}
HOSTNAME=`hostname`


CLUSTER_NODES=${prop_clusterNodes}
#replace ',' with space
CLUSTER_NODES=${CLUSTER_NODES//,/ }
#replace '"' with null
CLUSTER_NODES=${CLUSTER_NODES//\"/}

MANUAL_FOLDERS=${prop_manualFolders}
MANUAL_FOLDER_MODE=${prop_manualFolderMode}



for i in ${CLUSTER_NODES}
do
  mylog ""
  mylog "-----------------------------"
  mylog "[INFO] Connecting to host ${i}"
  
  
  #create app home folder and new version folder
  myexecute "ssh -oStrictHostKeyChecking=no root@${i} mkdir -p ${APPHOME}/${APPVERSION}"
  mylog "[INFO] Folder ${APPHOME}/${APPVERSION} is created successfully on host <<${i}>>" 

  #copy files from current host to another node by scp command
  #-p preserves modification times, access times, and modes from the original file
  mylog "[INFO] copying files to host <<${i}>> by scp command"
  myexecute "scp -rp ${SCRIPT_PATH}/../* root@${i}:${APPHOME}/${APPVERSION}/"
  mylog "[INFO] Copy is done!"
  
  #change owner and group for app package
  mylog "[INFO] changing owner and group recursively"
  myexecute "ssh -oStrictHostKeyChecking=no root@${i} chown -R ${prop_ownerGroup} ${APPHOME}/${APPVERSION}/"
  mylog "[INFO] Owner and group set up done!"
  
  #create manual folders
  if [ "${MANUAL_FOLDERS}" != "" ]; then
      theCommand="ssh -oStrictHostKeyChecking=no root@${i} \"pwd "
      for MANUAL_FOLDER in ${MANUAL_FOLDERS}
	  do
	      theCommand="${theCommand} && mkdir -p ${APPHOME}/${MANUAL_FOLDER}"
		  if [ "${MANUAL_FOLDER_MODE}" != "" ]; then
		    theCommand="${theCommand} && chmod -R ${MANUAL_FOLDER_MODE} ${APPHOME}/${MANUAL_FOLDER}"
		  fi
	  done
	  theCommand="${theCommand} \" "
	  myexecute2 "${theCommand}"
  fi
  
  #create soft link named 'current', -s:soft link -v:debug mode -n:treate link as normal file if it linking to a folder
  #-f:force create it is already there
  mylog "[INFO] Create soft link named 'current'"
  myexecute "ssh -oStrictHostKeyChecking=no root@${i} ln -svnf ${APPHOME}/${APPVERSION} ${APPHOME}/current"
  mylog "[INFO] Link creation is done!"
  
  if [ "${prop_runPostinstall}" = "true" ]; then
    myexecute "ssh -oStrictHostKeyChecking=no root@${i} ${APPHOME}/current/scripts/postinstall.sh"
    mylog "[INFO] postinstall is done on node ${i}"
  fi

  mylog "--------------------------------"
done

mylog "[INFO] Cluster installation is done successfully, for more detail please check log ${LOG}"
exit 0
