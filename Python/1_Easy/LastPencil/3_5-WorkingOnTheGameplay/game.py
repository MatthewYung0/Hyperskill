def switch_player(name):
    if name == "John":
        return "Jack"
    else:
        return "John"

def main():
    num_of_penc = int(input("How many pencils would you like to use:\n"))
    name = input("Who will be the first (John, Jack):\n")
    while num_of_penc != 0:
        print("|" * num_of_penc)
        print("%s's turn:" % name)
        deduction = int(input())
        name = switch_player(name)
        num_of_penc -= deduction


if __name__ == "__main__":
    main()
