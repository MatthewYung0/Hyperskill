def main():
    user_input = input()
    numbers = user_input.split(" ")
    number_one = int(numbers[0])
    number_two = int(numbers[2])

    if "+" in user_input:
        print(number_one + number_two)
    elif "-" in user_input:
        print(number_one - number_two)
    elif "*" in user_input:
        print(number_one * number_two)
    else:
        print(number_one / number_two)


if __name__ == "__main__":
    main()
