import sys
import os

nytimes_com = '''
This New Liquid Is Magnetic, and Mesmerizing

Scientists have created "soft" magnets that can flow 
and change shape, and that could be a boon to medicine 
and robotics. (Source: New York Times)


Most Wikipedia Profiles Are of Men. This Scientist Is Changing That.

Jessica Wade has added nearly 700 Wikipedia biographies for
important female and minority scientists in less than two 
years.

'''

bloomberg_com = '''
The Space Race: From Apollo 11 to Elon Musk

It's 50 years since the world was gripped by historic images
of Apollo 11, and Neil Armstrong -- the first man to walk 
on the moon. It was the height of the Cold War, and the charts
were filled with David Bowie's Space Oddity, and Creedence's 
Bad Moon Rising. The world is a very different place than 
it was 5 decades ago. But how has the space race changed since
the summer of '69? (Source: Bloomberg)


Twitter CEO Jack Dorsey Gives Talk at Apple Headquarters

Twitter and Square Chief Executive Officer Jack Dorsey 
addressed Apple Inc. employees at the iPhone maker’s headquarters
Tuesday, a signal of the strong ties between the Silicon Valley giants.
'''

saved_pages = {
    "bloomberg": bloomberg_com,
    "nytimes": nytimes_com
}


def create_file(directory, filename, contents):
    file_path = os.path.join(directory, filename)
    if not os.path.exists(file_path):
        with open(file_path, "w") as file:
            file.write(contents)


def create_directory(path):
    if not os.path.exists(path):
        os.makedirs(path)


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
        if user_input == "exit":
            break
        if "." in user_input:
            if user_input == "bloomberg.com":
                save_to_stack(stack_, current_page)
                print(bloomberg_com)
                create_file(absolute_path, "bloomberg", bloomberg_com)
                current_page = bloomberg_com
            elif user_input == "nytimes.com":
                save_to_stack(stack_, current_page)
                print(nytimes_com)
                create_file(absolute_path, "nytimes", nytimes_com)
                current_page = nytimes_com
            else:
                print_invalid_url()
        elif user_input == "back":
            if stack_:
                print(stack_.pop())
        else:
            clean_input = user_input.split(".")[0]
            if clean_input in saved_pages:
                save_to_stack(stack_, current_page)
                print(saved_pages[clean_input])
                current_page = saved_pages[clean_input]
            else:
                print_invalid_url()


if __name__ == "__main__":
    main()
