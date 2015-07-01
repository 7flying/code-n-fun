#ifndef _SERIAL_H_
#define _SERIAL_H_

/* I/O serial ports */

/* Serial ports have all their ports in the same order.  */
/* Location of port COM1 */
#define SERIAL_COM1_BASE 0x3F8 
#define SERIAL_DATA_PORT(base) (base)
#define SERIAL_FIFO_COMMAND_PORT(base) (base + 2)
#define SERIAL_LINE_COMMAND_PORT(base) (base + 3)
#define SERIAL_MODEM_COMMAND_PORT(base) (base + 4)
#define SERIAL_LINE_STATUS_PORT(base) (base + 5)


/**
 * SERIAL_LINE_ENABLE_DLAB:
 * Flag to tell that the highest 8 bits are comming to the data port, then the
 * lowers 8 bits will follow.
 */
#define SERIAL_LINE_ENABLE_DLAB 0x80

/**
 * serial_init:
 * Initialises the given COM port with the default configuration
 * @param com - COM to initialise
 */
void serial_init(unsigned int com);

/**
 * serial_write:
 * Writes the contents of buf of length len to the serial.
 * @param text - string to write
 * @param len - write up to len.
 * @param com - com port to write to
 */
void serial_write(char *text, unsigned int len, unsigned int com);
#endif /* _SERIAL_H_ */
