#ifndef _SEGMENT_H_
#define _SEGMENT_H_


struct GDT {
    /* Size of GDT */
    unsigned short limit;
    /* Starting address for the GDT*/
    unsigned int base;
} __attribute__((packed));

/**
 * load_gdt:
 * Loads the GDT into the processor.
 * @param gdt - GDT struct with address and size to be loaded in the processor
 */
void load_gdt(struct GDT);

#endif /* _SEGMENT_H_ */
