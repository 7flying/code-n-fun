#include "serial.h"
#include "io.h"

void serial_configure_baud_rate(unsigned short com, unsigned short divisor)
{
    // Tell the serial port that the higher 8 bits are comming
    outb(SERIAL_LINE_COMMAND_PORT(com), SERIAL_LINE_ENABLE_DLAB);
    // Sent the highest 8, then the lower 8
    outb(SERIAL_DATA_PORT(com), (divisor >> 8) & 0x00FF);
    outb(SERIAL_DATA_PORT(com), divisor & 0x00FF);
}

void serial_configure_line(unsigned short com)
{
    /* Bit:     | 7 | 6 | 5 4 3 | 2 | 1 0 |
     * Content: | d | b | prty  | s | dl  |
     * Value:   | 0 | 0 | 0 0 0 | 0 | 1 1 | = 0x03
     */
    outb(SERIAL_LINE_COMMAND_PORT(com), 0x03);
}

void serial_configure_buffers(unsigned short com)
{
    /* Bit:     | 7 6 | 5  | 4 | 3   | 2   | 1   | 0 |
     * Content: | lvl | bs | r | dma | clt | clr | e |
     * Value:   | 1 1 | 0  | 0 | 0   | 1   | 1   | 1 | = 0xC7
     */
    outb(SERIAL_FIFO_COMMAND_PORT(com), 0xC7);
}

void serial_configure_modem(unsigned short com)
{
    /* Bit:     | 7 | 6 | 5  | 4  | 3   | 2   | 1   | 0   |
     * Content: | r | r | af | lb | ao2 | ao1 | rts | dtr |
     * Value:   | 0 | 0 | 0  | 0  | 0   | 0   | 1   | 1   | = 0x03
     */
    outb(SERIAL_MODEM_COMMAND_PORT(com), 0x03);
}

int serial_is_transmit_fifo_empty(unsigned int com)
{
    return inb(SERIAL_LINE_STATUS_PORT(com)) & 0x20;
}

void serial_write(char *text, unsigned int len)
{
    serial_configure_baud_rate(1, 1);
    serial_configure_line(1);
    serial_configure_buffers(1);
    serial_configure_modem(1);
    if (serial_is_transmit_fifo_empty(1)) {
        unsigned int i;
        for (i=0; i<len; i++)
            outb(SERIAL_DATA_PORT(1), text[i]);
    }
}
