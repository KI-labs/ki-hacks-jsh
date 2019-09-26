#!/bin/bash
set -e

# color bash output
RED='\033[0;31m'
BLUE='\033[0;34m'
GREEN='\033[0;32m'
NC='\033[0m' # No Color

# define arguments
for i in "$@"
do
case ${i} in
    -u=*|--user=*)
    USER="${i#*=}"
    shift # past argument=value
    ;;
    -c=*|--context=*)
    CONTEXT="${i#*=}"
    shift # past argument=value
    ;;
    *)
          # unknown option
    ;;
esac
done

echo """
    _     _
   (_)___| |__
   | / __| '_ \\
   | \__ \ | | |
  _/ |___/_| |_|
 |__/
"""

# define default variables
CONTEXT=${CONTEXT:-${PWD}}

# add rc file
echo "source ${CONTEXT}/registry/${USER}.jshrc"
source ${CONTEXT}/registry/${USER}.jshrc
echo -e "${GREEN}✔${NC} Successfully loaded ${GREEN}${USER}${NC}"

## remove jsh file
#exec "$0"