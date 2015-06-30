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
 * serial_configure_baud_rate:
 * Sets the speed of the specified serial port to the division of the default
 * speed by the divisor.
 * The default speed is 115200 bits/s.
 * @param com - COM port to configure
 * @param divisor - the divisor to set the speed
 */
void serial_configure_baud_rate(unsigned short com, unsigned short divisor);

/**
 * serial_configure_line:
 * Configures the line of the given serial port, it is set with a data length of
 * 8 bits, no parity bits, one stop bit and a break control.
 * @param com - COM port to configure
 */
void serial_configure_line(unsigned short com);

/**
 * serial_configure_buffers:
 * Configures the buffers of the specified serial port, it is set to enable 3
 * bytes of FIFO buffers, 16 bytes large, clears both receiver and transmitter
 * FIFO queues
 * @param com - COM port to configure
 */
void serial_configure_buffers(unsigned short com);

/**
 * serial_configure_modem:
 * Configures the modem port of the given serial port, it is set to RTS (Ready
 * To Transmit) and DTR (Data Terminal Ready).
 * @param com - COM port to configure
 */
void serial_configure_modem(unsigned short com);

/**
 * serial_is_transmit_fifo_empty:
 * Checks whether the transmit FIFO queue is empty or not for the specified COM
 * port.
 * @param com - COM port to check
 * @return 0 if FIFO not empty
 *         1 if FIFO empty
 */
int serial_is_transmit_fifo_empty(unsigned int com);


/**
 * serial_write:
 * Writes the contents of buf of length len to the serial.
 * @param text - string to write
 * @param len - write up to len.
 */
void serial_write(char *text, unsigned int len);
#endif /* _SERIAL_H_ */
