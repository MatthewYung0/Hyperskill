import os
import random

operands = ["+", "-", "*"]


def check_input(user_input):
    return True if user_input.lstrip("-").isdigit() else False


def check_answer(user_answer, real_answer):
    while not check_input(user_answer):
        print("Incorrect format.")
        user_answer = input()

    if real_answer == str(user_answer):
        print("Right!")
        return True
    else:
        print("Wrong!")
        return False


def simple_operations():
    num_one, operand, num_two = generate_equation()
    print(f"{num_one} {operand} {num_two}")

    real_answer = get_simple_answer(num_one, operand, num_two)
    user_answer = input()
    return real_answer, user_answer


def prompt():
    print("Which level do you want? Enter a number:")
    print("1 - simple operations with numbers 2-9")
    print("2 - integral squares of 11-29")
    return str(input())


def get_level():
    user_input = int(prompt())
    while user_input != 1 and user_input != 2:
        print("Incorrect format.")
        user_input = prompt()
    return user_input


def generate_equation():
    num_one = random.randrange(2, 10)
    num_two = random.randrange(2, 10)
    operand = random.choice(operands)
    return num_one, operand, num_two


def get_simple_answer(num_one, operand, num_two):
    operation = {
        operands[0]: lambda x, y: x + y,
        operands[1]: lambda x, y: x - y,
        operands[2]: lambda x, y: x * y,
    }
    calculation_func = operation.get(operand)
    return calculation_func(int(num_one), int(num_two))


def get_integral_answer(number):
    return number * number


def save_file(name, level, score):
    if os.path.exists("results.txt"):
        os.remove("results.txt")
    else:
        file = open("results.txt", "w")
        if level == 1:
            level_text = "(level 1 simple operations with numbers 2-9)"
        else:
            level_text = "(level 2 integral squares of 11-29)"
        file.write(f"{name}: {score}/5 in {level_text}")
        file.close()


def main():
    score = 0
    level = int(get_level())
    if level == 1:
        for i in range(5):
            real_answer, user_answer = simple_operations()
            if check_answer(user_answer, real_answer):
                score += 1
    else:
        for i in range(5):
            number = random.randrange(11, 30)
            real_answer = get_integral_answer(number)
            print(number)
            user_answer = input()
            if check_answer(user_answer, real_answer):
                score += 1
    print(f"Your mark is {score}/5. Would you like to save the result? Enter yes or no.")
    save_answer = input().lower()
    if save_answer in ["yes", "y"]:
        print("What is your name?")
        name = input()
        save_file(name, level, score)
        print("The results are saved in \"results.txt\"")


if __name__ == "__main__":
    main()
