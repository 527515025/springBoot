#!/bin/sh


# the java must be goal .I think it may be not very good. （Sean 20170216）
COUNT=`java -version 2>&1 | grep 'java version' | grep 1.8 |  grep -v grep | wc -l`
if [ ${COUNT} != 1 ]; then
  COUNT=`echo $JAVA_HOME | grep 1.8 |  grep -v grep | wc -l`
  if [ ${COUNT} != 1 ]; then
    echo [ERROR] "Cannot find java 8"
    exit 1
  fi
fi


package=$1
version=$2
env=$3
resources=$4

if [ "${package}" = "" ]; then
    echo [ERROR] "Please provide package name as first param"
    exit 1
fi

if [ "${version}" = "" ]; then
    echo [ERROR] "Please provide package version as second param"
    exit 1
fi

if [ "${env}" = "" ]; then
    echo [ERROR] "Please provide env as third param"
    exit 1
fi

#在pom.xml文件里面定义的 maven的参数 详细请看 maven -D 命令的含义
#-X To debug the command.
theCommand="mvn clean package -Dpackage=${package} -Dpackage.version=${version} -Dpackage.environment=${env} -Dpackage.resources=${resources} "
echo [INFO] ${theCommand}
exec ${theCommand}



