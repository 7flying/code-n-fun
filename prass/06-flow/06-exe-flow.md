# Controlling Execution Flow

The instruction pointer ```EIP``` determines which instruction is going to be
executed next. We cannot modify directly the instruction pointer; however, there
are some instructions that can alter it in some specific conditions.

You can use instructions that modify the ```EIP``` register unconditionally
(*unconditional branches*) or based on a condition (*conditional branches*).

## Unconditional branches

There are three types of unconditional branches: *jumps*, *calls* and
*interrupts*.

* *Jumps* are used to go to a specific memory address declared as a label, such
  as ```loop:```, ```end:``` etc; when the processor encounters a jump the
  instruction pointer is changed to the address specified by the jump.

* *Calls* are similar to jump instructions but they remember where they jumped
  from and can return there. Calls are used to implement functions.

* *Interrupts* are used to signal events happening at the hardware level. When
  an interrupt is made the control of the processor is transferred to another
  program.


#### *Jumps*

The instuction format is as follows:

```GAS
jmp location
```

Where ```location``` is a value declared as a label within the program.

Internally the ```jmp``` instruction is translated to three different opcodes
depending on the distance (number of bytes jumped) from the ```jmp```
instruction memory location, and the destination memory location.

The types are divided into:

* Short jumps: when the jump is less than 128 bytes far.

* Far jumps: in segmented memory models, when the jump has a destination
  in another segment.

* Near jumps: when the jump is more than 128 bytes far, and in the same segment.


For instance, see ```jmptest.s```:

```GAS
	# jmptest.s - An example of the jmp instruction
	.section .text
	.globl _start
_start:
	nop
	# set 1 for exit syscall
	movl $1, %eax
	jmp downthere
	# set 10 as a result code
	movl $10, %ebx
	int $0x80
downthere:
	# set 20 as a result code
	movl $20, %ebx
	int $0x80
```

After running it, we can check that it went through the ```downthere``` branch
by checking the result code:

```bash
echo $?
```

And it will give us ```20```, as expected.

If we disassemble the code we can see that ```400087 <downthere>``` is assigned
as the jump destination:

```
jmptest:     file format elf64-x86-64

Disassembly of section .text:

0000000000400078 <_start>:
  400078:	90                   	nop
  400079:	b8 01 00 00 00       	mov    $0x1,%eax
  40007e:	eb 07                	jmp    400087 <downthere>
  400080:	bb 0a 00 00 00       	mov    $0xa,%ebx
  400085:	cd 80                	int    $0x80

0000000000400087 <downthere>:
  400087:	bb 14 00 00 00       	mov    $0x14,%ebx
  40008c:	cd 80                	int    $0x80

```


#### *Calls*

Since calls *remember* where they jumped from and can return there, they are
made using two instructions: first, the ```call``` instruction is used to jump
to the function you defined, and then ```ret```  is used to return from the
function to the memory location from which ```call``` was executed.

The underlying structure behind calls is the stack. This are the steps to make
a call (to a C style function, such as ```exit``` or ```printf```):

1. Push the parameters into the stack in reverse order, this is, the N - 1
   parameter first, the N - 2 parameter second and so on. This would leave
   the stack prepared to pop the parameters in order:

   ```
     The Stack
  ----------------
 |      ~         |
  ---------------- 
 |   Param 3      |
  ----------------
 |   Param 2      |
  ----------------
 |   Param 1      |  <--- ESP
  ----------------
  ```

2. Call the function:

   ```GAS
   call some_function
   ```

3. Clear the stack if parameters where pushed onto it.
   For instance, if you are using the  ```printf``` function with two 32-bit
   values add 8 to the ```ESP``` register.

###### *Local* functions (without parameters)

To make a local function we must remember the return address, do whatever we
need to do as a function, and then return.

The return address is automatically pushed to the stack when a call is made,
leaving the stack as follows:

```
     The Stack
  ----------------
 |      ~         |
  ---------------- 
 |   Param 3      |
  ----------------
 |   Param 2      |
  ----------------
 |   Param 1      |
  ----------------
 | Return address | <---  ESP
  ----------------
```

So, to save the return address we must remember the current ```ESP``` register
location.

The common practice to implement a function is the following one:

1. We are going to save the ```ESP`` register value to the ```EBP``` register;
   but before doing that we are going to save the original ```EBP``` value (to
   avoid corruption if it was needed) to the stack.

   ```GAS
   pushl %ebp
   movl %esp, %ebp
   ```

2. Our function will do whatever it does *without* modifying the ```EBP```
   register.

3. We will return to the memory location that called us, in this point
   the stack has the following contents:

   ```
     The Stack
  ----------------
 |      ~         |
  ---------------- 
 |   Param 3      |
  ----------------
 |   Param 2      |
  ----------------
 |   Param 1      |
  ----------------
 | Return address |
  ----------------
 | Some EBP Value | <--- ESP
  ----------------
```

   So, first of all restore the original ```ESP``` register from the ```EBP```
   register, and pop the original ```EBP``` register value:
   ```GAS
   movl %ebp, %esp
   popl %ebp
   ```

    Finally, return:
    ```GAS
	ret
	```

You can see an example at ```calltest.s```.

###### Function template

Wrapping up: use the following template for your functions.

```GAS
function_name_label:
	pushl %ebp
	movl %esp, %ebp
	# your function stuff
	movl %ebp, %esp
	popl %ebp
	ret
```

#### *Interrupts*

There are two types: *software* and *hardware* interrupts.

* Hardware interrupts: are generated by hardware devices (these devices
  are outside the processor, such as a hard disk). You can see the hardware
  interrupts received by a Linux system on ```/proc/interrupts```.

  Here is a sample file from my computer:

  ```
             CPU0       CPU1       CPU2       CPU3       CPU4       CPU5       CPU6       CPU7       
  0:         16          0          0          0          0          0          0          0  IR-IO-APIC-edge      timer
  1:          2          0          0          0          0          0          0          0  IR-IO-APIC-edge      i8042
  5:          0          0          0          0          0          0          0          0  IR-IO-APIC-edge      parport0
  8:          1          0          0          0          0          0          0          0  IR-IO-APIC-edge      rtc0
  9:          3          0          0          0          0          0          0          0  IR-IO-APIC-fasteoi   acpi
 12:          4          0          0          0          0          0          0          0  IR-IO-APIC-edge      i8042
 16:         33          0          0          0          0          0          0          0  IR-IO-APIC-fasteoi   ehci_hcd:usb1
 23:         37          0          0          0          0          0          0          0  IR-IO-APIC-fasteoi   ehci_hcd:usb2
 40:          0          0          0          0          0          0          0          0  DMAR_MSI-edge      dmar0
 41:          0          0          0          0          0          0          0          0  DMAR_MSI-edge      dmar1
 42:          0          0          0          0          0          0          0          0  IR-PCI-MSI-edge      PCIe PME
 43:          0          0          0          0          0          0          0          0  IR-PCI-MSI-edge      PCIe PME
 44:          0          0          0          0          0          0          0          0  IR-PCI-MSI-edge      PCIe PME
 45:      37323      31095          0      12515     468789     675352          0     307607  IR-PCI-MSI-edge      xhci_hcd
 46:          0          0          0          0          0          0          0          0  IR-PCI-MSI-edge      eth0
 47:      10510       8732          0       2732      26503      62080          0      14170  IR-PCI-MSI-edge      ahci
 48:         12          0          0          0          0          0          0          0  IR-PCI-MSI-edge      mei_me
 49:     328530          0          0          0          0          0          0          0  IR-PCI-MSI-edge      i915
 50:        492          0          0          0          0          0          0          0  IR-PCI-MSI-edge      snd_hda_intel
 51:        757          0          0          0          0          0          0          0  IR-PCI-MSI-edge      snd_hda_intel
NMI:          7          6          8          6          8          6          6          5   Non-maskable interrupts
LOC:      90265     113134     118445     114083     114226     146882      61643      77907   Local timer interrupts
SPU:          0          0          0          0          0          0          0          0   Spurious interrupts
PMI:          7          6          8          6          8          6          6          5   Performance monitoring interrupts
IWI:      11969      12279      11848      16697       1631       3601       3850       2180   IRQ work interrupts
RTR:          7          0          0          0          0          0          0          0   APIC ICR read retries
RES:      30822      16420      15986      13164      26054      20489       7436      12489   Rescheduling interrupts
CAL:        435        539        517        574        549        555        568        539   Function call interrupts
TLB:       4815       6519       5891       6532       3632       5132       4497       4378   TLB shootdowns
TRM:          0          0          0          0          0          0          0          0   Thermal event interrupts
THR:          0          0          0          0          0          0          0          0   Threshold APIC interrupts
MCE:          0          0          0          0          0          0          0          0   Machine check exceptions
MCP:         26         26         26         26         26         26         26         26   Machine check polls
ERR:          0
MIS:          0

  ```

* Software interrupts: are generated by programs and are provided by the
  operating system, they can be generated using the ```INT``` instruction.

  The format is: ```INT code```.
  
  These interrupts provide functions within the operating system and the BIOS.
  You have to specify a code with the ```INT``` instruction;
  Microsoft DOS used ```0x21``` as the software interrupt, ```0x10``` and
  ```0x16``` are for the BIOS interrupt call, and Linux uses ```0x80```.


When a program is called via interrupt, the caller is put on hold and the called
program takes the control. The instruction pointer is transferred to the called
program, which continues the execution until it completes, then the control
returns to the caller program using an interrupt return instruction (```IRET```
 or ```IRETD```).

