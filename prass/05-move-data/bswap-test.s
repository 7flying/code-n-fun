	# bswap-test.s - An example of the BSWAP instruction
	.section .text
	.globl main
main:
	nop
	movl $0x12345678, %ebx
	bswap %ebx
	movl $1, %eax
	int $0x80
	
