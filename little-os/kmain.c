#include "io.h"
#include "colours.h"


void fb_write_cell(unsigned int i, char c, unsigned char fg, unsigned char bg)
{
    char *fb = (char *) 0x000B8000;
    fb[i] = c;
    fb[i + 1] = ((fg & 0x0F) << 4) | (bg & 0x0F);
}

void fb_move_cursor(unsigned short pos)
{
    outb(FB_COMMAND_PORT, FB_HIGH_BYTE_COMMAND);
    outb(FB_DATA_PORT, ((pos >> 8) & 0x00FF));
    outb(FB_COMMAND_PORT, FB_LOW_BYTE_COMMAND);
    outb(FB_DATA_PORT, pos & 0x00FF);
}

int write(char *buf, unsigned int len)
{
    unsigned int i;
    for (i = 0; i<len; i++) {
        fb_write_cell(i, buf[i], FB_GREEN, FB_DARK_GREY);
    }
    fb_move_cursor(len);
    return 0;
}


int kmain(int arg0, int arg1)
{
    char test[] ="heeX";
    int ret = write(test, 4);
    ret += (arg0 + arg1);
    /*
    fb_write_cell(30, 'H', FB_GREEN, FB_DARK_GREY);
    fb_write_cell(32, 'E', FB_GREEN, FB_DARK_GREY);
    fb_write_cell(34, 'L', FB_GREEN, FB_DARK_GREY);
    fb_write_cell(36, 'L', FB_GREEN, FB_DARK_GREY);
    fb_write_cell(38, 'O', FB_GREEN, FB_DARK_GREY);
    */
    return ret;
}
