#include <linux/module.h>	/* Must */
#include <linux/kernel.h>	/* For KERN_INFO */
#include <linux/init.h>		/* For the macros */
#define DRIVER_AUTHOR "7flying"
#define DRIVER_DESC "A sample driver"

static int __init hello_4_init(void)
{
	printk(KERN_INFO "Hello, world 4\n");
	return 0;
}

static void __exit cleanup_hello_4(void)
{
	printk(KERN_INFO "Goodbye, world 4\n");
}

module_init(hello_4_init);
module_exit(cleanup_hello_4);

/* Declare code as GPL */
MODULE_LICENSE("GPL");

/* Module author and description */
MODULE_AUTHOR(DRIVER_AUTHOR);
MODULE_DESCRIPTION(DRIVER_DESC)

/* Say that uses /dev/testdevice */
MODULE_SUPPORTED_DEVICE("testdevice");
