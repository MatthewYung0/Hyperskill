formats = ["plain", "bold", "italic", "inline-code", "link", "header", "unordered-list", "ordered-list", "new-line"]
prints = []


def save_file(prints_):
    file = open("output.md", 'w')
    for line in prints_:
        file.writelines(line)
    file.close()


def main_list(prints_, is_list):
    while True:
        no_of_rows = int(input("Number of rows: "))
        if no_of_rows < 1:
            print("The number of rows should be greater than zero")
        else:
            break

    for row in range(no_of_rows):
        number = str(int(row + 1))
        if is_list:
            prints_.append(number + ". " + input("Row #" + number + ": ") + "\n")
        else:
            prints_.append("* " + input("Row #" + number + ": ") + "\n")

def main_italic(prints_):
    prints_.append("*" + input("Text: ") + "*")


def main_inline(prints_):
    prints_.append("`" + input("Text: ") + "`")


def main_bold(prints_):
    prints_.append("**" + input("Text: ") + "**")


def main_plain(prints_):
    prints_.append(input("Text: "))


def main_link(prints_):
    label_ = input("Label: ")
    url_ = input("URL: ")
    prints_.append("[%s](%s)" % (label_, url_))


def main_header(prints_):
    def check_header_level(level):
        return False if int(level) < 1 or int(level) > 6 else True

    def generate_header(level, text):
        header = ""
        for level in range(level):
            header += "#"

        if len(prints_) != 0:
            header = "\n" + header + " " + text + "\n"
        else:
            header = header + " " + text + "\n"
        return header

    while True:
        level_ = int(input("Level: "))
        if check_header_level(level_):
            text_ = input("Text: ")
            prints_.append(generate_header(level_, text_))
            break
        else:
            print("The level should be within the range of 1 to 6")


def main():
    user_input_ = ""
    while user_input_ != "!done":
        user_input_ = input("\nChoose a formatter: ")
        if user_input_ == "!help":
            print(
                "Available formatters: plain bold italic header link inline-code ordered-list unordered-list new-line")
            print("Special commands: !help !done")
        elif user_input_ == "link":
            main_link(prints)
        elif user_input_ == "header":
            main_header(prints)
        elif user_input_ == "plain":
            main_plain(prints)
        elif user_input_ == "bold":
            main_bold(prints)
        elif user_input_ == "italic":
            main_italic(prints)
        elif user_input_ == "inline-code":
            main_inline(prints)
        elif user_input_ == "new-line":
            prints.append("\n")
        elif user_input_ == "ordered-list":
            main_list(prints, True)
        elif user_input_ == "unordered-list":
            main_list(prints, False)
        else:
            print("Unknown formatting type or command")

        for print_ in prints:
            print(print_, end="")

    save_file(prints)


if __name__ == "__main__":
    main()
