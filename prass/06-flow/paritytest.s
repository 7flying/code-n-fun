	# paritytest.s - Example of the usage of the parity flag
	.section .text
	.globl _start
_start:
	movl $1, %eax
	movl $4, %ebx
	cmp %eax, %ebx
	jp overthere
	int $0x80
overthere:
	movl $10, %ebx
	int $0x80
