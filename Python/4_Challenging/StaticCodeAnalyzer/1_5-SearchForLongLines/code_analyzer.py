MAX_CHAR = 79


def print_error(line_number):
    print("Line {}: S001 Too long".format(line_number))


def main():
    try:
        with open(input(), 'r') as file:
            for line_number, line in enumerate(file, start=1):
                if len(line) > MAX_CHAR:
                    print_error(line_number)

    except FileNotFoundError:
        print(f"File not found!")
    except Exception as e:
        print(f"An error occurred: {e}")


if __name__ == '__main__':
    main()
