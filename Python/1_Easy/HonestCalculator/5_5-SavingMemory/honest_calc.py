msgs = [
    "Enter an equation",
    "Do you even know what numbers are? Stay focused!",
    "Yes ... an interesting math operation. You've slept through all classes, haven't you?",
    "Yeah... division by zero. Smart move...",
    "Do you want to store the result? (y / n):",
    "Do you want to continue calculations? (y / n):",
    " ... lazy",
    " ... very lazy",
    " ... very, very lazy",
    "You are",
    "Are you sure? It is only one digit! (y / n)",
    "Don't be silly! It's just one number! Add to the memory? (y / n)",
    "Last chance! Do you really want to embarrass yourself? (y / n)",
]

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
        msg += msgs[6]
    if number_one == 1 or number_two == 1 and operation == operands[2]:
        msg += msgs[7]
    if number_one == 0 or number_two == 0 and (
            operation == operands[2] or operation == operands[0] or operation == operands[1]):
        msg += msgs[8]
    if msg != "":
        msg = msgs[9] + msg
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
    equation = input(msgs[0] + "\n")
    variables = equation.split()
    variables[0] = m_check(variables[0])
    variables[2] = m_check(variables[2])

    try:
        number_one_ = float(variables[0])
        number_two_ = float(variables[2])
        if variables[1] in operands:
            check(number_one_, number_two_, variables[1])
            if variables[1] == operands[3] and number_two_ == 0:
                print(msgs[3])
            else:
                result = get_result(number_one_, variables[1], number_two_)
                print(result)
                save_result = input(msgs[4] + "\n")
                if save_result == "y":
                    if is_one_digit(result):
                        msg_index = 10
                        while (msg_index < 13):
                            print(msgs[msg_index])
                            save_result = input()
                            if msg_index >= 12:
                                memory = result
                            if save_result == "y":
                                msg_index += 1
                            else:
                                break
                    else:
                        memory = result
                continue_calc = input(msgs[5] + "\n")
                if continue_calc != "y":
                    is_correct_equation = True
        else:
            print(msgs[2])
    except ValueError:
        print(msgs[1])
