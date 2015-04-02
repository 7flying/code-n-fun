# A Sample Assembly Language Program

## Simple program

#### Using ```as``` and ```ld```

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
	as -o cpuid.o cpuid.s
	```

	Generates the object code file ```cpuid.o```.
	
* Link the object code file into the executable file:

	```
	ld -o cpuid cpuid.o
	```

	Generates the executable file ```cpuid```.

Launch the program: ```./cpuid```

It should give the output: ```The processor Vendor ID is 'GenuineIntel'``` if you
are using an Intel processor.

#### Using ```gcc```

The main difference is that ```gcc``` looks for the ```main``` label in order
to determine where the program starts, so we must provide that.

Program:

```
	# cpuid-gcc.s Sample program to extract the processor Vendor made for
	# gcc
	.section .data
output:
	.ascii "The processor Vendor ID is 'xxxxxxxxxxxx'\n"
	.section .text
	# gcc looks for the main label to determine the beginning of the program
	.globl main
main:
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

```
gcc -o cpuid-gcc cpuid-gcc.s
```

## Debugging

To debug an assembly language program the code must be reassembled with the
```-gstabs``` parameter. This parameter adds extra info to help ```gdb```
debug the code, this extra info makes the executable bigger, so make sure to
reassemble the program again after finishing the debug phase.

To generate the executable do:

```
as -gstabs -o program.o program.s
ld -o program program.o
```
