#!/bin/bash

#####################
# bash only
# usage ./package.sh
#####################

#declare -a: define an array
#declare -i: define an integer
declare -i index=1
declare -a envMap
declare -a formatMap=(["1"]="docker" ["2"]="springboot")

#GET THE URL OF CURRENT PATH
CURRENT_DIR=`dirname $0`
cd $CURRENT_DIR

#########################
# confirm the env 
#########################
index=1
#scan the envs from conf/*
for i in env/* ; do 
 if [ -d ${i} ]; then
	envMap["${index}"]="`basename ${i}`"
	index=index+1
 fi
done

#Print the choice
env=1
echo "Please choose the environment by typing the number [default ${env}]"
for i in ${!envMap[@]} ;do
	echo [${i}]:${envMap[${i}]}
done

#Read the envTmp . Set The finalEnv. Pay Attention 'read' NOT 'Read'
read envTmp
# echo "${envTmp}"
if [ "${envTmp}" != "" ]; then
  env=${envTmp}
fi
# echo "${env}"
finalEnv=${envMap[${env}]}
echo "${finalEnv}"
echo "[INFO] You are choosing environment: ${finalEnv}"
echo "[INFO] Set the env properties by env/${finalEnv}/env.properties"

#Load the env/${finalEnv}/env.properties
. env/${finalEnv}/env.properties
echo ""
echo ""

#########################
# confirm the format 
#########################
format=1
for i in ${!formatMap[@]};do
	echo [${i}]: ${formatMap[${i}]}
done
read formatTmp
if [ "${formatTmp}" != "" ]; then
  format=${formatTmp}
fi
finalFormat=${formatMap[${format}]}
#########################
# confirm the package 
#########################
finalPackage=${prop_package}
echo "[INFO] You are choosing package version: ${finalPackage}" 
echo ""
echo ""

#########################
# confirm the version 
#########################
finalPackageVersion=${prop_packageVersion}
echo "[INFO] You are choosing package version: ${finalPackageVersion}" 
echo ""
echo ""

######################
# begin packing
######################
if [ -n "${finalFormat}" ] && [ "${finalFormat}" = "springboot" ]; then
	##############################
	# generate executable war file
	##############################

	#noneed.properties The .properties is outside the war . So it is no need any more.
	theCommand="./war-it.sh ${finalPackage} ${finalPackageVersion} ${finalEnv} 'noneed.properties'"
	echo [INFO] ${theCommand}
	eval ${theCommand} || exit 1
else
	##############################
	# generate executable war file
	##############################
	theCommand="./war-it.sh ${finalPackage} ${finalPackageVersion} ${finalEnv} 'noneed.properties'"
	echo [INFO] ${theCommand}
	eval ${theCommand} || exit 1
	
	
	
	#####################################
	# generate docker image and export it
	#####################################
	theCommand="./docker-it.sh ${finalPackage} ${finalPackageVersion} ${prop_dockerDestFolder} ${finalEnv}"
	echo [INFO] ${theCommand}
	eval ${theCommand} || exit 1
fi

theCommand="./tar-it.sh ${finalEnv} ${finalPackage} ${finalPackageVersion} ${finalFormat}"
echo [INFO] ${theCommand}
eval ${theCommand} || exit 1

echo "[INFO] package is done successfully"

