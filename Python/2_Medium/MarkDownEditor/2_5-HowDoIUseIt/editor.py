formats = ["plain", "bold", "italic", "inline-code", "link", "header", "unordered-list", "ordered-list", "new-line"]
user_input = ""

while user_input != "!done":
    user_input = input("Choose a formatter: ")

    if user_input == "!help":
        print("Available formatters: plain bold italic header link inline-code ordered-list unordered-list new-line")
        print("Special commands: !help !done")
    elif user_input in formats or user_input == "!done":
        continue
    else:
        print("Unknown formatting type or command")

