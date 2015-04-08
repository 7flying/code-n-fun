# A Sample Assembly Language Program

## Simple program

#### Using ```as``` and ```ld```

The program is as follows:

```GAS
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

	```bash
	as -o cpuid.o cpuid.s
	```

	Generates the object code file ```cpuid.o```.
	
* Link the object code file into the executable file:

	```bash
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

```GAS
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

```bash
gcc -o cpuid-gcc cpuid-gcc.s
```

## Debugging

To debug an assembly language program the code must be reassembled with the
```-gstabs``` parameter. This parameter adds extra info to help ```gdb```
debug the code, this extra info makes the executable bigger, so make sure to
reassemble the program again after finishing the debug phase.

To generate the executable do:

```bash
as -gstabs -o program.o program.s
ld -o program program.o
```

## Using C Library Functions in Assembly

The program is as follows:

```GAS
	# cpuid-clib.s Sample program to extract the processor Vendor using
	# C libray functions in assembly
	.section .data
output:
	.asciz "The processor Vendor ID is '%s'\n"
	.section .bss
	.lcomm buffer, 12
	.section .text
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

```

#### Linking with C library functions

You can either perform *static* of *dynamic* linking.

* Static linking: links function object code directly into the application
executable program file. This wastes memory when there are multiple instances
of the program running at the same time since they all have their own copy of
the same functions.

* Dynamic linking: it uses libraries that enable to reference the functions in
the applications, but not link the function codes in the executable program.
Dynamic libs are called at the program's runtime by the operating system and
can be shared among multiple programs.


On Linux systems the standard C dynamic library is located in the file
```libc.so.x```, where ```x``` represents the lib's version.

This file is automatically linked to C programs when using ```gcc```.

To link the ```libx.so``` there is no need to specify the whole path
```/lib/libx.so``` because the linker assumes that library would be somewhere
in ```/lib/*```.

1. Use the option ```-l``` followed by the required lib name, in this
   case ```c```.
2. You must specify the program that will load the dynamic lib at runtime. For
   Linux systems it its ```ld-linux.so.2```, generally in the ```/lib```
   directory. Use the ```-dynamic-linker``` parameter.

So, this it gives us:

```bash
ld -dynamic-linker /lib/ld-linux.so.2 -o cpuid-clib -lc cpuid-clib.o
```

If using ```gcc``` make the appropriate label change from ```_start``` to
```main```, and run ```gcc``` like before:

```bash
gcc -o cpuid-clib cpuid-clib.s
```

###### Note on x86_64 systems

If we are in a 64 system some instructions such as ```pushl``` would give us
problems, so specify that you would like a 32 bit executable with
```-m elf_i386``` option but first assemble the program with the
```--32``` flag.

```bash
as --32 -o cpuid-clib.o cpuid-clib.s
ld -dynamic-linker /lib/ld-linux.so.2 -m elf_i386 -o cpuid-clib -lc cpuid-clib.o 
```

You can avoid those two steps with ```gcc``` using ```-m32``` option:

```bash
gcc -m32 -o cpuid-clib cpuid-clib.s
```
just remember to change ```_start``` to ```main```.
