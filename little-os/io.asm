;; Function to send a byte to an I/O port
;; stack: [esp + 8] the data byte
;;        [esp + 4] I/O port
;;        [esp] return address
global outb

outb:
        mov al, [esp + 8]
        mov dx, [esp + 4]
        out dx, al
        ret
