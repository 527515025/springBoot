#ident  "%W%"
#!/bin/sh -x

#######################
###for redhat 7 only
#######################

1. close whole firewall
issue:
	all port like 80/20 and so on cannot be accessed
check:
	systemctl list-unit-files|grep enabled | grep firewall (if not null, firewall is open)
   
fix:
	systemctl stop firewalld.service
	systemctl disable firewalld.service
	
rollabck:
	systemctl enable firewalld.service
	systemctl start firewalld.service




