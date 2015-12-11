//from gaylers classes
public enum TokenType
{
	EOSLine, 
	IdTok, 
	constantTok, 
	assign_opt, 
	le_opt,
	ge_opt,
	lt_opt,  
	gt_opt, 
	eq_opt,
	ne_opt, 
	add_opt, 
	mul_opt,
	sub_opt,
	div_opt,
	FEATURE_RESERVED,
	IS_RESERVED,
	DO_RESERVED,
	END_RESERVED,
	IF_RESERVED,
	THEN_RESERVED,
	ELSE_RESERVED,
	PRINT_RESERVED,
	FROM_RESERVED,
	UNTIL_RESERVED,
	LOOP_RESERVED,
	LEFT_OPERAND,
	RIGHT_OPERAND
}