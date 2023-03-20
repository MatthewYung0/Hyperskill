message_zero = "Enter an equation"
message_one = "Do you even know what numbers are? Stay focused!"
message_two = "Yes ... an interesting math operation. You've slept through all classes, haven't you?"
message_three = "Yeah... division by zero. Smart move..."

is_correct_equation = False

operands = ["+", "-", "*", "/"]


def multiply(number_one, number_two):
    return number_one * number_two


def divide(number_one, number_two):
    return number_one / number_two


def add(number_one, number_two):
    return number_one + number_two


def subtract(number_one, number_two):
    return number_one - number_two


while not is_correct_equation:
    equation = input(message_zero + "\n")
    variables = equation.split()
    try:
        number_one_ = float(variables[0])
        number_two_ = float(variables[2])
        if variables[1] in operands:
            if variables[1] == operands[3] and number_two_ == 0:
                print(message_three)
            else:
                if variables[1] == operands[0]:
                    print(add(number_one_, number_two_))
                elif variables[1] == operands[1]:
                    print(subtract(number_one_, number_two_))
                elif variables[1] == operands[2]:
                    print(multiply(number_one_, number_two_))
                elif variables[1] == operands[3]:
                    print(divide(number_one_, number_two_))
                is_correct_equation = True
        else:
            print(message_two)
    except ValueError:
        print(message_one)
