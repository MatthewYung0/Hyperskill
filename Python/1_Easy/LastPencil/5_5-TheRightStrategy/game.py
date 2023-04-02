def ask_number_of_pencils():
    n = input("How many pencils would you like to use:")
    try:
        n = int(n)
    except ValueError:
        print("The number of pencils should be numeric")
        return ask_number_of_pencils()
    else:
        if n <= 0:
            print("The number of pencils should be positive")
            return ask_number_of_pencils()
        return n


def calc_number_of_pencils(n):
    bars = ""
    for _ in range(n):
        bars += "|"
    return bars


def ask_username():
    user = input(f"Who will be the first (John, Jack):")
    if user not in ["John", "Jack"]:
        print("Choose between 'John' and 'Jack'")
        return ask_username()
    return user


def remove_pencils(pencils):
    n = input()
    try:
        n = int(n)
        if n > 3 or n <= 0:
            print("Possible values: '1', '2' or '3'")
            return remove_pencils(pencils)
        if n > pencils:
            print("too many pencils")
            return remove_pencils(pencils)
    except ValueError:
        print("Possible values: '1', '2' or '3'")
        return remove_pencils(pencils)
    else:
        return n


def losing_positions(pencils):
    return set(range(1, pencils + 1, 4))


def remove_pencils_bot(pencils):
    n = 1
    if 1 < pencils <= 4:
        return pencils - 1
    elif pencils == 1:
        return n
    else:
        while pencils - n not in losing_positions(pencils) and 1 <= n < 3:
            n += 1
        return n


def intro():
    number_of_pencils = ask_number_of_pencils()
    first_user = ask_username()
    print(calc_number_of_pencils(number_of_pencils))
    return first_user, number_of_pencils


def main():
    first_user, number_of_pencils = intro()
    turn = first_user
    while number_of_pencils > 0:
        print(f"{turn}'s turn:")
        if turn == "John":
            number_of_pencils -= remove_pencils(number_of_pencils)
        else:
            remove_number = remove_pencils_bot(number_of_pencils)
            number_of_pencils -= remove_number
            print(remove_number)
        print(calc_number_of_pencils(number_of_pencils))
        turn = "Jack" if turn == "John" else "John"
        if number_of_pencils <= 0:
            print(f"{turn} won!")
            break


main()
