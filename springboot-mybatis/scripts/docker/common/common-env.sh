#ident  "%W%"
#!/bin/sh -x

#SCRIPT_PATH must be defined firstly by calling script
echo "[INFO] calling common-env.sh"
if [ "${SCRIPT_PATH}" = "" ]; then
  echo "[ERROR] SCRIPT_PATH is not defined in calling script"
  exit 1
else
    #############
	# define own function
	#############
	
	#print out command into console (and log file if there is one) and then execute it
	myexecute () {
	  local dt=`date +%Y-%m-%d\ %H:%M:%S`
	  local theCommand=${1}
	  echo "${dt}: [COMMAND] ${theCommand}"
	  if [ "${LOG}" != "" ]; then
	    echo "${dt}: [COMMAND] ${theCommand}" >> ${LOG}
	  fi
	  
	  ${theCommand}
	  return $?
	}
	
	myexecute2 () {
	  local dt=`date +%Y-%m-%d\ %H:%M:%S`
	  local theCommand=${1}
	  echo "${dt}: [COMMAND] ${theCommand}"
	  if [ "${LOG}" != "" ]; then
	    echo "${dt}: [COMMAND] ${theCommand}" >> ${LOG}
	  fi
	  
	  eval ${theCommand}
	  return $?
	}
	
	#print out log into console (and log file if there is one)
	mylog () {
	  local dt=`date +%Y-%m-%d\ %H:%M:%S`
	  local theLog=${1}
	  echo "${dt}: ${theLog}"
	  if [ "${LOG}" != "" ]; then
	    echo "${dt}: ${theLog}" >> ${LOG}
	  fi
	  
	}
	
	#print out log into console (and log file if there is one), and transate \n into new line
	mylog2 () {
	  local dt=`date +%Y-%m-%d\ %H:%M:%S`
	  local theLog=${1}
	  echo -e "${dt}: ${theLog}"
	  if [ "${LOG}" != "" ]; then
	    echo -e "${dt}: ${theLog}" >> ${LOG}
	  fi
	  
	}
	
	#check it is a number or not
	isNumber () {
	  var=${1}
	  if [ ${var} -eq ${var} 2> /dev/null ]
	  then
	    return 0
	  else
	    return 1
	  fi
    }
    
    

	############
	# to export variables
	############
	
	#export all variables in env.properties
	echo "[INFO] export all variables in env.properties"
	if [ -f ${SCRIPT_PATH}/../env/env.properties ]; then
	  myexecute "source ${SCRIPT_PATH}/../env/env.properties"
	elif [ -f ${SCRIPT_PATH}/../env/env.properties ]; then
	  myexecute "source ${SCRIPT_PATH}/../env/env.properties"
	else
	  echo "[ERROR] cannot find env.properties"
	  exit 1
	fi
	
fi


echo "[INFO] common-env.sh is done"

#exit 0 is not allowed here which is stop calling script from going on executing 




