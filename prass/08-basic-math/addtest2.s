    # addtest2.s - second add example, using negative numbers
    .section .data
data:
    .int -40
    .section .text
    .flobl _start
_start:
    nop
    movl $-10, %eax
    movl $-200, %ebx
    movb $80, %ecx
    addl data, %eax
    addl %ecx, %eax
    addl %ebx, %eax
    addl %eax, data
    addl $210, data
    movl $1, %eax
    movl $0, %ebx
    int $0x80
    
