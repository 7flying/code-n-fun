    # mmxtest.s - Example of MMX data types
    .section  .data
values1:
    .int 1, -1
values2:
    .byte 0x10, 0x05, 0xff, 0x32, 0x47, 0xe4, 0x00, 0x01
    .section .text
    .globl _start
_start:
    movq values, %mm0
    movq values2, %mm1
    movl $1, %eax
    movl $0, %ebz
    int $0x80
    
