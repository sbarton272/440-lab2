#!/usr/bin/python

from subprocess import call

# Compiles code
call(['javac', '-cp', '.', 'client/PartyClient.java'])
call(['javac', '-cp', '.', 'server/Server.java'])

print "To run server: java server/Server"
print "To run client: java client/PartyClient <hostname>"
