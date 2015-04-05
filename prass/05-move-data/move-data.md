# Moving Data

## Defining Data Elements

There are two types of data section: ```.data``` and ```.rodata```, the second
one is read-only.

To define a data element a *label* and a *directive* are required; the
label is used as a reference for the assembler to access the memory location,
the assembler directive defines how many bytes will be reserved for the data
element.

| Directive   | Data type                              |
|-------------|----------------------------------------|
|```.ascii``` | Text string                            |
|```.asciz``` | Null-terminated text string            |
|```.byte```  | Byte value                             |
|```.double```| Double-precision floating-point number |
|```.float``` | Single-precision floating-point number |
|```.int```   | 32-bit integer                         |
|```.long```  | 32-bit integer                         |
|```.octa```  | 16-byte integer                        |
|```.quad```  | 8-byte integer                         |
|```.short``` | 16-bit integer                         |
|```.single```| Single-precision floating-point number |

Some examples:

* Takes 42 bytes of memory, places the string sequentially in the memory bytes,
  and assigns the label ```output``` to the first byte.

  ```GAS
  output:
      .ascii "The processor Vendor ID is 'xxxxxxxxxxx'\n"
  ```

* Assigns the float

  ```GAS
  pi:
      .float 3.14159
  ```

* Places the long integer (4 bytes) 100 in the memory location starting at
  the memory location starting at reference ```sizes```, then places the 4
  bytes of 150 after that and so on. It acts like an array.
  
  To reference 250 if each element takes 4 bytes you get it by accessing the
  memory location at ```sizes+12```.

  ```GAS
  sizes:
      .long 100,150,200,250,300
  ```

Remember that the label of the elements must go after the directive defining
the data section.

```GAS
section .data
output:
    .ascii "The processor Vendor ID is 'xxxxxxxxxxx'\n"
pi:
    .float 3.14159
sizes:
    .long 100,150,200,250,300
```

Each data element is placed in memory in the order defined in the data section,
from lower to higher memory addresses.

## Defining static symbols

Static symbols are defined with the ```.equ``` directive. The directive is used
to set a constant value to a symbol later used on the text section.

The data symbol value cannot be changed once it is defined.

For instance:

```GAS
.equ factor, 3
.equ LINUX_SYS_CALL, 0x80
```

To reference the static data element, we must use a dollar sign (```$```)
before the label name:

```GAS
movl $LINUX_SYS_CALL, %eax
```

## The *bss* section

When you define a data element in the bss section we just declare raw segments
of memory that are reserved for whatever we want.

The GNU assembler uses two directives to declare buffers:

| Directive   | Description |
|-------------|-------------|
|```.comm```  | Declares a *common* memory area for data that is not initialized |
|```.lcomm``` | Declares a *local common* memory area for data that is not initialized. |

The local common memory area cannot be accessed outside of the local assembly
code, this is, data defined as *.lcomm* cannot be used in ```.globl```
directives.

The format for those directives is as follows:

```.directive symbol, length```

Where ```symbol```is a label assigned to the memory area, and ```length``` is
the number of bytes contained in the memory area, for instance:

```GAS
.section .bss
.lcomm buffer, 10000
```

The data in the bss section is not included in the executable program
because they are not initialized with program data; these memory areas are
reserved at runtime.

#### Executable size tests

* ```sizetest1.s```: has no data elements and performs an exit 0.
* ```sizetest2.s```: has a 10000 byte buffer declared in the bss section
  and performs an exit 0.
* ```sizetest3.s```: has a 10000 byte buffer declared in the data section
  and performs an exit 0.

| Program          | Size (bytes) |
|------------------|--------------|
| ```sizetest1.s```| 664          |
| ```sizetest2.s```| 907          |
| ```sizetest3.s```| 10907        |


## Moving Data Elements

Since data elements are located in memory and many processor instructions use
registers, we need to move data around them using the ```MOV``` instruction.

The basic format of ```MOV``` is as follows:

```GAS
movx source, destination
```

Where ```x``` can be:

* ```l```: for a 32-bit long word value.

  For instance, to move the 32-bit ```EAX``` register to the 32-bit ```EBX```
  register:

  ```GAS
  movl %eax, %ebx
  ```
  
* ```w```: for a 16-bit word value.

  ```GAS
  movw %ax, %bx
  ```

* ```b```: for a 8-bit byte value

  ```GAS
  movb %al, %bl
  ```

#### What things can be moved around?

The table shows where a immediate data element, a general-pupose register,
a segment register, a control register, a debug register and a memory location
can be moved to.

|   | Immediate data ele. | General-purpose reg. | Segment reg. | Control reg. | Debug reg. | Memory location |
|---|:---:|:---:|:---:|:---:|:---:|:---:|
| **Immediate data ele.** |  | x  |  |  |  | x |
| **General-purpose reg.** |  | x | x | x | x | x |
| **Segment reg.** |  | x |  |  |  | x |
| **Control reg.** |  | x |  |  |  |  |
| **Debug reg.** |  | x |  |  |  |  |
| **Memory location** |  | x | x |  |  |  |

**Note**: there are special instructions (```MOVS```) to move string values
from one memory location to another.

#### Moving immediate data to registers and memory

Intermediate values must be preceded by a dollar sign.

Some examples of moving immediate data:

The value 0 to the ```EAX``` register:
```GAS
movl $0, %eax
```

The hexadecimal value 80 to the ```EBX```register:
```GAS
movl $0x80, %ebx
```

The value 100 to the ```height``` memory location:
```GAS
movl $100, height
```

#### Moving data between registers

This is the fastest way to move data with the processor, it is recommended to
keep data in the registers to decrease the amount of time spent accessing
memory locations.

The eight general-purpose registers (```EAX```, ```EBX```, ```ECX```, ```EDX```,
```EDI```, ```ESI```, ```EBP``` and ```ESP```) can be moved to any other type
of register.

Special-purpose registers (control debug and segment) can only be moved from or
to a general-purpose register.

Some examples:

Move 32 bits of dat from ```EAX``` to ```ECX```:
```GAS
movl %eax, %ecx
```

Move 16 bits of data from ```AX``` to ```CX```:
```GAS
movw %ax, %cx
```

#### Moving data between memory and registers

###### Moving data values from memory to a register

The simplest case is to use the label used to define the memory location:

```GAS
movl value, %eax
```

This is moving 4 bytes of data starting at the memory location referenced by
the ```value```label to the ```EAX``` register; remember that for 2 bytes
```MOVW``` must be used and for 1 byte ```MOVB```.

###### Moving data values from register to memory

Very simple:

```GAS
movl %eax, value
```

Moves 4 bytes of data stored in the ```EAX``` register and places them in the
memory location referenced by the ```value``` label.

###### Using indexed memory locations

Remember that we can specify more than one value on a directive:

```GAS
values:
	.int 10, 15, 20, 25
```

And this behaves like an array, each element takes 4 bytes and when referencing
data in the array we must specify an index.

This is called **indexed memory mode**, and the memory location is determined
by:

```base_address + offset_address + index * size```

* A base address
* An offset address that is added to the base address
* The size of the data element
* An index to determine which data element to select

The format of the expression is:

```base_address(offset_address, index, size)```

- If any of the values are zero, they can be omitted but commas are still
required as placeholders.

- The ```offset_address``` and the ```index``` must be registers.

Example:
  Given the array referenced by the ```values``` label, reference 20 and move it
  to ```EAX```.

   ```GAS
   values:
       .int 10, 15, 20, 25
   ```

   Solution:
   
   ```GAS
   movl $2, %edi
   movl values(, %edi, 4), %eax
   ```

###### Using indirect addressing with registers

The registers can also hold a memory address, and when the register does, it is
referred to as a **pointer**. Accessing the data stored in the memory location
referenced by a pointer is called **indirect addressing**.
