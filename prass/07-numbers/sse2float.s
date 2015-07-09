    # sse2float.s - moving SSE2 FP data types
    .section .data
value1:
    .double 12.34, 1234.234
value2:
    .double -2234.345, 12345.4
    .section .bss
    .lcomm data, 16
    .section .text
    .globl _start
_start:
    movupd value1, %xmm0
    movupd value2, %xmm1
    movupd %xmm0, %xmm2
    movupd %xmm0, data
    movl $1, %eax
    movl $0, %ebx
    int $0x80
    
