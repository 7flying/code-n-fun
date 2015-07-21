    # imultest.s - example of the imul instruction
    .section .data
value1:
    .int 10
value2:
    .int -35
value3:
    .int 400
    .section .text
    .globl main
main:
    nop
    movl value1, %ebx
    movl value2, %ecx
    imull %ebx, %ecx
    movl value3,%edx
    imull $2, %edx, %eax
    movl $1, %eax
    movl $0, %ebx
    int $0x80
