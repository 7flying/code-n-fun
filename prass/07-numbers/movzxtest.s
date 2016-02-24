    # movzxtest.s - Test the strange movzx instruction
    .section .text
    .globl main
_main:
    nop
    movl $279, %ecx
    movzz %cl, %ebx
    movl $1, %eax
    int $0x80
    
