#include <sys/ptrace.h> // Defines ptrace and __ptrace_request constants
#include <sys/reg.h>	// For system calls
#include <sys/wait.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <errno.h>
#include <string.h>

int do_child(int, char **);
int do_trace(pid_t);

int main(int argc, char **argv)
{
	if (argc < 2) {
		fprintf(stderr, "Usage: %s prog args\n", argv[0]);
		exit(1);
	}

	pid_t child = fork();
	// One process executes the program to be traced and the other traces it
	if (child == 0)
		return do_child(argc - 1, argv + 1);
	else
		return do_trace(child);
}

int do_child(int argc, char **argv)
{
	// Prepare a NULL terminated argument array for execvp
	char *args [argc + 1];
	memcpy(args, argv, argc * sizeof(char*));
	args[argc] = NULL;
	// Start the tracing process and execute the program
	ptrace(PTRACE_TRACEME);
	kill(getpid(), SIGSTOP);
	return execvp(args[0], args);
}

int wait_for_syscall(pid_t);

int do_trace(pid_t child)
{
	int status, syscall, retval;
	waitpid(child, &status, 0);
	ptrace(PTRACE_SETOPTIONS, child, 0, PTRACE_0_TRACESYSGOOD);
	while (1) {
		// If it returns non-zero the child has exited, we end the loop
		if (wait_for_syscall(child) != 0)
			break;
		syscall = ptrace(PTRACE_PEEKUSER, child, sizeof(long) * ORIG_EAX);
		fprintf(stderr, "syscall(%d) = ", syscall);
		if (wait_for_syscall(child) != 0)
			break;
		retval = ptrace(PTRACE_PEEKUSER, child, sizeof(long) * EAX);
		fprintf(stderr, "%d\n", retval);
	}
	return 0;
}

int wait_for_syscall(pid_t child)
{
	int status;
	while (1) {
		ptrace(PTRACE_SYSCALL, child, 0, 0);
		waitpid(child, &status, 0);
		if (WIFSTOPPED(status) && WSTOPSIG(status) & 0x80)
			return 0;
		if (WIFEXITED(status))
			return 1;
	}
}
