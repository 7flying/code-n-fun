	# betterloop.s - Loop the proper way
	.section .data
output:
	.asciz "The value is %d\n"
	.section .text
	.globl main
main:
	movl $0, %ecx
	movl $0, %eax
	jecxz done
loop1:
	addl %ecx, %eax
	loop loop1
done:
	pushl %eax
	pushl $output
	call printf
	movl $1, %eax
	movl $0, %ebx
	int $0x80
