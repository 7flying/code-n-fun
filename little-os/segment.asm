global lgdts

lgdts:
        mov eax, [esp + 4]
        lgdt [eax]
        ret
