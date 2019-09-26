#!/bin/bash
set -e

# color bash output
RED='\033[0;31m'
BLUE='\033[0;34m'
GREEN='\033[0;32m'
NC='\033[0m' # No Color

# required path
export WORKDIR=$(cd $(dirname $0) && pwd)

# help
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

case "$1" in
  view|v)
    "$WORKDIR/commands/view" "$WORKDIR"
    ;;
  load|l)
    "$WORKDIR/commands/load" "$2" "$WORKDIR"
    ;;
  unload|u)
    "$WORKDIR/commands/unload"
    ;;
  *)
    help
    ;;
esac
