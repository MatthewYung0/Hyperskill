import os
import requests
import sys
from colorama import Fore
from os.path import exists
from bs4 import BeautifulSoup


def create_file(directory, filename, contents):
    file_path = os.path.join(directory, filename)
    if not os.path.exists(file_path):
        with open(file_path, "w", encoding="utf=8") as file:
            file.write(contents)


def create_directory(path):
    if not os.path.exists(path):
        os.makedirs(path)


def extract_and_highlight_links(page_content):
    soup = BeautifulSoup(page_content, "html.parser")
    for link in soup.find_all('a'):
        link.replace_with(Fore.BLUE + link.text + Fore.RESET)  # Highlight the link text in blue
    return soup.get_text()


def print_file(path):
    with open(path, "r", encoding="utf=8") as file:
        print(file.read())


def print_invalid_url():
    print("Error: Invalid URL")


def save_to_stack(stack, page):
    if page != "":
        stack.append(page)


def main():
    args = sys.argv
    directory = args[1]
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
            req = requests.get(os.path.join("https://", user_input))
            if req.status_code == 200:
                text = extract_and_highlight_links(req.content)
                save_to_stack(stack_, current_page)
                create_file(absolute_path, clean_input, text)
                current_page = text
                print(text)
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
