#!/bin/bash


cmd=ls
value=`$cmd`
echo ${value}

result=$($cmd 2>&1)
echo $result
