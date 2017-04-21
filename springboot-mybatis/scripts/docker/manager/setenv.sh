#ident  "%W%"
#!/bin/sh -x

echo "[INFO] calling setenv.sh"

#find out the absolute path of the script when being executed
if cd "`dirname $0`"; then
    SCRIPT_PATH=${PWD}
    echo "[INFO] SCRIPT_PATH=${SCRIPT_PATH}"
    cd "${OLDPWD}" || exit 1
else
    echo "[ERROR] cannot find script folder path"
    exit 1
fi


############
# call common-env.sh
############
. ${SCRIPT_PATH}/common-env.sh


#other env setup
#xxx
#xxx


echo "[INFO] setenv.sh is done"

#exit 0 is not allowed here which will stop calling script from going on executing




