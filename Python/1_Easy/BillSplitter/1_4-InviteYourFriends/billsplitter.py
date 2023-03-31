list_of_friends = {}

num_of_friends = int(input("Enter the number of friends joining (including you):\n"))

if num_of_friends > 0:
    print("\nEnter the name of every friend (including you), each on a new line:")
    for friend in range(num_of_friends):
        list_of_friends[input()] = 0
    print(list_of_friends)
else:
    print("\nNo one is joining for the party")
