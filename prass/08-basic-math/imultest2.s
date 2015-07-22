    # imultest2.s - example of detecting an imul based overflow
    .section .text
    .globl main
main:
    nop
    movw $680, %ea
    movw $100m %cx
    imulw %cx
    jo over
    movl $1, %eax
    movl $0, %ebx
    int $0x80
over:
    movl $1, %eax
    movl $1, %ebx
    int $0x80
    
