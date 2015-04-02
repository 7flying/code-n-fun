	# cpuid.s Sample program to extract the processor Vendor ID
	.section .data
	# Declare a text string with the staring memory location denoted by
	# the label output.
	# The xxx are used as placeholders in the memory area reserved for the
	# data variable.
	# Remember %d, %s etc in C (somehow similar but NOT the same)
output:
	.ascii "The processor Vendor ID is 'xxxxxxxxxxxx'\n"
	# Declare the instruction code section and the starting label of the
	# application
	.section .text
	.globl _start
_start:
	# Load the EAX register with zero and run the CPUID instruction
	movl $0, %eax
	cpuid
	# The CPUID instruction places the response in the EABX, ECX and EDX
	# registers.
	# First move the ouput variable declared before to EDI and then move
	# the response of CPUID to EDI.
	movl $output, %edi
	movl %ebx, 28(%edi)
	movl %edx, 32(%edi)
	movl %ecx, 36(%edi)
	# Use the Linux system call 4 to access the console display.
	# int $0x80 generates a software interrupt, int generates interrupts.
	# The function that is performed is determined by the value of EAX.
	
	# Set which syscall in EAX: 4 to eax (system call value: __NR_write)
	movl $4, %eax
	# Select the file descriptor in EBX: 1 (standard output)
	movl $1, %ebx
	# Set where the string starts in ECX: we are writing 'output'
	movl $output, %ecx
	# Set the length of the string in EDX (just count the length of output)
	movl $42, %edx
	# Generate a software interrupt
	int $0x80
	# Exit the program in a clean way using the exit system call
	# __NT_EXIT, with a value of 1 and pass 0 as the status (everything ok)
	movl $1, %eax
	# Pass 0 as the status : normal program exit
	movl $0, %ebx
	int $0x80
	
