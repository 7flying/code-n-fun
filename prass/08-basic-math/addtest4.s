    # addtest4.s - detecting an overflow condition
    .section .data
data:
output:
    .asciz "The result is %d\n"
    .section .text
    .globl _start
_start:
    nop
    movl $-1236589475, %ebx
    movl $-1232252565, %eax
    addl %ex, %ebx
    jo over
    pushl %ebx
    pushl $output
    call printf
    add $8, %esp
    pushl $0
    call exit
over:
    pushl $0
    pushl $output
    call printf
    add $8, %esp
    pushl $0
    call exit
    
