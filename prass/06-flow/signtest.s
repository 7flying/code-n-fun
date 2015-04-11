	# signtest.s - Example of the usage of the sign flag
	.section .text
	.globl _start
_start:
	movl $1, %eax
	movl $0, %edi
	subl $1,  %edi
	jc overthere
	movl $1, %ebx
	int $0x80
overthere:
	movl $0, %ebx
	int $0x80
