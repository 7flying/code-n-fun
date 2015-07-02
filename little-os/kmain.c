#include "fb.h"
#include "colours.h"
#include "serial.h"
#include "segment.h"


int kmain(int arg0, int arg1)
{
   
    char test[] ="heeX";
    int ret = fb_write(test, 4);
    ret += (arg0 + arg1);
    /*
    fb_write_cell(30, 'H', FB_GREEN, FB_DARK_GREY);
    fb_write_cell(32, 'E', FB_GREEN, FB_DARK_GREY);
    fb_write_cell(34, 'L', FB_GREEN, FB_DARK_GREY);
    fb_write_cell(36, 'L', FB_GREEN, FB_DARK_GREY);
    fb_write_cell(38, 'O', FB_GREEN, FB_DARK_GREY);
    */
    serial_init(1);
    serial_write(test, 4, 1);
    return ret;
}
