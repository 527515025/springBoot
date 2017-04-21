#ident  "%W%"
#!/bin/sh -x


###############################
##  used for single server install
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
  myexecute ". ${SCRIPT_PATH}/preinstall.sh"
  if [ $? != 0 ]; then
      exit 1
  fi
fi	

mylog "[INFO] Begin install-single.sh"

APPVERSION=`date +%Y%m%d-%H%M%S`
LOG=/tmp/install-${prop_package}-${APPVERSION}.log


APPROOT=${prop_appRoot}
APPNAME=${prop_package}
APPHOME=${APPROOT}/${APPNAME}
HOSTNAME=`hostname`


SINGLE_NODE=${prop_singleNode}
MANUAL_FOLDERS=${prop_manualFolders}
MANUAL_FOLDER_MODE=${prop_manualFolderMode}


mylog ""
mylog "-----------------------------"
mylog "[INFO] Installing on host ${SINGLE_NODE}"


#create app home folder and new version folder
myexecute "mkdir -p ${APPHOME}/${APPVERSION}"
mylog "[INFO] Folder ${APPHOME}/${APPVERSION} is created successfully on host <<${SINGLE_NODE}>>" 

#copy files from current folder to app home folder
#-p reserve the specified attributes (default: mode,ownership,timestamps)
mylog "[INFO] copying files to host <<${SINGLE_NODE}>> by cp command"
myexecute "cp -rp ${SCRIPT_PATH}/../* ${APPHOME}/${APPVERSION}/"
mylog "[INFO] Copy is done!"

#change owner and group for app package
mylog "[INFO] changing owner and group recursively"
myexecute "chown -R ${prop_ownerGroup} ${APPHOME}/${APPVERSION}"
mylog "[INFO] Owner and group set up done!"

#create manual folders
if [ "${MANUAL_FOLDERS}" != "" ]; then
  for MANUAL_FOLDER in ${MANUAL_FOLDERS}
  do
	  myexecute "mkdir -p ${APPHOME}/${MANUAL_FOLDER}"
	
	  if [ "${MANUAL_FOLDER_MODE}" != "" ]; then
	    myexecute "chmod -R ${MANUAL_FOLDER_MODE} ${APPHOME}/${MANUAL_FOLDER}"
	  fi
  done
fi

#create soft link named 'current', -s:soft link -v:debug mode -n:treate link as normal file if it linking to a folder
#-f:force create it ignoring if it is already there
mylog "[INFO] Create soft link named 'current'"
myexecute "ln -svnf ${APPHOME}/${APPVERSION} ${APPHOME}/current"
mylog "[INFO] Link creation is done!"
mylog "--------------------------------"

mylog "[INFO] Installation is done successfully, for more detail please check log ${LOG}"
mylog "------------------------------------"
mylog "------------------------------------"

# The postinstall.sh has been lost.(It is unused.)
if [ "${prop_runPostinstall}" = "true" ]; then
  myexecute ". ${SCRIPT_PATH}/postinstall.sh"
fi

exit 0
