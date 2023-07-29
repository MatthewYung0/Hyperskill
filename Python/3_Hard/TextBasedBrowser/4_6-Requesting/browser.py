import sys
import os
from os.path import exists

import requests


def create_file(directory, filename, contents):
    file_path = os.path.join(directory, filename)
    if not os.path.exists(file_path):
        with open(file_path, "w", encoding="utf=8") as file:
            file.write(contents)


def create_directory(path):
    if not os.path.exists(path):
        os.makedirs(path)


def print_file(path):
    with open(path, "r", encoding="utf=8") as file:
        print(file.read())


def print_invalid_url():
    print("Error: Invalid URL")


def save_to_stack(stack, page):
    if page != "":
        stack.append(page)


def main():
    https = "https://"
    args = sys.argv
    directory = args[1]
    # directory = "/home/myung/projects/hyperskill/python/Text-Based Browser/Text-Based Browser/task/tabs"
    create_directory(directory)
    absolute_path = os.path.join(os.getcwd(), directory)
    current_page = ""
    stack_ = []

    while True:
        user_input = input().strip()
        clean_input = user_input.split(".")[0]
        if user_input == "exit":
            break
        if "." in user_input:
            req = requests.get(os.path.join(https, user_input))
            if req.status_code == 200:
                save_to_stack(stack_, current_page)
                text = req.text
                print(text)
                create_file(absolute_path, clean_input, text)
                current_page = text
            else:
                print_invalid_url()
        elif user_input == "back":
            if stack_:
                print(stack_.pop())
        else:
            if exists(os.path.join(absolute_path, clean_input)):
                save_to_stack(stack_, current_page)
                print_file(os.path.join(absolute_path, clean_input))
                current_page = clean_input
            else:
                print_invalid_url()


if __name__ == "__main__":
    main()
