#!/bin/bash
OIC_HOME=$HOME/sim/oic
SCRIPTS=${OIC_HOME}/bin

if [[ ! -d ${SCRIPTS} ]]; then
  mkdir -p ${SCRIPTS}
fi
#cd ${SCRIPTS}

OIC_RC=${SCRIPTS}/oicrc.sh
if [[ ! -f ${OIC_RC} ]]; then
cat > ${OIC_RC} <<EOF
#!/bin/bash
#ADE hacking ...
export OIC_HOME=${OIC_HOME}
export MAVEN_LOCAL_REPO=\${OIC_HOME}/.m2
export ADE_VIEW_ROOT=\${OIC_HOME}/view
export MAVEN_HOME="\$ADE_VIEW_ROOT/soacommon/tools/apache-maven-3.0.5"
export M2_HOME=\${MAVEN_HOME}
export ANT_HOME="\$ADE_VIEW_ROOT/ant"
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.7.0_80.jdk/Contents/Home
export PCHOME=\${ADE_VIEW_ROOT}/pcbpel
export pchome=\${PCHOME}
export ESE_RTF_LOCAL_URL=http://artifactory-slc-prod.oraclecorp.com/artifactory
export NDE_VOB_ROOT=\${ADE_VIEW_ROOT}
export T_WORK=\${OIC_HOME}/fmwhome12
export MW_HOME=\${ADE_VIEW_ROOT}/oracle/work
export ADE_VIEW_NAME=oic_code
export ITOOLS_SOA_HOME=\${OIC_HOME}/others/mac/lib
export MAC_FUZZ_HOME=\${OIC_HOME}/others/mac
export PATH=\${MAC_FUZZ_HOME}/bin:\${JAVA_HOME}/bin:\$PATH

export RSERVER=slc11jrq.us.oracle.com
export RUSER=xiaomiwa

get(){
  p2=\$2
  if [[ -z \$2 ]]
    then
      p2='./'
  fi
  p1=\$1
  if [[ \$1 != /* ]]
    then
      p1='/home/xiaomiwa/'\$p1
  fi
  scp -r \${RUSER}@\${RSERVER}:"\$p1" "\$p2"
}

put(){
  p2=\$2
  if [[ -z \$2 ]]
    then
      p2='/home/xiaomiwa/'
  fi
  scp "\$1" \${RUSER}@\${RSERVER}:"\$p2"
}
EOF
fi

OIC_ANT=${SCRIPTS}/ant
if [[ ! -f ${OIC_ANT} ]]; then
cat > ${OIC_ANT} <<EOF
#!/bin/bash
source ${OIC_RC} > /dev/null

ANT_ARGS="-lib \${ADE_VIEW_ROOT}/buildtools/jlib/mail.jar"
ANT_ARGS=\${ANT_ARGS}" -lib \${ADE_VIEW_ROOT}/buildtools/jlib/activation.jar"
ANT_ARGS=\${ANT_ARGS}" -lib \${ADE_VIEW_ROOT}/pcbpel/utl/ant/lib/ant-contrib-1.0b1.jar"
ANT_ARGS=\${ANT_ARGS}" -lib \${ADE_VIEW_ROOT}/pcbpel/patch_lib/ant-logger.jar"
ANT_ARGS=\${ANT_ARGS}" -lib \${ADE_VIEW_ROOT}/pcbpel/qa/as11tests/lib/ftp/"
ANT_ARGS=\${ANT_ARGS}" -lib \${ADE_VIEW_ROOT}/bpm/tools/antlib/maven-ant-tasks-2.1.1.jar"
ANT_ARGS=\${ANT_ARGS}" -lib \${ADE_VIEW_ROOT}/bpm/tools/antlib/ant-apb.jar"
ANT_ARGS=\${ANT_ARGS}" -lib \${ADE_VIEW_ROOT}/bpm/tools/antlib/ant-fuego-tools.jar"
ANT_ARGS=\${ANT_ARGS}" -lib \${ADE_VIEW_ROOT}/bpm/tools/antlib/asm.jar"
ANT_ARGS=\${ANT_ARGS}" -lib \${ADE_VIEW_ROOT}/bpm/tools/antlib/bcel.jar"
ANT_ARGS=\${ANT_ARGS}" -lib \${ADE_VIEW_ROOT}/bpm/tools/antlib/ivy-1.4.1.jar"
ANT_ARGS=\${ANT_ARGS}" -lib \${ADE_VIEW_ROOT}/bpm/tools/antlib/p-unit.jar"
ANT_ARGS=\${ANT_ARGS}" -lib \${ADE_VIEW_ROOT}/bpm/tools/antlib/qdox.jar"
ANT_ARGS=\${ANT_ARGS}" -lib \${M2_HOME}/ant/lib/maven-ant-tasks-2.1.1.jar"
ANT_ARGS=\${ANT_ARGS}" -Dtools.dir=\${ADE_VIEW_ROOT}/bpm/tools"
ANT_ARGS=\${ANT_ARGS}" -Dfuego.basedir=\${ADE_VIEW_ROOT}/bpm"
ANT_ARGS=\${ANT_ARGS}" -logger com.oracle.ant.logger.PcbpelLogger"
ANT_ARGS=\${ANT_ARGS}" -Dant.build.javac.source=1.6"
ANT_ARGS=\${ANT_ARGS}" -Dant.build.javac.target=1.6"
if [[ -n "\$ADE_VIEW_ROOT" ]]
then
  set -- \${ANT_ARGS} "\$@"
  export MAVEN_OPTS='-Xmx1024m -XX:MaxPermSize=512m'
fi

pwd
echo exec "\$ANT_HOME/bin/ant" "\$@"
exec "\$ANT_HOME/bin/ant" "\$@"

EOF
chmod +x ${OIC_ANT}
fi

OIC_MVN=${SCRIPTS}/mvn
if [[ ! -f ${OIC_MVN} ]]; then
cat > ${OIC_MVN} <<EOF
#!/bin/bash
source ${OIC_RC} > /dev/null

if [[ -n "\$PCHOME" ]]
then
  set -- -B -gs \${PCHOME}/tools/maven/conf/settings.xml -s \${PCHOME}/tools/maven/conf/settings-user.xml "\$@"
  export MAVEN_OPTS='-Xmx1024m -XX:MaxPermSize=512m'
fi

pwd
echo exec "\$M2_HOME/bin/mvn" "\$@"
exec "\$M2_HOME/bin/mvn" "\$@"
EOF
chmod +x ${OIC_MVN}
fi

OIC_SYNC=${SCRIPTS}/sync_ics.sh
if [[ ! -f ${OIC_SYNC} ]]; then
  cp $0 ${SCRIPTS}/
fi
pushd ${SCRIPTS} > /dev/null
source ./oicrc.sh > /dev/null
popd > /dev/null

label=$1
project=$2
baseSrcPath=/ade_autofs/gd92_fmwcdc/PCBPEL_ICSMAIN_GENERIC.rdd/${label}
codeRoot=${OIC_HOME}/view

sync_code() {
  local m=$1
  if [[ -z ${m} ]]; then
    return 1
  fi
  echo "[---->>>> sim sync] copying source code for ${m}"
  exclude="--exclude=.ade_path"
  exclude=${exclude}" --exclude=/DELETE"
  exclude=${exclude}" --exclude=/qa"
  exclude=${exclude}" --exclude=/samples_as11r1"
  rsync -rtvuL ${exclude} ${RUSER}@${RSERVER}:${baseSrcPath}/${m}/ ${codeRoot}/${m}/
  echo "[---->>>> sim sync] done ..."
  echo ""
  echo ""
}

fuzz_files() {
  cd ${ITOOLS_SOA_HOME}
  get /net/slcnas458/export/fmw_soadev/relmgmt/imerge/itools.soa/build-imerge.xml
  fussLib=${codeRoot}/oracle/work/wlserver/server/lib
  if [[ ! -d ${fussLib} ]]; then
    mkdir -p ${fussLib}
  fi
  cd ${fussLib}
  get ${baseSrcPath}/pcbpel/lib/weblogic.jar
}

prepare() {
    regx="[0-9]{6}\.[0-9]{4}\.[0-9]{4}"
    if [[ ${label} =~ $regx ]] || [[ ${label} == "LATEST" ]]; then
      if [[ "-dep" == "${project}" ]]; then
        sync_code pcbpel/common/repo
        sync_code pcbpel/generated
        sync_code pcbpel/patch_lib
        sync_code pcbpel/lib
        sync_code maven/repo
        exit 0
      fi
      if [[ ! -z ${project} ]]; then
        rsync -aL ${RUSER}@${RSERVER}:${baseSrcPath}/${project}/ ${codeRoot}/${project}/
        exit 0
      fi
      sync_code pcbpel
      sync_code maven/repo
      fuzz_files
      cd ${PCHOME}
      ant clean-local-maven-repository
      ant adapter-binding
    fi
}

##need force sync required modules
oic_compile() {
  local m=$1
  if [[ -z ${m} ]]; then
    return 1
  fi
  if [[ ${label} != "-c" ]]; then
    rsync -aL ${RUSER}@${RSERVER}:${baseSrcPath}/${m}/ ${codeRoot}/${m}/
  fi
  cd ${codeRoot}/${m}
  mvn install -N
  if [[ $? -ne 0 ]] ; then
    echo 'could not perform tests'
    exit 1
  fi
}

if [[ ! -d ${codeRoot} ]]; then
  mkdir -p ${codeRoot}
fi

#oic_compile pcbpel/modules/uitools
#oic_compile pcbpel/modules/adapter
#oic_compile pcbpel/modules/adapter/cloud
#oic_compile pcbpel/modules/adapter/cloud-adapter-plugins
if [[ ${label} != "-c" ]]; then
  prepare
fi
oic_compile pcbpel/modules/adapter/cloud/runtime-framework
oic_compile pcbpel/modules/adapter/cloud/designtime-framework
rm -f ${codeRoot}/pcbpel/generated/adapter/CCSAdapter.jar
rm -f ${codeRoot}/pcbpel/generated/ide/oracle.cloud.adapter.ccs.jar
oic_compile pcbpel/modules/adapter/cloud-adapter-plugins/cloud.adapter.ccs