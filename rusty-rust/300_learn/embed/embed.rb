require 'ffi'

module Hello
  extend FFI::Library
  ffi_lib 'target/release/libembed-b3e3801bdc712847.so'
  attach_function :process, [], :void
end

Hello.process

puts "done!"
