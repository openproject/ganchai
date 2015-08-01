from lxml import etree
import requests
import sys

reload(sys)

sys.setdefaultencoding('utf-8')

html = requests.get("http://www.android-arsenal.com/?page=1&sort=created")
selector = etree.HTML(html.text)

content = selector.xpath('//div[@id="projects"]/div/div/div[1]/div[1]/a[1]/text()')
for each in content:
    print each
