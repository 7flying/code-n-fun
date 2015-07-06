global load_gdt

load_gdt:
        mov eax, [esp + 4]
        lgdt [eax]
load_segment_reg:
        mov ds, 0x10
        mov ss, 0x10
        mov es, 0x10
        mov gs, 0x10
        mov fs, 0x10
; To load cs do a far jump
        jmp 0x08:flush_cs
flush_cs:
        ret