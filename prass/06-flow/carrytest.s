   	# carrytest.s - Example of the usage of the carry flag
	.section .text
	.globl _start
_start:
	movl $1, %eax
	movl $0xffffffff, %ebx
	addl $1, %ebx
	js overflow
	movl $1, %ebx
	int $0x80
overflow:
	movl $0, %ebx
	int $0x80
