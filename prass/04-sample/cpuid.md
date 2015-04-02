# A Sample Assembly Language Program

The program is as follows:

```
	# cpuid.s Sample program to extract the processor Vendor ID
	.section .data
output:
	.ascii "The processor Vendor ID is 'xxxxxxxxxxxx'\n"
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
```

To generate the executable:

* Assemble the assembly language source into the object code:

	```
	as -o cpuid.o cpuid.sx
	```

	Generates the object code file ```cpuid.o```.
	
* Link the object code file into the executable file:

	```
	ld -o cpuid cpuid.o
	```

	Generates the executable file ```cpuid```.

Launch the program: ```./cpuid```

It should give the output: ```The processor Vendor ID is 'GenuineIntel'``` if you
are usign an Intel processor.

