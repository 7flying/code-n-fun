	# cmpxchg8b-test.s - Example of the CMPXCHG8B instruction
	.section .data
data:
	.byte 0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77, 0x88
	.section .text
	.globl main
main:
	nop
	movl $0x88776655, %edx
	movl $0x44332211, %eax
	movl $0x22222222, %ecx
	movl $0x11111111, %ebx
	# The comparison results in equal so ECX:EBX is moved to data
	cmpxchg8b data
	movl $0, %ebx
	movl $1, %eax
	int $0x80

