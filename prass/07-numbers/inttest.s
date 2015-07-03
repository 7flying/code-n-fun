    # inttest - Test signed integers
    .section .data
data:
    .int -45
    .section .text
    .globl _start
_start:
    nop
    movl $-345, %eax
    movw $0xffb1, %dx
    movl data, %ebx
    movl $1, %eax
    int $0x80
 
