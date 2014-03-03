#!/bin/bash
#bash script to start up terminals and ssh to computers.
#These computers’ names are read from a file named comps.

HOSTNAME=homer.cs.colostate.edu
PORT=42598

for i in `cat ./comps`
do
 echo 'sshing to '${i}
 gnome-terminal -x bash -c "ssh -t ${i} 'cd CS455/CS455_Assignment1/src; java cs455/overlay/node/MessagingNode $HOSTNAME $PORT; bash'"
done

exit 0
