#!/bin/bash
set -e

# color bash output
RED='\033[0;31m'
BLUE='\033[0;34m'
GREEN='\033[0;32m'
NC='\033[0m' # No Color

# required path
export WORKDIR=$(cd $(dirname $0) && pwd)

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
  ${GREEN}view${NC}    view available profiles
  ${GREEN}load${NC}    load desired profiles
  ${GREEN}unload${NC}  revert loaded profile
"
  exit 1
}

# view command
view () {
  (printf ${GREEN}"PROFILES${NC}\n"; ls -1 "$WORKDIR"/registry | sed -e 's/\.jshrc$//') | column -t
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
  WORKDIR=$2

  # add rc file
  JSHRC="${WORKDIR}/registry/${USER}.jshrc"

  # identify SHELL based on *.jshrc
  eval $(awk '/JSH_SHELL/{print $2}' ${JSHRC})
  case $JSH_SHELL in

    "zsh")
      echo -e "${BLUE}✔${NC} Setting up ${BLUE}zsh${NC} shell"
      sleep 0.5
      zsh -is <<< 'source ${WORKDIR}/registry/${USER}.jshrc; \
      clear; echo ✔ Successfully loaded ${USER}; \
      exec </dev/tty;'
      ;;

    "bash")
      echo -e "${GREEN}✔${NC} Setting up ${GREEN}bash${NC} shell"
      sleep 0.5
      bash -i <<< 'source ${WORKDIR}/registry/${USER}.jshrc; \
      clear; echo ✔ Successfully loaded ${USER}; \
      exec </dev/tty;'
      ;;
    *)
      echo -e "${RED}missing SHELL in *.jshrc!"
      ;;
  esac
}

# "main"
case "$1" in
  view|v)
    view
    ;;
  load|l)
    load "$2" "$WORKDIR"
    ;;
  *)
    help
    ;;
esac
