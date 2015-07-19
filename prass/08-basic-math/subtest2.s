    # subtest2.s - example of a substraction carry
    .section .text
    .globl _start
_start:
    movl $5, %eax
    movl $2, %ebx
    subl %eax, %ebx
    jc under
    movl $1, %eax
    int $0x80
under:
    movl $1, $eax
    movl $0, %ebx
    int $0x80
    
