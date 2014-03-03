Author: Kaila Thornton

My program follows the package structure Shrideep outlined in his slides.
Registry and MessagingNodes are the core programs

Calling "make" will compile the files

Registry must be started before Messaging Nodes



java cs455.overlay.node.Registry portnum will run the Registry
java cs455.overlay.node.MessagingNode registry-host registry-port will start a Messaging Node


Valid commands for the Registry are:
list-messaging nodes
list-weights
setup-overlay <# of connections>
send-overlay-link-weights

Valid commands for the MessagingNodes are:
exit-overlay

Unfortunately, dijkstra's and therefore the sending of rounds has not been implemented yet.
