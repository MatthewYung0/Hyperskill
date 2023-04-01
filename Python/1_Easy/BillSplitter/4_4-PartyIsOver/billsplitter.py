import random

list_of_friends = {}

num_of_friends = int(input("Enter the number of friends joining (including you):\n"))


def add_friends():
    print("\nEnter the name of every friend (including you), each on a new line:")
    for friend in range(num_of_friends):
        list_of_friends[input()] = 0


def split_bill(bill_, lucky_friend):
    if lucky_friend == "None":
        for friend in list_of_friends:
            list_of_friends[friend] = round((bill_ / num_of_friends), 2)
    else:
        for friend in list_of_friends:
            if friend == lucky_friend:
                list_of_friends[friend] = 0
            else:
                list_of_friends[friend] = round((bill_ / (num_of_friends - 1)), 2)


def get_lucky_friend():
    answer = input("\nDo you want to use the \"Who is lucky?\" feature? Write Yes/No:\n")
    if answer == "Yes":
        friend_names = list(list_of_friends.keys())
        lucky_friend_ = random.choice(friend_names)
        print("\n" + lucky_friend_ + " is the lucky one!")
        return lucky_friend_
    else:
        print("\nNo one is going to be lucky")
        return "None"


if num_of_friends > 0:
    add_friends()
    bill = int(input("\nEnter the total bill value:\n"))
    lucky_friend = get_lucky_friend()
    split_bill(bill, lucky_friend)
    print(list_of_friends)
else:
    print("\nNo one is joining for the party")
