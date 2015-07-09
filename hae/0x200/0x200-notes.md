# 0x200

## GDB Command notes

* To setup Intel syntax by default specify: ```set dissasembly-flavor intel```
in ```~/.gdbinit```
* ```list```
* ```x```: examine. Syntax: ```x/<how-many><how-to-display><size>  <location> ```.
  Remember that on 0x86 the values are stored **on little-endian**.

  Where the display can be:
  + ```o``` (octal)
  + ```x``` (hexadecimal)
  + ```u``` (unsigned base-10 decimal)
  + ```t``` (binary)
  
  And the size:
  + ```b``` (1 byte)
  + ```h``` (halfword. 2 bytes)
  + ```w``` (word, 4 bytes)
  + ```g``` (giant, 8 bytes)

We can also examine instructions with ```i```, the memory is disassembled
as assembly language instructions.

## Data sizes on 32 vs 64 bits

| ~   | int | unsigned int | short int | long int | long long int | float | char |
|-----|-----|--------------|-----------|----------|---------------|-------|------|
| 32 bits | 4 | 4 | 2 | 4 | 8 | 4 | 1 |
| 64 bits | 4 | 4 | 2 | 8 | 8 | 4 | 1 |

