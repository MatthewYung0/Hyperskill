import requests
from bs4 import BeautifulSoup


def start_program():
    info = {}
    print("Input the URL:")
    url = input()
    headers = {'Accept-Language': 'en-US,en;q=0.5'}
    return info, url, headers


def finish_program(result=''):
    if not result:
        print("\nInvalid page!")
    else:
        print(result)


def get_result(info, url, headers):
    resp = requests.get(url, headers=headers)
    if resp and 'articles' in url:
        soup = BeautifulSoup(resp.content, 'html.parser')
        title = soup.find('title')
        meta = soup.find('meta', {'name': 'description'})
        info['title'] = title.text
        info['description'] = meta.get('content')
    return info


def main():
    info, url, headers = start_program()
    result = get_result(info, url, headers)
    finish_program(result)


main()
