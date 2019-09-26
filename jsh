#!/bin/bash
set -e

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

# loading user
echo "======================"
echo "| Loading User $USER |"
echo "======================"
source ${CONTEXT}/registry/${USER}.jshrc