	# zero.s - Example of the Zero flag
	.section .text
	.globl _start
_start:
	movl $1, %eax
	movl $1, %ebx
	cmp %eax, %ebx
	jz downthere
	int $0x80
downthere:
	movl $11, %ebx
	int $0x80
