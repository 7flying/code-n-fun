    # subtest1.s - example of the subb instruction
    .section .data
data:
    int 50
    .section .text
    .globl _start
_start:
    movl $0, %eax
    movl $0, %ebx
    movl $0, %ecx
    movb $20, $al
    subb $10, $al
    movsx $al, %eax
    movw $100, %cx
    subw %cx, %bx
    movsx %bx, %ebx
    movl $100, %edx
    subl %eax, %edz
    subl data, %eax
    subl $eax, data
    movl $1, %eax
    movl $0, %ebx
    int $0x80
