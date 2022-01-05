import requests
import collections
import textwrap
from datetime import datetime

from PIL import Image
from PIL import ImageFont
from PIL import ImageDraw

github_username = input("Please provide your github username: ")

token = "YOUR GITHUB LOGIN TOKEN"

header = {'Authorization': 'token %s' % token}
api_url_user = f"https://api.github.com/users/{github_username}"
api_url_repos = f"https://api.github.com/users/{github_username}/repos"

response_user = requests.get(api_url_user, headers=header)
response_repos = requests.get(api_url_repos, headers=header)

data_user = response_user.json()
data_repos = response_repos.json()

now = datetime.now()
year_today = int(now.year)

language_list = set()
repository_list = set()


def get_years_on_github():
    year_start = 0
    for key, value in data_user.items():
        if (key == "created_at"):
            year_start = int(value[:4])
    return year_today - year_start


def get_profile_pic():
    profile_url = ""
    for key, value in data_user.items():
        if (key == "avatar_url"):
            profile_url = value

    img_data = requests.get(profile_url).content
    with open('assets/profile_pic.png', 'wb') as handler:
        handler.write(img_data)


def get_repo_count():
    repo_count = 0
    for repository in data_repos:
        created = repository["created_at"]
        year_created = int(created[:4])
        if (year_created == year_today):
            repository_list.add(repository["name"])
            repo_count += 1
    return repo_count


def top5_bytes_lang_repo():
    bytes_per_repo = []  # [('MyOne', 300), ('gigi', 310)]
    bytes_per_language = []  # [('Python', [2671]), ('Python', [6104]), ('Java', [6150]), ('Makefile', [357]), ('Python', [3773]), ('Java', [8968]), ('Makefile', [344]), ('BlitzBasic', [797]), ('Python', [13819])]
    for repo in repository_list:
        url = f"https://api.github.com/repos/" + github_username + "/" + repo + "/languages"
        response = requests.get(url)
        data = response.json()
        value_repo = 0
        for key, value in data.items():
            value_repo += value
            bytes_per_language.append((key, [value]))
        bytes_per_repo.append((repo, value_repo))
    collection = collections.defaultdict(list)
    for key, value in bytes_per_language:
        collection[key].extend(value)  # add or create list

    list_tmp = list(collection.items())
    top_languages = []
    for key, value in list_tmp:
        top_languages.append((key, sum(value)))
    top_languages.sort(key=lambda x: x[1], reverse=True)

    bytes_per_repo.sort(key=lambda y: y[1], reverse=True)
    top_repos = bytes_per_repo
    return top_languages[:5], top_repos[:5]


def count_user_commits():
    commits = 0
    for repo in data_repos:
        response = requests.get(repo['url'] + '/commits')
        data = response.json()
        commits += len(data)
    return commits


def generate_img1():
    get_profile_pic()
    back = Image.open('assets/background1.png', 'r')
    img = Image.open('assets/profile_pic.png', 'r')

    img = img.resize((250, 250))
    back_w, back_h = back.size
    img_w, img_h = img.size
    offset = ((back_w - img_w) // 2, (back_h - img_h) // 2 - 270)
    back.paste(img, offset)

    draw = ImageDraw.Draw(back)
    font = ImageFont.truetype("fonts/GothamMediumRegular.ttf", 48)

    years = get_years_on_github()
    # years = 5
    if years <= 1:
        text = github_username + ", it's time to celebrate 1 year on Github with a year review!"
    else:
        text = github_username + ", it's time to celebrate " + str(years) + " years on Github with a year review!"
    para = textwrap.wrap(text, width=25)

    current_h, pad = back_h // 2, 15
    for line in para:
        w, h = draw.textsize(line, font=font)
        draw.text(((720 - w) / 2, current_h), line, (51, 51, 51), font=font)
        current_h += h + pad

    back.show()
    back.save("out1.png")


def generate_img2():
    back = Image.open('assets/background2.png', 'r')
    back_w, back_h = back.size

    draw = ImageDraw.Draw(back)
    font = ImageFont.truetype("fonts/GothamMediumRegular.ttf", 42)

    repo_count = get_repo_count()
    commit_count = count_user_commits()
    text = ["You commited " + str(commit_count) + " times", "to " + str(repo_count) + " public repositories",
            "this year.", "", "", "Wow, that's a lot of coffee!"]

    current_h, pad = back_h // 2 - 170, 25
    for line in text:
        w, h = draw.textsize(line, font=font)
        draw.text(((720 - w) / 2, current_h), line, (51, 51, 51), font=font)
        current_h += h + pad

    back.show()
    back.save("out2.png")


def generate_img3():
    top5_lang, top5_repo = top5_bytes_lang_repo()
    print(top5_lang, top5_repo)
    top_language = top5_lang[0][0]
    print(top_language)

    back = Image.open('assets/background3.png', 'r')

    if top_language == 'Java':
        img = Image.open('assets/java.png', 'r')
    elif top_language == 'Python':
        img = Image.open('assets/python.png', 'r')
    elif top_language == 'JavaScript':
        img = Image.open('assets/js.png', 'r')
    elif top_language == 'C++':
        img = Image.open('assets/cpp.png', 'r')
    elif top_language == 'HTML':
        img = Image.open('assets/html.png', 'r')
    else:
        img = Image.new('RGBA', (250, 250), (255, 255, 255, 255))

    img = img.resize((250, 250))
    back_w, back_h = back.size
    img_w, img_h = img.size
    offset = ((back_w - img_w) // 2, (back_h - img_h) // 2 - 280)
    back.paste(img, offset)

    draw = ImageDraw.Draw(back)
    font = ImageFont.truetype("fonts/GothamMediumRegular.ttf", 38)

    text = top_language + " was your favourite language this year!"
    para = textwrap.wrap(text, width=24)
    current_h, pad = back_h // 2 - 110, 20
    for line in para:
        w, h = draw.textsize(line, font=font)
        draw.text(((720 - w) / 2, current_h), line, (51, 51, 51), font=font)
        current_h += h + pad

    font = ImageFont.truetype("fonts/GothamMediumRegular.ttf", 28)

    text = []
    current_h, pad = (back_h - img_h) / 2 + 180, 20
    n = 1
    text.append("Top languages:")
    for lang in top5_lang:
        text.append(str(n) + ". " + lang[0])
        n += 1
    for line in text:
        w, h = draw.textsize(line, font=font)
        draw.text((720 / 2 - 340, current_h), line, (51, 51, 51), font=font)
        current_h += 20 + pad

    font = ImageFont.truetype("fonts/GothamMediumRegular.ttf", 28)

    text = []
    current_h, pad = (back_h - img_h) / 2 + 180, 20
    n = 1
    text.append("Top largest projects:")
    for repo in top5_repo:
        if (len(repo[0]) >= 20):
            txt = repo[0][18] + "..."
        else:
            txt = repo[0]
        text.append(str(n) + ". " + txt)
        n += 1
    for line in text:
        w, h = draw.textsize(line, font=font)
        draw.text((720 / 2 - 50, current_h), line, (51, 51, 51), font=font)
        current_h += 20 + pad

    back.show()
    back.save("out3.png")

# Debug prints.
# print('Years on Github:', get_years_on_github())
# get_profile_pic()
# print('Repos this year:', get_repo_count())
# print(top5_bytes_lang_repo())
# print('Commits this year:', count_user_commits())

generate_img1()
generate_img2()
generate_img3()
