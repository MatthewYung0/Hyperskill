import random

operands = ["+", "-", "*"]


def generate_equation():
    num_one = random.randrange(2, 10)
    num_two = random.randrange(2, 10)
    operand = random.choice(operands)
    return num_one, operand, num_two


def get_answer(num_one, operand, num_two):
    operation = {
        operands[0]: lambda x, y: x + y,
        operands[1]: lambda x, y: x - y,
        operands[2]: lambda x, y: x * y,
    }
    calculation_func = operation.get(operand)
    return calculation_func(int(num_one), int(num_two))


def main():
    num_one, operand, num_two = generate_equation()
    print(f"{num_one} {operand} {num_two}")
    real_answer = get_answer(num_one, operand, num_two)

    user_answer = int(input())
    if real_answer == user_answer:
        print("Right!")
    else:
        print("Wrong!")


if __name__ == "__main__":
    main()
