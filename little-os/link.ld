ENTRY(loader)

SECTIONS {
    /* Locate code at 1MB */
    . = 0x00100000;
    /* Align at 4KB all the .text sections */
    .text ALIGN (0x1000) :
    {
        *(.text)
    }
    /* Align at 4KB all the read-only sections */
    .rodata ALIGN (0x1000) :
    {
        *(.rodata*)
    }
    /* Align at 4KB all the data sections */
    .data ALIGN (0x1000) :
    {
        *(.data)
    }
    /* Align at 4KB the COMMON sections and .bss sections */
    .bss ALIGN (0x1000) :
    {
        *(COMMON)
        *(.bss)
    }
}
