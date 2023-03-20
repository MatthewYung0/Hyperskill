message_zero = "Enter an equation"
message_one = "Do you even know what numbers are? Stay focused!"
message_two = "Yes ... an interesting math operation. You've slept through all classes, haven't you?"

is_correct_equation = False

while not is_correct_equation:
    equation = input(message_zero + "\n")
    variables = equation.split()
    try:
        float(variables[0])
        float(variables[2])
        if variables[1] == '+' or \
                variables[1] == '-' or \
                variables[1] == '*' or \
                variables[1] == '/':
            is_correct_equation = True
        else:
            print(message_two)
    except ValueError:
        print(message_one)
