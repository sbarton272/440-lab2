#!/usr/bin/python

from subprocess import call

# Compiles code
call(['javac', '-cp', './src', 'src/client/PartyClient.java'])
call(['javac', '-cp', './src', 'src/server/Server.java'])

print "To run server: java server/Server"
print "To run client: java client/PartyClient <hostname>"
