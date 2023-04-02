def switch_player(name):
    if name == "John":
        return "Jack"
    else:
        return "John"


def get_num_of_pencils():
    print("How many pencils would you like to use:")
    while True:
        try:
            num_of_penc = int(input())
            if num_of_penc <= 0:
                print("The number of pencils should be positive")
            else:
                return num_of_penc
        except ValueError:
            print("The number of pencils should be numeric")


def is_valid_name(name):
    while True:
        if name != "John" and name != "Jack":
            print("Choose between 'John' and 'Jack'\n")
            name = input()
        else:
            return name


def is_valid_number():
    while True:
        try:
            deduction = int(input())
            if deduction > 3 or deduction <= 0:
                print("Possible values: '1', '2' or '3'\n")
            else:
                return int(deduction)
        except ValueError:
            print("Possible values: '1', '2' or '3'\n")


def main():
    num_of_penc = get_num_of_pencils()
    name = is_valid_name(input("Who will be the first (John, Jack):\n"))
    print("|" * num_of_penc)
    print("%s's turn!" % name)
    while num_of_penc != 0:
        deduction = is_valid_number()
        if is_valid_name(name):
            if deduction > num_of_penc:
                print("Too many pencils were taken")
            else:
                name = switch_player(name)
                num_of_penc -= deduction
                if num_of_penc == 0:
                    print("%s won!" % name)
                else:
                    print("|" * num_of_penc)
                    print("%s's turn!" % name)
        else:
            name = input("Choose between 'John' and 'Jack'\n")


if __name__ == "__main__":
    main()
