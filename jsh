#!/bin/bash
set -e

# color bash output
RED='\033[0;31m'
BLUE='\033[0;34m'
GREEN='\033[0;32m'
NC='\033[0m' # No Color

# required path
export WORKDIR=$(cd $(dirname $0) && pwd)
export REGISTRY=s3://jsh-release/profiles/

# help command
help() {
  echo -e " ${GREEN}
    _     _
   (_)___| |__
   | / __| '_ \\
   | \__ \ | | |
  _/ |___/_| |_|
 |__/
${NC}
Version: 1.0
Usage: jsh [command]

Commands:
  ${GREEN}upload${NC}   upload profile
  ${GREEN}list${NC}     list available profiles
  ${GREEN}view${NC}     view profile
  ${GREEN}load${NC}     load profile

"
  exit 1
}

# upload command
upload () {

  help_upload() {
  echo -e "Usage: jsh ${GREEN}upload${NC} [PATH]"
    exit 1
  }

  [ -z "$1" ] && help_upload
  echo -e "... Uploading ${BLUE}$1"
  aws s3 cp "$1" ${REGISTRY} --quiet
  echo -e "${GREEN}✔${NC} Successfully ${GREEN}uploaded${NC} ${BLUE}$1"
}

# list command
list () {
  aws s3 ls ${REGISTRY}
  # TODO -> clean up
#  (printf ${GREEN}"PROFILES${NC}\n"; ls -1 "$WORKDIR"/registry | sed -e 's/\.jshrc$//') | column -t
}

# view command
view () {

  help_view() {
  echo -e "Usage: jsh ${GREEN}view${NC} [PATH]"
    exit 1
  }

  [ -z "$1" ] && help_view
  echo -e "... Fetching ${BLUE}$1${NC}\n"
  aws s3 cp ${REGISTRY}${1} /dev/stdout
  echo -e "\n\n${GREEN}✔${NC} Successfully ${GREEN}fetched${NC} ${BLUE}$1"
}

# load command
load () {

  help_load() {
  echo -e "Usage: jsh ${GREEN}load${NC} [USER]"
    exit 1
  }

  [ -z "$1" ] && help_load

  # define default variables
  USER=$1
  JSHRC=/tmp/jsh/current_profile.jshrc
  echo -e "... Fetching ${BLUE}$1${NC}"
  aws s3 cp --quiet ${REGISTRY}${USER} ${JSHRC}
  echo -e "${GREEN}✔${NC} Successfully ${GREEN}fetched${NC} ${BLUE}$1"

  # TODO -> indicate inside of shell

  # identify SHELL based on *.jshrc
  eval $(awk '/JSH_SHELL/{print $2}' ${JSHRC})
  case $JSH_SHELL in

    "zsh")
      echo -e "${BLUE}✔${NC} Setting up ${BLUE}zsh${NC} shell"
      echo 'export PROMPT="$bg[green]% $fg[black]% %n@jshrc ▶ %c ㇇%{$reset_color%}"' >> ${JSHRC}
      sleep 2
      JSHRC=$JSHRC USER=$USER zsh -is <<< 'source ${JSHRC}; \
      clear; echo ✔ Successfully loaded ${USER}; \
      exec </dev/tty;'
      ;;

    "bash")
      echo -e "${GREEN}✔${NC} Setting up ${GREEN}bash${NC} shell"
      echo 'export PS1="\e[0;42m\u@jsh ▶ \W ㇇\e[m"' >> ${JSHRC}
      sleep 2
      JSHRC=$JSHRC USER=$USER bash -i <<< 'source ${JSHRC}; \
      clear; echo ✔ Successfully loaded ${USER}; \
      exec </dev/tty;'
      ;;
    *)
      echo -e "${RED}missing JSH_SHELL in *.jshrc!"
      ;;
  esac
}

# "main"
case "$1" in
  upload)
    upload "$2"
    ;;
  list)
    list
    ;;
  view)
    view "$2"
    ;;
  load)
    load "$2"
    ;;
  *)
    help
    ;;
esac
