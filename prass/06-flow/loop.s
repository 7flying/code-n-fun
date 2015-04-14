	# loop.s - Example of loop instruction
	.section .data
output:
	.asciz "The value is: %d\n"
	.section .text
	.globl main
main:
	movl $100, %ecx
	movl $0, %eax
loop1:
	addl %ecx, %eax
	loop loop1
	pushl %eax
	pushl $output
	call printf
	add $8, %esp
	movl $1, %eax
	movl $0, %ebx
	int $0x80
