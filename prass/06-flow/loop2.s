	# loop2.s - Another xample of loop instruction
	.section .data
output:
	.asciz "We are now on it: %d\n"
	.section .text
	.globl main
main:
	movl $100, %ecx
	movl $0, %ebx
	movl $0, %eax
loop1:
	addl $1, %ebx
	loop loop1
	pushl %ebx
	pushl $output
	call printf
	add $8, %esp
	movl $1, %eax
	movl $0, %ebx
	int $0x80
