	# bubble.s - Bubble sort algorithm
	.section .data
values:
	.int 105, 235, 61, 315, 134, 221, 53, 145, 117, 5
	.section .text
	.globl main
main:
	# Initialize compare register (indirect addressing)
	movl $values, %esi
	# Initialize counters
	movl $9, %ecx
	movl $9, %ebx
loop:
	# Move the current value of ESI to EAX to compare
	movl (%esi), %eax
	# Compare first element (EAX) with second element (ESI + 4 bytes)
	cmp %eax, 4(%esi)
	# If the second is greater or equal jump to skip
	jge skip
	# Else exchange the values
	xchg %eax, 4(%esi)
	# Set the second element (previously the first)
	# as the first compared operand
	movl %eax, (%esi)
skip:
	# Increment the pointer to the next element 
	add $4, %esi
	# Decrement the counter
	dec %ebx
	# If not zero jump to loop
	jnz loop
	# Decrement inner counter
	dec %ecx
	# If zero end
	jz end
	# Else update ESI with values
	movl $values, %esi
	# Update EBX with ECX
	movl %ecx, %ebx
	# And start a new loop
	jmp loop
end:
	movl $1, %eax
	movl $0, %ebx
	int $0x80
