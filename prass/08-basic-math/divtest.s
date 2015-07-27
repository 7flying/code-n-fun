    # divtest.s - the div instruction
    .section .data
divid:
    .quad 500
divisor:
    .int 50
quotient:
    .int 0
remainder:
    .int 0
output:
    .asciz "The quotient is %d and the remainder is %d\n"
    .section .text
    .globl main
main:
    movl divid, %eax
    movl divid+4, %edx
    divl divisor
    movl %eax, quotient
    movl %edx, remainder
    pushl remainder
    pushl quotient
    pushl $output
    call printf
    add $12, %esp
    pushl $0
    call exit
    
