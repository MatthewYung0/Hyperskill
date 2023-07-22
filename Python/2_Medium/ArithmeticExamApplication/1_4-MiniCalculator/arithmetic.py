def main():
    num_one, operand, num_two = input().split(" ")

    operation = {
        "+": lambda x, y: x + y,
        "-": lambda x, y: x - y,
        "*": lambda x, y: x * y,
    }

    calculation_func = operation.get(operand)
    result = calculation_func(int(num_one), int(num_two))
    print(result)


if __name__ == "__main__":
    main()
