	# jmptest.s - An example of the jmp instruction
	.section .text
	.globl _start
_start:
	nop
	movl $1, %eax
	jmp downthere
	movl $10, %ebx
	int $0x80
downthere:
	movl $20, %ebx
	int $0x80
	
