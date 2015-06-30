#ifndef _SEGMENT_H_
#define _SEGMENT_H_

struct GDT {
    unsigned int address;
    unsigned short size;
} __attribute__((packed));

/**
 * lgdts:
 * Loads the GDT into the processor.
 * @param gdt - GDT struct with address and size to be loaded in the processor
 */
void lgdts(struct GDT);

#endif /* _SEGMENT_H_ */
