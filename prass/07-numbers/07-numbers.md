# Using Numbers

The core numeric data types are: (1) unsigned integers, (2) signed
integers, (3) binary coded decimal, (4) packed binary coded decimal,
(5) single-precision floating-point, (6) double-precision
floating-point and (7) double-extended floating-point.

The SIMD (Single Instruction Multiple Data) extension on some processors
adds other numeric data types: (1) 64-bit packed integers, (2) 128-bit
packed integers, (3) 128-bit packed single-precision floating-point and
(4) 128-bit packed double-precision floating-point.

# Integers

## Standard integer sizes

Integers can have the following sizes: byte, word (16 bits), doubleword
 (32 bits) and quadword (64 bits).

On the IA-32 platform integers that use more than 1 byte are stored in
memory in *little endian* format (lowest-order byte is stored in the
lowest memory location), however when those values are moved to
registers, they are stored in *big-endian* format.

**REMAINDER:** when we are debugging, if we use ```x``` with ```b``` to
examine memory and print the results in binary format, the values will
appear in *little-endian*, whereas when we examine a register the values
will appear in *big-endian*.
 
  ```
  # Memory in hexadecimal
  (gdb) x/x &data
  0x0000000 <data>: 0x00000123
  # Memory in binary, 4 bytes
  (gdb) x/4b &data
  0x0000000 <data>: 0x23 0x01 0x00 0x00
  # Register (hexadecimal)
  (gdb) print/x $eax
  $1 = 0x123
  ```

## Unsigned integers

Unsigned integers can have the following sizes: byte, word (16 bits),
doubleword (32 bits) and quadword (64 bits). They are stored in
consecutive bytes.

## Signed integers

The IA-32 platform uses the two's complement (one's complement and add
one) method to represent signed integers.

See ```inttest.s```.

# Extending integers

When we are converting an unsigned integer value to a large bit size,
we must ensure that all the leading bits are zero. We can use two
instructions:

```GAS
movl $0, %ebx
movw %ax, %ebx
```

Or, use the ```MOVZX``` instruction. This instruction moves a smaller
unsigned integer value (in memory or in a register) to a larger-sized
unsigned integer value (only in a register).

See ```movzxtest.s```.

