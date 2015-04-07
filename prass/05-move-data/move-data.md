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

When we use a label, it references the data value contained in the memory
location:

```GAS
movl values, %edi
```

And when you use the dollar (```$```), you get the memory location address of
the data value, and not the data value located at the address.

```GAS
movl $values, %edi
```

Similarly, when we use a register we load a value in the register:

```GAS
movl $10, %edi
```

So when we put parentheses around the register, the instruction moves the value
to the memory location contained in the register, for instance:

```GAS
movl %edx, (%edi)
```

You can increment and decrease the indirect addressing value contained in the
register:

```GAS
movl %edx, 4(%edi)
movl %edx, -4(edi)
```

## Conditional Move instructions

A conditional move instruction is, as the name implies, a ```MOV``` instruction
that takes place under special conditions.

The conditional move instructions have the format:

```GAS
cmovx source, destination
```

Where ```x``` is a one or two letter code defining the condition. The conditions
are based on specific bits of the ```EFLAGS``` register.

These are the important bits for conditional move instructions:

| ```EFLAGS``` bit | Name | Description
|---|---|---|
| ```CF``` | Carry flag | When a mathematical expression creates a carry or borrow |
| ```OF``` | Overflow flag | When a integer value is too large or too small |
| ```PF``` | Parity flag | When the register contains corrupt data from a mathematical operation |
| ```SF``` | Sign flag | Indicates whether the result is positive or negative |
| ```ZF``` | Zero flag | When the result of the mathematical operation is zero |


The conditional move instructions are divided into instructions for signed
and unsigned operations.

* Unsigned operations: involve comparisons that rely on the Carry, Zero and
  Parity flags to determine the difference between operands.

  | Instruction pair | Description | ```EFLAGS``` Condition |
  |---|---|---|
  | ```CMOVA```/```CMOVNBE``` | Above/not below or equal | (CF or ZF) = 0 |
  | ```CMOVAE```/```CMOVNB``` | Above or equal/not below | CF = 0 |
  | ```CMOVNC``` | Not carry | CF = 0 |
  | ```CMOVB```/```CMOVNAE``` | Below/not above or equal | CF = 1 |
  | ```CMOVC``` | Carry | CF = 1 |
  | ```CMOVBE```/```CMOVNA``` | Below or equal/not above | (CF or ZF) = 1 |
  | ```CMOVE```/```CMOVZ``` | Equal/zero | ZF = 1 |
  | ```CMOVNE```/```CMOVNZ``` | Not equal/not zero | ZF = 0
  | ```CMOVP```/```CMOVPE``` | Parity/parity even | PF = 1 |
  | ```CMOVNP```/```CMOVPO``` | Not parity/parity odd | PF = 0 |

* Signed operations: involve comparisons that rely on the Sign and Overflow
  flags to indicate the comparison between operands.

  | Instruction pair | Description | ```EFLAGS``` Condition |
  |---|---|---|
  | ```CMOVGE```/```CMOVNL``` | Greater or equal/ not less | (SF xor OF) = 0 |
  | ```CMOVL```/```CMOVNGE``` | Less/not greater of equal | (SF xor OF) = 1 |
  | ```CMOVLE```/```CMOVNG``` | Less of equal/not greater | ((SF xor OF) or ZF) = 1 |
  | ```CMOVO``` | Overflow | OF = 1 |
  | ```CMOVNO``` | Not overflow | OF = 0 |
  | ```CMOVS``` | Sign (negative) | SF = 1 |
  | ```CMOVNS``` | Not sign (not negative) | SF = 0|


Example:

```GAS
 # Move the value refererenced by value to ECX
 movl value, %ecx
 # Substract EBX from ECX and set EFLAGS
 cmp %ebx, %ecx
 # Move if (CF or ZF) = 0
 cmova %ecx, %ebx
```

## Exchanging Data

To switch the values of two registers you generally need a temporary
intermediate register, however Intel provides instructions to exchange data
without intermediate registers.

These are the instructions:

| Instruction | Description |
|---|---|
| ```XCHG ``` | Exchanges the values of two registers, or a register an a memory location |
| ```BSWAP``` | Reverses the byte order in a 32-bit register |
| ```XADD``` | Exchanges two values and stores the sum in the dest. operand |
| ```CMPXCHG``` | Compares a value with an external value and exchanges it with another |
| ```CMPXCHG8B``` | Compares two 64-bit values and exchanges it with another |


* ```XCHG``` Exchange Register/Memory with Register: exchanges data values
  between two general purpose registers, or between a general purpose register
  and a memory location.

  The registers must have the same size, either 8, 16 or 32-bit sizes are
  supported.

  The format is as follows:

  ```GAS
  xchg operand1, operand2
  ```

  Example (see ```xchg-test.s``` to get the complete code):
    ```GAS
    movl $1, %eax
	movl $2, %ebx
	xchg %eax, %ebx
	```

* ```BSWAP```, Byte Swap : reverses the order of the bytes in a 32-bit
  register; bits 0-7 are swapped with bits 24-31 and bits 8-15 are swapped
  with bits 16-23.
 
  This is used to change from a big-endian value to a little-endian value, and
  vice versa. For instance if we had ```0x12345678``` after a ```BSWAP``` we
  get ```0x78563412```.

  See ```bswap-test.s``` for a complete example.
  
  * ```XADD```, Exchange and Add: exchanges the values between two registers or
  a memory location and a register, then adds the values and stores the result
  in the destination location. The registers can be 8, 16 or 32-bit ones.

  The format is:

  ```GAS
  xadd source, destination
  ```

  Where ```source``` must be a register and ```destination``` a register
  or a memory location.

  See ```xadd-test.s``` for an example.

* ```CMPXCHG```, Compare and Exchange: compares the destination operand with
  the value in the ```EAX```, ```AX``` or ```AL``` registers.
  If the values are equal, the source value is moved to the destination
  register; otherwise, the destination operand value is moved to the ```EAX```,
  ```AX``` or ```AL``` registers.

  Instruction format:

  ```GAS
  cmpxchg source, destination
  ```

  See ```cmpxchg-test.s``` for an example.

* ```CMPXCHG8B```, Compare and Exchange Bytes: (similar to ```CMPXCHG```
  instruction), works with 8-byte values. It has a single operand:
  ```destination```, that references a memory location.
  
  ```GAS
  cmpxchg8b destination
  ```
  
  The 8-byte value of the ```EDX```:```EAX``` pair (```EDX``` high-order)
  is compared with the value in
  the memory location.  If the values are equal, the 64-bit value contained in
  the ```ECX```:```EBX``` pair (```ECX``` high-order) is moved to the
  destination memory location;
  otherwise, the value in the memory location is loaded into
  ```EDX```:```EAX``` pair.

  A little review:
  + Memory location: operand1 of comparison.
  + ```EDX```:```EAX``` pair: operand2 of comparison. When the comparison is not
    equal the value in the memory location is loaded into this pair.
  + ```ECX```:```EBX``` pair: this value is moved to dest. memory location when
    comparison results in equal values.
  
  See ```cmpxchg8b-test.s``` for an example.


## The Stack

The stack is reserved at higher memory addresses and grows downward lower
memory addresses.

The bottom of the stack (highest memory address) has the data elements placed
by the operating system, such as the command-line parameters.

The ```ESP``` register holds the memory address of the top of the stack, if
we modify the ```ESP``` register and lost track of it, strange things can
happen.

#### Push and Pop data

To place new elements into the stack you perform a *push* operation, whereas
you *pop* the topmost element from the stack when you want to retrieve the
last placed element.

```PUSH``` and ```POP``` instructions have the following format:

```GAS
pushx source

popx destination
```

Where ```x``` is:
* ```w```: for a 16-bit word
* ```l```: for a 32-bit long word

The ```source``` value may be:
* 16-bit and 32-bit register value.
* 16-bit segment register.
* 8-, 16- and 32-bit immediate data value.
* 16- and 32-bit memory value.

And ```destination``` location can be:
* 16-bit and 32-bit register location.
* 16-bit segment register location.
* 16- and 32-bit memory location. 


There are a few additional ```PUSH``` and ```POP``` functions:

| Instruction | Description |
|---|---|
| ```PUSHA``` vs```POPA``` | Push or pop all the 16-bit general purpose registers |
| ```PUSHAD``` vs ```POPAD``` | Push or pop all the 32-bit general purpose registers |
| ```PUSHF``` vs ```POPF``` | Push or pop the lower 16 bits of ```EFLAGS``` register |
| ```PUSHFD``` vs ```POPFD``` | Push or pop the ```EFLAGS``` register (32 bits)|


* ```PUSHA``` and ```PUSHAD```: ```PUSHA``` pushes the 16-bit registers in the
  following order:  ```DI```, ```SI```, ```BP```, ```BX```, ```DX```, ```CX```
  and ```AX```.
  ```PUSHAD``` does the same with the 32 bit registers.

* ```POPA``` and ```POPAD```: retrieve the 16-bit and 32-bit general purpose
  registers respectively, in the reverse order in which they where pushed.

* ```PUSHF```, ```POPF```, ```PUSHFD``` and ```POPFD```: the execution of these
  instructions depends on the processor mode of operation.
  
  - Protected mode in ring 0 (privileged mode): all of the non reserved flags
    in the ```EFLAGS``` register can be modified but ```VIP```, ```VIF``` and
    ```VM``` flags. ```VIP``` and ```VIF``` flags are cleared and ```VM``` is
	unmodified.
	
  - Protected mode in rings > 0 (unprivileged mode): same as in ring 0 plus
  ```IOFL``` field is not allowed to be modified.


See ```pushpop.s``` for an example.

