import string
import requests
from bs4 import BeautifulSoup


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
    url = "https://www.nature.com/nature/articles?sort=PubDate&year=2020&page=3"
    response = requests.get(url)
    if response.status_code == 200:
        content = response.content
        soup = BeautifulSoup(content, "html.parser")
        articles = soup.find_all("article")
        for article in articles:
            # Find the article type
            article_type_tag = article.find("span", {"data-test": "article.type"})
            if article_type_tag and article_type_tag.text.strip() == "News":
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
                        body = article_soup.find("p", {"class": "article__teaser"}).get_text()
                        save_article_to_file(title, body)
    else:
        print(f"The URL returned {response.status_code}!")


main()
