import string
import requests
import os
from bs4 import BeautifulSoup

def clear_folder(folder_path):
    try:
        for filename in os.listdir(folder_path):
            file_path = os.path.join(folder_path, filename)
            if os.path.isfile(file_path):
                os.remove(file_path)
            elif os.path.isdir(file_path):
                clear_folder(file_path)  # Recursively clear subdirectories
    except Exception as e:
        print(f"An error occurred while clearing the folder '{folder_path}': {e}")

def sanitize_filename(name):
    # Remove punctuation and replace whitespaces with underscores
    translator = str.maketrans("", "", string.punctuation)
    return name.translate(translator).replace(" ", "_")


def save_article_to_file(title, body):
    sanitized_title = sanitize_filename(title.strip())
    filename = f"{sanitized_title}.txt"
    with open(filename, "w", encoding="utf-8") as file:
        file.write(body.strip())

def main():
    number_of_pages = int(input())
    article_type = str(input())
    for page_number in range(1, number_of_pages + 1):
        current_dir = os.getcwd()
        directory = "Page_" + str(page_number)
        try:
            os.mkdir(directory)
        except FileExistsError:
            pass
        os.chdir(directory)
        clear_folder("Page_" + str(page_number))
        url = "https://www.nature.com/nature/articles?sort=PubDate&year=2020&page=" + str(page_number)
        response = requests.get(url)
        if response.status_code == 200:
            content = response.content
            soup = BeautifulSoup(content, "html.parser")
            articles = soup.find_all("article")
            for article in articles:
                # Find the article type
                article_type_tag = article.find("span", {"data-test": "article.type"})
                # if article_type_tag and article_type_tag.text.strip() == "News":
                if article_type_tag and article_type_tag.text.strip() == article_type:
                    # Find the article link
                    article_link_tag = article.find("a", {"data-track-action": "view article"})
                    if article_link_tag and "href" in article_link_tag.attrs:
                        article_url = "https://www.nature.com" + article_link_tag["href"]
                        article_response = requests.get(article_url)
                        if article_response.status_code == 200:
                            article_content = article_response.content
                            article_soup = BeautifulSoup(article_content, "html.parser")
                            # Find the article title
                            title = article_soup.find("title").get_text().strip()
                            # Find the article body
                            global body
                            if article_type == "News":
                                body = article_soup.find("p", {"class": "article__teaser"}).get_text()
                            elif article_type == "Nature Briefing":
                                body = article_soup.find("div", {"class": "c-article-teaser-text"}).get_text()
                            save_article_to_file(title, body)
            os.chdir(current_dir)
        else:
            print(f"The URL returned {response.status_code}!")
    print("Saved all articles.")


main()
