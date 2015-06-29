#ifndef _FB_H_
#define _FB_H_

/* I/O ports */
#define FB_COMMAND_PORT 0x3D4
#define FB_DATA_PORT 0x3D5

/* I/O port commands */
#define FB_HIGH_BYTE_COMMAND 14
#define FB_LOW_BYTE_COMMAND 15


/**
 * fb_write_cell:
 * Writes a character with the specified foreground and background in the
 * framebuffer at position i in the framebuffer.
 * @param i - location in the framebuffer
 * @param c - char to print
 * @param fg - foreground colour
 * @param bg - background colour
 */
void fb_write_cell(unsigned int i, char c, unsigned char fg, unsigned char bg);

/**
 * fb_move_cursor:
 * Moves the cursor of the framebuffer to the given position.
 * @param pos - position to set the cursor
 */
void fb_move_cursor(unsigned short pos);

/**
 * fb_write:
 * Writes the contents of buf of length len to the screen.
 * @param buf - string to write
 * @param len - write up to len.
 */
int fb_write(char *buf, unsigned int len);

#endif /* _FB_H_ */
