def main():
    num_of_penc = int(input("How many pencils would you like to use:\n"))
    name = input("Who will be the first (John, Jack):\n")

    for pencil in range(0, num_of_penc):
        print("|", end="")
    print("\n%s is going first!" % name)


if __name__ == "__main__":
    main()
