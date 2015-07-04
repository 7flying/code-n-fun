    # movsxtest - Example of movsx instruction
    .section .text
    .globl _start
_start:
    movw $-79, %cx
    movl $0, %ebx
    movw %cx, %bx
    movsz %cz, %eax
    movl $1, %eax
    movl $0, %ebx
    int $0x80
    
