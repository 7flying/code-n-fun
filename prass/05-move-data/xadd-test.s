	# xadd-test.s - An example of the XADD instruction
	.section .text
	.globl main
main:
	movl $1, %eax
	movl $2, %ebx
	xadd %eax, %ebx
	# EAX now has 2 and EBX 3
	movl $1, %eax
	int $0x80
