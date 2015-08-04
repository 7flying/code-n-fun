    # saltest.s - example of the sal instruction
    .section .data
value1:
    .int 25
    .section .text
    .globl main
main:
    movl $10, %ebx
    sall %ebx
    movb $2, %cl
    sall %cl, %ebx
    sall $2, %ebx
    sall value1
    sall $2, value1
    movl $1, %eax
    movl $0, %ebx
    int $0x80
    
