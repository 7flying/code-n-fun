	# calltest.s - Example of using CALL and RET instructions
	.section .data
output:
	.asciz "This is section: %d\n"
	.globl main
main:
	pushl $1
	pushl $output
	call printf
	add $8, %esp
	call downthere
	pushl $3
	pushl $output
	call printf
	add $8, %esp
	pushl $0
	call exit
downthere:
	pushl %ebp
	movl %esp, %ebp
	pushl $2
	pushl $output
	call printf
	add $8, %esp
	movl %ebp, %esp
	popl %ebp
	ret
