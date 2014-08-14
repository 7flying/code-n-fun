#include <linux/module.h>		/* Must */
#include <linux/moduleparam.h> 	/* For the param related macros */
#include <linux/kernel.h>		/* For KERN_INFO */
#include <linux/init.h>			/* For the macros */

MODULE_LICENSE("GPL");
MODULE_AUTHOR("7flying");

static short int myshort = 1;
static int myint = 1;
static long mylong = 9999;
static char* mystring = "bla";
static int myintarray[2] = { -1 , 0};
static int arr_argc = 0;

/* module_param(variable_name, type, permissions)
 * permissions: bits for exposing parameters in sysfs (if non-zero) */

module_param(myshort, short, S_IRUSR | S_IWUSR | S_IRGRP | S_IWGRP);
MODULE_PARM_DESC(myshort, "A short integer");
module_param(myint, int, S_IRUSR | S_IWUSR | S_IRGRP | S_IROTH);
MODULE_PARM_DESC(myint, "An integer");
module_param(mylong, long, S_IRUSR);
MODULE_PARM_DESC(mylong, "A long integer");
module_param(mystring, charp, 0000);
MODULE_PARM_DESC(mystring, "A character string");

/* module_param_array(name, type, num, persmissions)
 * num: pointer to the variable that will store the number of
 * 		elements of the array
 */
module_param_array(myintarray, int, &arr_argc, 0000);
MODULE_PARM_DESC(myintarray, "An array of integers");

static int __init hello_5_init(void)
{
	int i;
	printk(KERN_INFO "Hello, world 5\n==============\n");
	printk(KERN_INFO "myshort is a short integer: %hd\n", myshort);
	printk(KERN_INFO "myint is an integer: %d\n", myint);
	printk(KERN_INFO "mylong is a long integer: %ld\n", mylong);
	printk(KERN_INFO "mysting is a string: %s\n", mystring);
	for(i = 0; i < (sizeof myintarray / sizeof (int)); i++)
	{
		printk(KERN_INFO "myintarray[%d] = %d\n", i, myintarray[i]);
	}
	printk(KERN_INFO "got %d arguments for myintarray.\n", arr_argc);
	return 0;
}

static void __exit hello_5_exit(void)
{
	printk(KERN_INFO "Goodbye, world 5\n");
}

module_init(hello_5_init);
module_exit(hello_5_exit);
