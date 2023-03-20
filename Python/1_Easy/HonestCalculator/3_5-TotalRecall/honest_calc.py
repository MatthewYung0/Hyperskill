def multiply(number_one, number_two):
    return number_one * number_two


def divide(number_one, number_two):
    return number_one / number_two


def add(number_one, number_two):
    return number_one + number_two


def subtract(number_one, number_two):
    return number_one - number_two


def m_check(value):
    if value == "M":
        return memory
    else:
        return value


def get_result(number_one, operand, number_two):
    if operand == operands[0]:
        return add(number_one, number_two)
    elif operand == operands[1]:
        return subtract(number_one, number_two)
    elif operand == operands[2]:
        return multiply(number_one, number_two)
    else:
        return divide(number_one, number_two)


message_0 = "Enter an equation"
message_1 = "Do you even know what numbers are? Stay focused!"
message_2 = "Yes ... an interesting math operation. You've slept through all classes, haven't you?"
message_3 = "Yeah... division by zero. Smart move..."
message_4 = "Do you want to store the result? (y / n):"
message_5 = "Do you want to continue calculations? (y / n):"

is_correct_equation = False

operands = ["+", "-", "*", "/"]

memory = 0

while not is_correct_equation:

    equation = input(message_0 + "\n")
    variables = equation.split()
    variables[0] = m_check(variables[0])
    variables[2] = m_check(variables[2])

    try:
        number_one_ = float(variables[0])
        number_two_ = float(variables[2])
        if variables[1] in operands:
            if variables[1] == operands[3] and number_two_ == 0:
                print(message_3)
            else:
                result = get_result(number_one_, variables[1], number_two_)
                print(result)

                save_result = input(message_4 + "\n")
                if save_result == "y":
                    memory = result

                continue_calc = input(message_5 + "\n")
                if continue_calc != "y":
                    is_correct_equation = True

        else:
            print(message_2)
    except ValueError:
        print(message_1)
