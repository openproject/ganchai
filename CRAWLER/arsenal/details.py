from lxml import etree
import requests

if __name__ == '__main__':
    detailpage = "http://www.android-arsenal.com//details/1/2261"
    html = requests.get(detailpage)
    selector = etree.HTML(html.text)

    titleHTML = selector.xpath('//*[@id="readme"]/article/*[1]/text()')
    descriptionHTML = selector.xpath('//*[@id="readme"]/article/p[1]/text()')

    print("".join(titleHTML))
    print("".join(descriptionHTML))
