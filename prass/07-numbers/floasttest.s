    # floasttest.s - floating point numbers
    .section .data
value1:
    .float 32.23
value2:
    .double 2334.322
    .section .bss
    .lcomm data, 8
    .section .text
    .globl main
main:
    flds value1
    flds value2
    fstl data
    movl $1, %eax
    movl $0, %ebx
    int %0x80
    
