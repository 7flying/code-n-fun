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

  ```
  output:
      .ascii "The processor Vendor ID is 'xxxxxxxxxxx'\n"
  ```

* Assigns the float

  ```
  pi:
      .float 3.14159
  ```

* Places the long integer (4 bytes) 100 in the memory location starting at
  the memory location starting at reference ```sizes```, then places the 4
  bytes of 150 after that and so on. It acts like an array.
  
  To reference 250 if each element takes 4 bytes you get it by accessing the
  memory location at ```sizes+12```.

  ```
  sizes:
      .long 100,150,200,250,300
  ```

Remember that the label of the elements must go after the directive defining
the data section.

```
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

```
.equ factor, 3
.equ LINUX_SYS_CALL, 0x80
```

To reference the static data element, we must use a dollar sign (```$```)
before the label name:

```
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
code, this is, they cannot be used in ```.globl``` directives.

The format for those directives is as follows:

```.directive symbol, length```

Where ```symbol```is a label assigned to the memory area, and ```length``` is
the number of bytes contained in the memory area, for instance:

```
.section .bss
.lcomm buffer, 10000
```

The data in the bss section is not included in the executable program
because they are not initialized with program data; these memory areas are
reserved at runtime.

#### Executable size tests

* ```sizetest1.s```: has no data elements and performs an exit 0.
* ```sizetest2.s```: has a 10000 byte buffer declared in the bss section.
* ```sizetest3.s```: has a 10000 byte buffer declared in the data section.

| Program | Size (bytes) |
|-|-|
| ```sizetest1.s```| 664 |
| ```sizetest2.s```| 907 |
| ```sizetest3.s```| 10907 |
