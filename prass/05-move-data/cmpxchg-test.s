	# cmpxchg-test.s - An example of the CMPXCHG instruction
	.section .data
data:
	.int 10
	.section .text
	.globl main
main:
	nop
	movl $10, %eax
	movl $5, %ebx
	# Since EAX and data have the same value EBX in moved to data
	cmpxchg %ebx, data
	movl $1, %eax
	int $0x80
	
