    # ssefloat.s - moving SSE FP data types
    .section .data
value1:
    .float 32.32, 22.444, -3456.5, 0.55555
value2:
    .float -333.444, 11111.4, 1.0004, 0.222227
    .section .bss
    .lcomm data, 16
    .section .text
    .globl _start
_start:
    movups value1, %xmm0
    movups value2, %xmm1
    movups %xmm0, %xmm2
    movups %xmm0, data
    movl $1, %eax
    movl $0, %ebx
    int $0x80
    
