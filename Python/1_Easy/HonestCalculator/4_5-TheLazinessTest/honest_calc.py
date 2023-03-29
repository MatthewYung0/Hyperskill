msg_0 = "Enter an equation"
msg_1 = "Do you even know what numbers are? Stay focused!"
msg_2 = "Yes ... an interesting math operation. You've slept through all classes, haven't you?"
msg_3 = "Yeah... division by zero. Smart move..."
msg_4 = "Do you want to store the result? (y / n):"
msg_5 = "Do you want to continue calculations? (y / n):"
msg_6 = " ... lazy"
msg_7 = " ... very lazy"
msg_8 = " ... very, very lazy"
msg_9 = "You are"

operands = ["+", "-", "*", "/"]

is_correct_equation = False

memory = 0


def is_one_digit(number):
    if -10 < number < 10 and number.is_integer():
        return True
    else:
        return False


def check(number_one, number_two, operation):
    msg = ""
    if is_one_digit(number_one) and is_one_digit(number_two):
        msg += msg_6
    if number_one == 1 or number_two == 1 and operation == operands[2]:
        msg += msg_7
    if number_one == 0 or number_two == 0 and (
            operation == operands[2] or operation == operands[0] or operation == operands[1]):
        msg += msg_8
    if msg != "":
        msg = msg_9 + msg
        print(msg)


def m_check(value):
    return memory if value == "M" else value


def get_result(number_one, operand, number_two):
    if operand == operands[0]:
        return number_one + number_two
    elif operand == operands[1]:
        return number_one - number_two
    elif operand == operands[2]:
        return number_one * number_two
    else:
        return number_one / number_two


while not is_correct_equation:
    equation = input(msg_0 + "\n")
    variables = equation.split()
    variables[0] = m_check(variables[0])
    variables[2] = m_check(variables[2])

    try:
        number_one_ = float(variables[0])
        number_two_ = float(variables[2])
        if variables[1] in operands:
            check(number_one_, number_two_, variables[1])
            if variables[1] == operands[3] and number_two_ == 0:
                print(msg_3)
            else:
                result = get_result(number_one_, variables[1], number_two_)
                print(result)

                save_result = input(msg_4 + "\n")
                if save_result == "y":
                    memory = result

                continue_calc = input(msg_5 + "\n")
                if continue_calc != "y":
                    is_correct_equation = True
        else:
            print(msg_2)
    except ValueError:
        print(msg_1)
