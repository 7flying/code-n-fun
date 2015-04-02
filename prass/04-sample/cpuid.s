	# cpuid.s Sample program to extract the processor Vendor ID
	.section .data
	# Declare a text string with the staring memory location denoted by
	# the label output.
	# The xxx are used as placeholders in the memory area reserved for the
	# data variable.
	# Remember %d, %s etc in C (somehow similar but not the same)
output:
	.ascii "The processor Vendor ID is 'xxxxxxxxxxxx'\n"
	# Declare the instruction code section and the starting label of the
	# application
	.section .text
	.globl _start
_start:
	movl $0, %eax
	cpuid
	movl $output, %edi
	movl %ebx, 28(%edi)
	movl %edx, 32(%edi)
	movl %ecx, 36(%edi)
	movl $4, %eax
	movl $1, %ebx
	movl $output, %ecx
	movl $42, %edx
	int $0x80
	movl $1, %eax
	movl $0, %ebx
	int $0x80
	
