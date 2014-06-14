#!/usr/bin/env python

import sys
from omniORB import CORBA
import CosNaming
import EchoApp

# Inisiasi ORB
orb = CORBA.ORB_init(sys.argv, CORBA.ORB_ID)

# Mencari reference ke namingContext
obj = orb.resolve_initial_references("NameService")
rootContext = obj._narrow(CosNaming.NamingContext)

if rootContext is None:
	print "failed to narrow the root naming context"
	sys.exit(1)

name = [CosNaming.NameComponent("Echo", "")]

try:
	obj = rootContext.resolve(name);
except CosNaming.NamingContext.NotFound, ex:
	print "Name notfound"
	sys.exit(1)
	
# Narrow object
eo = obj._narrow(EchoApp.Echo)

if eo is None:
	print "Object reference bukan EchoApp.Echo"
	sys.exit(1)

message = "Hello from Python"
result  = eo.doEcho(message)

print "I said '%s'. The object said '%s'." % (message,result)