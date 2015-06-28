# Notes on Little-OS development

## 1. First Configuration

We are going to load a little program (```loader.asm```) that moves
```0xCAFEBABE``` to ```eax``` register.
We are targeting a 32 bit platform. In NASM you can target 32 bits using:

```
nasm -f elf32 loader.nasm
```

Since we are loading the "kernel" via GRUB, and GRUB itself takes 1MB
(```0x00100000```) the kernel needs to be loaded at a memory address larger or
equal to that. So we need to tell the linker about this (see ```link.ld```), and
link the program using:

```
ld -T link.ld -melf_i386 loader.o kernel.elf
```

To load the program by a machine we need a bootable image, we are using an ISO.
The ISO is created using El-Torito standard (see
[OS Dev wiki](http://wiki.osdev.org/El-Torito)), we need the following structure
to generate the ISO:

```
iso/
└── boot
    ├── grub
    │   ├── menu.lst
    │   └── stage2_eltorito
    └── kernel.elf
```

where ```menu.lst``` is a GRUB configuration file. To generate the ISO image
run:

```
genisoimage -R -b boot/grub/stage2_eltorito -no-emul-boot -boot-load-size 4 \
-A os -input-charset utf8 -quiet -boot-info-table -o os.iso iso
```

And launch Boschs:

```
bochs -f boochsrc.txt -q
```

## 2. Setup C support

C needs a stack, to setup a stack point the ```esp``` register to a free memory
area (aligned on 4 bytes due to performance [TODO check why]). We are going to
reserve 4 MB of memory on the ```bss``` section, and then point the ```esp```
register to the end of this section (see ```kernel_stack``` on ```loader.asm```).

Remember that to call a C function from assembly we need to declare it as
```extern function_name```, push the arguments onto the stack starting with the
rightmost argument and finally call the function.

To compile C code tell the compiler that no extra libraries should be used,
stack protections should be removed and overall, that the presence of external
libs should not be assumed
(see [gcc link options](https://gcc.gnu.org/onlinedocs/gcc/Link-Options.html)).

```
-m32 -nostdlib -nostdinc -fno-builtin -fno-stack-protector -nostartfiles
    -nodefaultlibs
```


