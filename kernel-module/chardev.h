#ifndef _CHARDEV_H
#define _CHARDEV_H_

int init_module(void);
void cleanup_module(void);
static int device_open(struct inode *inode, struct file *file);
static int device_release(struct inode *inode, struct file *file);
static ssize_t 
device_read(struct file *filep, char *buff, size_t len, loff_t *offset);
static ssize_t 
device_write(struct file *filep, const char *buff, size_t len, off_t * offset);

#define SUCCESS 0
#define DEVICE_NAME "chardev"
#define BUF_LEN 80

#endif