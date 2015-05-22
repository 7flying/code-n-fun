from ctypes import cdll

lib = cdll.LoadLibrary("target/release//libembed-b3e3801bdc712847.so")

lib.process()

print "Done!"
