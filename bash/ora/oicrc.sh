#!/bin/bash
#ADE hacking ...
export OIC_HOME=$HOME/sim/oic
export MAVEN_LOCAL_REPO=${OIC_HOME}/.m2
export ADE_VIEW_ROOT=${OIC_HOME}/view
export MAVEN_HOME="$ADE_VIEW_ROOT/soacommon/tools/apache-maven-3.0.5"
export M2_HOME=${MAVEN_HOME}
export ANT_HOME="$ADE_VIEW_ROOT/ant"
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.7.0_80.jdk/Contents/Home
export PCHOME=${ADE_VIEW_ROOT}/pcbpel
export pchome=${PCHOME}
export ESE_RTF_LOCAL_URL=http://artifactory-slc-prod.oraclecorp.com/artifactory
export NDE_VOB_ROOT=${ADE_VIEW_ROOT}
export T_WORK=${OIC_HOME}/fmwhome12
export MW_HOME=${ADE_VIEW_ROOT}/oracle/work
export ADE_VIEW_NAME=xiaomiwa_oic_code
export ITOOLS_SOA_HOME=${OIC_HOME}/others/mac/lib
export MAC_FUZZ_HOME=${OIC_HOME}/others/mac
export PATH=${MAC_FUZZ_HOME}/bin:${JAVA_HOME}/bin:$PATH

#ANT_ARGS="-lib ${ADE_VIEW_ROOT}/buildtools/jlib/mail.jar -lib ${ADE_VIEW_ROOT}/buildtools/jlib/activation.jar -lib ${ADE_VIEW_ROOT}/pcbpel/utl/ant/lib/ant-contrib-1.0b1.jar -lib ${ADE_VIEW_ROOT}/pcbpel/patch_lib/ant-logger.jar -lib ${ADE_VIEW_ROOT}/pcbpel/qa/as11tests/lib/ftp/ -lib ${ADE_VIEW_ROOT}/bpm/tools/antlib/maven-ant-tasks-2.1.1.jar -lib ${ADE_VIEW_ROOT}/bpm/tools/antlib/ant-apb.jar -lib ${ADE_VIEW_ROOT}/bpm/tools/antlib/ant-fuego-tools.jar -lib ${ADE_VIEW_ROOT}/bpm/tools/antlib/asm.jar -lib ${ADE_VIEW_ROOT}/bpm/tools/antlib/bcel.jar -lib ${ADE_VIEW_ROOT}/bpm/tools/antlib/ivy-1.4.1.jar -lib ${ADE_VIEW_ROOT}/bpm/tools/antlib/p-unit.jar -lib ${ADE_VIEW_ROOT}/bpm/tools/antlib/qdox.jar -lib ${M2_HOME}/ant/lib/maven-ant-tasks-2.1.1.jar -Dtools.dir=${ADE_VIEW_ROOT}/bpm/tools -Dfuego.basedir=${ADE_VIEW_ROOT}/bpm -logger com.oracle.ant.logger.PcbpelLogger -Dant.build.javac.source=1.6 -Dant.build.javac.target=1.6"

export RSERVER=slc11jrq.us.oracle.com
export RUSER=xiaomiwa

get(){
  p2=$2
  if [[ -z $2 ]]
    then
      p2='./'
  fi
  p1=$1
  if [[ $1 != /* ]]
    then
      p1='/home/xiaomiwa/'$p1
  fi
  scp -r ${RUSER}@${RSERVER}:"$p1" "$p2"
}

put(){
  p2=$2
  if [[ -z $2 ]]
    then
      p2='/home/xiaomiwa/'
  fi
  scp "$1" ${RUSER}@${RSERVER}:"$p2"
}