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

# loading user
echo "======================"
echo "| Loading User $USER |"
echo "======================"
source ./registry/$(USER).jshrc