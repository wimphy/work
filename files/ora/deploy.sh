#!/usr/bin/env bash

if [[ ! -d ${HOME}/.oic ]]; then
  mkdir ${HOME}/.oic
fi

cp ./ant ${HOME}/.oic/
cp ./mvn ${HOME}/.oic/
cp ./oicrc.sh ${HOME}/.oic/
cp ./sync_ics.sh ${HOME}/.oic/

source ./oicrc.sh
cd ${OIC_HOME}