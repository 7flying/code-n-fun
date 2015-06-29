;; outb - function to send a byte to an I/O port
;; stack: [esp + 8] the data byte
;;        [esp + 4] I/O port
;;        [esp] return address
global outb

;; inb - function to read bytes from the given I/O port
;; stack: [esp + 4] the address of the I/0 port
;;        [esp] return address
global inb

outb:
        mov al, [esp + 8]
        mov dx, [esp + 4]
        out dx, al
        ret

inb:
        mov dx, [esp + 4]
        in al, dx
        ret
