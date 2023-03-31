list_of_friends = {}

num_of_friends = int(input("Enter the number of friends joining (including you):\n"))


def add_friends():
    print("\nEnter the name of every friend (including you), each on a new line:")
    for friend in range(num_of_friends):
        list_of_friends[input()] = 0


def split_bill(bill):
    for friend in list_of_friends:
        list_of_friends[friend] = round((bill / num_of_friends), 2)


if num_of_friends > 0:
    add_friends()
    split_bill(int(input("\nEnter the total bill value:\n")))
    print(list_of_friends)
else:
    print("\nNo one is joining for the party")
