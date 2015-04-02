	# cpuid-clin.s Sample program to extract the processor Vendor using
	# C libray functions in assembly
	.section .data
output:
	.asciz "The processor Vendor ID is '%s'\n"
	.section .bss
	.lcomm buffer, 12
	.section .text
	# gcc looks for the main label to determine the beginning of the program
	.globl _start
_start:
	movl $0, %eax
	cpuid
	movl $buffer, %edi
	movl %ebx, (%edi)
	movl %edx, 4(%edi)
	movl %ecx, 8(%edi)
	pushl $buffer
	pushl $output
	call printf
	addl $8, %esp
	pushl $0
	call exit
