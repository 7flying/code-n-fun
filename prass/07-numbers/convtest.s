    # convtest.s - example of data conversions
    .section .data
value1:
    .float 1.25, 124.45, 200.0, -312.5
value2:
    .int 1, -453, 0, -25
    .section .bss
data:
    .lcomm data, 16
    .section .text
    .globl _start
_start:
    cvtps2dq value1, %xmm0
    cvttps2q value1, %xmm1
    cvtdq2ps value2, %xmm2
    movdqu %xmm0, data
    movl $1, %eax
    movl $0, %ebx
    int $0x80
    
