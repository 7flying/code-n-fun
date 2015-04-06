	# cmovatest.s - Example of CMOVA instruction
	.section .data
out_one:
	.asciz "EBX has: %d\n"
out_two:
	.asciz "ECX has: %d\n"
	.section .text
	.globl main
main:
	nop
	movl $7, %ecx
	movl $6, %ebx
	pushl %ebx
	pushl $out_one
	call printf
	pushl %ecx
	pushl $out_two
	call printf
	cmp %ebx, %ecx
	pushl %ebx
	pushl $out_one
	call printf
	pushl %ecx
	pushl $out_two
	call printf
	cmova %ecx, %ebx
	pushl %ebx
	pushl $out_one
	call printf
	pushl %ecx
	pushl $out_two
	call printf
	addl $8, %esp
	pushl $0
	call exit
	
