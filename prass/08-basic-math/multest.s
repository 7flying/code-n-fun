    # multest.s - example of the mul instruction
    .section .data
data1:
    .int 315814
data2:
    .int 165432
result:
    .quad 0
    output                      ;
    .asciz "Result: %qd\n"
    .section .text
    .globl main
main:
    nop
    movl data1, %eax
    mull data2
    movl %eax, result
    movl %edx, result+4
    pushl %edx
    pushl %eax
    pushl $output
    call printf
    add $12, %esp
    pushl $0
    call exit
    
