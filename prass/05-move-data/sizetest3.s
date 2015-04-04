	# sizetest3.s - A sample program to view the executable size.
	# It has a 10000 byte buffer declared in the data section
	.section .data
buffer:
	.fill 10000
	.section .text
	.globl _start
_start:
	movl $1, %eax
	movl $0, %ebx
	int $0x80
