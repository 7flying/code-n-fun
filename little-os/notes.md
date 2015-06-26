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
