	# xchg-test.s - Example of the XCHGS instruction
	.section .text
	.globl main
main:
	nop
	movl $1, %eax
	movl $2, %ebx
	# EBX will have 1 and EAX 2
	xchg %eax, %ebx
	addl $8, %esp
	pushl $0
	call exit
