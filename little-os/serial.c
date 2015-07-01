#include "serial.h"
#include "io.h"


/**
 * serial_configure_baud_rate:
 * Sets the speed of the specified serial port to the division of the default
 * speed by the divisor.
 * The default speed is 115200 bits/s.
 * @param com - COM port to configure
 * @param divisor - the divisor to set the speed
 */
static void
serial_configure_baud_rate(unsigned short com, unsigned short divisor)
{
    // Tell the serial port that the higher 8 bits are comming
    outb(SERIAL_LINE_COMMAND_PORT(com), SERIAL_LINE_ENABLE_DLAB);
    // Sent the highest 8, then the lower 8
    outb(SERIAL_DATA_PORT(com), (divisor >> 8) & 0x00FF);
    outb(SERIAL_DATA_PORT(com), divisor & 0x00FF);
}

/**
 * serial_configure_line:
 * Configures the line of the given serial port, it is set with a data length of
 * 8 bits, no parity bits, one stop bit and a break control.
 * @param com - COM port to configure
 */
static void serial_configure_line(unsigned short com)
{
    /* Bit:     | 7 | 6 | 5 4 3 | 2 | 1 0 |
     * Content: | d | b | prty  | s | dl  |
     * Value:   | 0 | 0 | 0 0 0 | 0 | 1 1 | = 0x03
     */
    outb(SERIAL_LINE_COMMAND_PORT(com), 0x03);
}
 
/**
 * serial_configure_buffers:
 * Configures the buffers of the specified serial port, it is set to enable 3
 * bytes of FIFO buffers, 16 bytes large, clears both receiver and transmitter
 * FIFO queues
 * @param com - COM port to configure
 */
static void serial_configure_buffers(unsigned short com)
{
    /* Bit:     | 7 6 | 5  | 4 | 3   | 2   | 1   | 0 |
     * Content: | lvl | bs | r | dma | clt | clr | e |
     * Value:   | 1 1 | 0  | 0 | 0   | 1   | 1   | 1 | = 0xC7
     */
    outb(SERIAL_FIFO_COMMAND_PORT(com), 0xC7);
}

/**
 * serial_configure_modem:
 * Configures the modem port of the given serial port, it is set to RTS (Ready
 * To Transmit) and DTR (Data Terminal Ready).
 * @param com - COM port to configure
 */
static void serial_configure_modem(unsigned short com)
{
    /* Bit:     | 7 | 6 | 5  | 4  | 3   | 2   | 1   | 0   |
     * Content: | r | r | af | lb | ao2 | ao1 | rts | dtr |
     * Value:   | 0 | 0 | 0  | 0  | 0   | 0   | 1   | 1   | = 0x03
     */
    outb(SERIAL_MODEM_COMMAND_PORT(com), 0x03);
}

/**
 * serial_is_transmit_fifo_empty:
 * Checks whether the transmit FIFO queue is empty or not for the specified COM
 * port.
 * @param com - COM port to check
 * @return 0 if FIFO not empty
 *         1 if FIFO empty
 */
static int serial_is_transmit_fifo_empty(unsigned int com)
{
    return inb(SERIAL_LINE_STATUS_PORT(com)) & 0x20;
}

void serial_init(unsigned int com)
{
    serial_configure_baud_rate(com, 1);
    serial_configure_line(com);
    serial_configure_buffers(com);
    serial_configure_modem(com);
}

void serial_write(char *text, unsigned int len, unsigned int com)
{
    while (!serial_is_transmit_fifo_empty(com)) {
        
    }
    unsigned int i;
    for (i=0; i<len; i++)
        outb(SERIAL_DATA_PORT(com), text[i]);
}
