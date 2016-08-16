#!/bin/sh

if [ $# -lt 1 ]; then
  echo "指定された引数は$#個です。" 1>&2
  echo "実行するには1個以上の引数が必要です。" 1>&2
  exit 1
fi

servlet_api="/usr/share/tomcat7/lib/servlet-api.jar "
cmd="javac -classpath "$servlet_api
space=" "

while [ $# -gt 0 ]
do
	# 処理
	cmd=$cmd$1$space
	shift
done

result=$($cmd 2>&1)

if [ "$result" = "" ]; then
  mv *.class classes
  /etc/init.d/tomcat7 restart
  echo "Done"
else
  echo $result
fi
