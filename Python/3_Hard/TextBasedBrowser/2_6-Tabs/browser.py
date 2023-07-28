import sys
import os
from pathlib import Path
from os.path import exists

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


def create_file(directory, filename, contents):
    file_path = os.path.join(directory, filename)
    if not exists(file_path):
        with open(file_path, "w") as file:
            file.write(contents)


def create_directory(path):
    if not os.path.exists(path):
        os.makedirs(path)



def print_invalid_url():
    print("Error: Incorrect URL")


def main():
    args = sys.argv
    directory = args[1]
    # directory = "/home/myung/projects/hyperskill/python/Text-Based Browser/Text-Based Browser/task/tab"
    create_directory(directory)
    absolute_path = os.path.join(os.getcwd(), directory)
    while True:
        user_input = input()
        if "." in user_input:
            if user_input == "bloomberg.com":
                print(bloomberg_com)
                create_file(absolute_path, "bloomberg", bloomberg_com)
            elif user_input == "nytimes.com":
                print(nytimes_com)
                create_file(absolute_path, "nytimes", nytimes_com)
            else:
                print_invalid_url()
        elif user_input == "bloomberg":
            file = Path(os.path.join(absolute_path, user_input))
            if file.is_file():
                print(bloomberg_com)
            else:
                print_invalid_url()
        elif user_input == "nytimes":
            file = Path(os.path.join(absolute_path, user_input))
            if file.is_file():
                print(nytimes_com)
            else:
                print_invalid_url()
        elif user_input == "exit":
            break
        else:
            print_invalid_url()


if __name__ == "__main__":
    main()
