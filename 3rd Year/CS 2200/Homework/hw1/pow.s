main:	la	$sp, stack		! load address of stack into $sp
		la	$at, pow		! load address of pow into $at
		addi $a0, $zero, 2 	! $a0 = 2, the base for pow
		addi $a1, $zero, 4	! $a1 = 4, the power for pow
		jalr $at, $ra		! jump to pow, set $ra to return addr
		halt				! when we return, just halt
pow:	halt				! change me to your pow impelementation
stack:	.byte 0 			! the stack begins here (for example, that is)

