import requests


def main():
    print("Input the URL:")
    url = input()
    response = requests.get(url)

    if response.status_code == 200:
        file = open('source.html', 'wb')
        content = requests.get(url).content
        file.write(content)
        file.close()
        print("Content saved.")
    else:
        print(f"The URL returned {response.status_code}!")


main()
