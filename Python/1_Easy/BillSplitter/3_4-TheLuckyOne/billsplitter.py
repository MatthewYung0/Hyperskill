import random

list_of_friends = {}

num_of_friends = int(input("Enter the number of friends joining (including you):\n"))


def add_friends():
    print("\nEnter the name of every friend (including you), each on a new line:")
    for friend in range(num_of_friends):
        list_of_friends[input()] = 0


def split_bill(bill):
    for friend in list_of_friends:
        list_of_friends[friend] = round((bill / num_of_friends), 2)


def is_lucky():
    answer = input("\nDo you want to use the \"Who is lucky?\" feature? Write Yes/No:\n")
    if answer == "Yes":
        friend_names = list(list_of_friends.keys())
        print("\n" + random.choice(friend_names) + " is the lucky one!")
    else:
        print("\nNo one is going to be lucky")


if num_of_friends > 0:
    add_friends()
    bill = int(input("\nEnter the total bill value:\n"))
    is_lucky()
    # print(list_of_friends)
else:
    print("\nNo one is joining for the party")
