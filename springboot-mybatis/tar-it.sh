#!/bin/bash



################################
# based on maven assembly plugin



environment=$1
package=$2
packageVersion=$3
packageFormat=$4

if [ "${environment}" = "" ]; then
    echo [ERROR] "Please provide env as first param"
    exit 1
fi

if [ "${package}" = "" ]; then
    echo [ERROR] "Please provide package name as second param"
    exit 1
fi

if [ "${packageVersion}" = "" ]; then
    echo [ERROR] "Please provide version as third param"
    exit 1
fi

if [ "${packageFormat}" = "" ]; then
    echo [ERROR] "Please provide package format as forth param"
    exit 1
fi


theCommand="mvn -e assembly:single -Dpackage=${package} -Dpackage.version=${packageVersion} -Dpackage.environment=${environment} -Dpackage.format=${packageFormat}"
echo ${theCommand}
${theCommand}

