#include <linux/kernel.h>		
#include <linux/module.h>
#include <linux/fs.h>
#include <asm/uaccess.h>	/* put_user macro */
#include "chardev.h"

static int Major;			/* Major number of the device driver */
static int Device_Open = 0;
static char msg[BUF_LEN];
static char *msg_ptr;

static struct file_operations fops = {
	.read = device_read,
	.write = device_write,
	.open = device_open,
	.release = device_release
};

/* Function called when the module is loaded */
int init_module(void)
{
	Major = register_chrdev(0, DEVICE_NAME, &fops);
	if (Major < 0) {
		printk(KERN_ALERT "Registering char device failed with %d\n", Major);
		return Major;
	}

	printk(KERN_INFO "I was assigned major number %d. To talk to\n", Major);
	printk(KERN_INFO "the driver, create a new dev file with\n");
	printk(KERN_INFO "'mknod /dev/%s c %d 0'.\n", DEVICE_NAME, Major);
	printk(KERN_INFO "Try various minor numbers. Try to cat and echo to\n");
	printk(KERN_INFO "the device file.\n");
	printk(KERN_INFO "Remove device file and module when done.\n");

	return SUCCESS;
}

/* Function called when the module is unloaded */
void cleanup_module(void)
{
	unregister_chrdev(Major, DEVICE_NAME);
}

/* Function called when a process tries to open the device file,
 * like  */
static int device_open(struct inode *inode, struct file *file)
{
	static int counter = 0;
	if (Device_Open)
		return -EBUSY;
	Device_Open++;
	sprintf(msg, "I already told you %d times Hello world!,", counter++);
	msg_ptr = msg;
	try_module_get(THIS_MODULE);

	return SUCCESS;
}

/* Function called when a process closes the device file. */
static int device_release(struct inode *inode, struct file *file)
{
	Device_Open--; /* Decrease flag to be ready for the next caller */
	module_put(THIS_MODULE); /* Decrement the usage count. */

	return 0;
}

/* Called when a process, which already opened the dev file,
 * attemps to read from it */
static ssize_t 
device_read(struct file *filep, char *buff, size_t len, loff_t *offset)
{
	int bytes_read = 0;
	/* If we're at the end of the message return EOF -> 0 */
	if (*msg_ptr == 0)
		return 0;

	/* Put data in buffer */
	while (len && *msg_ptr) {
		/* NOTE: the buffer is in the user data segment. We are at kernel,
		 * so, "*" asigment won't work. We have to copy data from the kernel
		 * data segment to the user data segment, hence the use of put_user */
		put_user(*(msg_ptr++), buff++);

		len--;
		bytes_read++;
	}

	/* Style: most read functions return the number of bytes put into buffer */
	return bytes_read;
}

/* Function called when a process writes to dev dile: echo "hi" > /dev/hello */
static ssize_t 
device_write(struct file *filep, const char *buff, size_t len, off_t * offset)
{
	printk(KERN_ALERT "Sorry, this operation isn't supported.\n");
	return -EINVAL;
}
