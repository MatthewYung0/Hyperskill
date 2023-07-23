import random

operands = ["+", "-", "*"]


def check_input(user_input):
    return True if user_input.lstrip("-").isdigit() else False


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
    score = 0
    for i in range(5):
        num_one, operand, num_two = generate_equation()
        print(f"{num_one} {operand} {num_two}")
        real_answer = get_answer(num_one, operand, num_two)
        user_answer = input()

        while not check_input(user_answer):
            print("Incorrect format.")
            user_answer = input()

        if real_answer == int(user_answer):
            print("Right!")
            score += 1
        else:
            print("Wrong!")

    print(f"Your mark is {score}/5.")


if __name__ == "__main__":
    main()
