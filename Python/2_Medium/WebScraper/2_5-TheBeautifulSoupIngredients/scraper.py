import json
import requests
from bs4 import BeautifulSoup

def main():
    global title
    global description
    print("Input the URL:")
    link = input()
    response = requests.get(link)
    isInvalidLink = False

    if response.status_code == 200:
        parser = "html.parser"
        req = requests.get(link)
        soup = BeautifulSoup(req.text, parser)
        try:
            title = soup.find('title').get_text()
            description = soup.find('meta', {'name': 'description'}).get('content')
        except:
            title = "None"
            description = "None"

        if title == "None" or title == "" or description == "None" or description == "":
            isInvalidLink = True
    else:
        isInvalidLink = True

    if isInvalidLink == True:
        print("Invalid page!")
    else:
        print("{\"title\": \"" + title + "\", \"description\": \"" + description + "\"}")

main()
