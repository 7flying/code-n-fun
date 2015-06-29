#ifndef INCLUDE_IO_H
#define INCLUDE_IO_H


/**
 * outb:
 * Sends the data to the specified I/O port. Uses io.asm
 * @param port - I/O port to send data to
 * @param data - data to send to the port
 */
void outb(unsigned short, unsigned char);


/**
 * inb:
 * Reads a byte from the given I/O port.
 * @param port - port to read from
 * @return read byte
 */
unsigned char inb(unsigned short);

#endif /* INCLUDE_IO_H */
