import argparse
import os
import re
from pathlib import Path


def correct_length(line):
    MAX_CHAR = 79
    return len(line) <= MAX_CHAR


def correct_indentation(line):
    pattern = r'^( {4})*(?!\s)'
    if line.rstrip() == "":
        return True
    return re.match(pattern, line) is not None


def correct_with_no_semi(line):
    pattern = r'^[^#]*;(\s*$| +#)'
    return re.match(pattern, line) is None


def correct_comment(line):
    pattern = r'^[^#]*[^# ]+ ?#'
    return not re.match(pattern, line)


def correct_todo(line):
    TODO = "TODO"
    pattern = re.compile(r'#.*\btodo\b', re.IGNORECASE)
    matches = pattern.findall(line)
    if len(matches) != 0:
        for word in matches:
            if word != TODO:
                return False
    return True


def correct_lines(blank_lines_):
    if blank_lines_ == 3:
        return False
    return True


def update_blank_line_counter(line, blank_lines_):
    if len(line.rstrip("\n")) == 0 or line.isspace() is True:
        blank_lines_ += 1
    else:
        blank_lines_ = 0
    return blank_lines_


def print_error(path, line_number, error_numbers):
    for error in error_numbers:
        print("{}: Line {}: ".format(path, line_number), end='')
        if error == 1:
            print("S001 Too long")
        elif error == 2:
            print("S002 Indentation is not a multiple of four")
        elif error == 3:
            print("S003 Unnecessary semicolon")
        elif error == 4:
            print("S004 At least two spaces required before inline comments")
        elif error == 5:
            print("S005 TODO found")
        elif error == 6:
            print("S006 More than two blank lines used before this line")


def check_file(path):
    try:
        with open(path, 'r') as file:
            blank_lines = 1
            error_numbers = []
            for line_number, line in enumerate(file, start=1):
                if not correct_length(line):
                    error_numbers.append(1)
                if not correct_indentation(line):
                    error_numbers.append(2)
                if not correct_with_no_semi(line):
                    error_numbers.append(3)
                if not correct_comment(line):
                    error_numbers.append(4)
                if not correct_todo(line):
                    error_numbers.append(5)
                if not correct_lines(blank_lines):
                    error_numbers.append(6)
                blank_lines = update_blank_line_counter(line, blank_lines)
                print_error(path, line_number, error_numbers)
                error_numbers.clear()
    except FileNotFoundError:
        print(f"File not found")
    except Exception as e:
        print(f"An error occurred: {e}")


def main(args):
    if os.path.isfile(args.path):
        check_file(args.path)
    elif os.path.isdir(args.path):
        path = Path(args.path)

        sorted_list = get_sorted_list_of_paths(path)

        for file in sorted_list:
            if file.is_file():
                check_file(file)


def get_sorted_list_of_paths(path):
    list_of_files = []
    for file in path.iterdir():
        list_of_files.append(file)
    return sorted(list_of_files, key=lambda x: x.name)


def get_path():
    parser = argparse.ArgumentParser(description="Path to file, or directory containing the files")
    parser.add_argument("path")
    return parser.parse_args()


if __name__ == '__main__':
    main(get_path())
