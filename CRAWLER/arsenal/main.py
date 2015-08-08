from lxml import etree
from multiprocessing.dummy import Pool as ThreadPool
import requests
import sys

reload(sys)

sys.setdefaultencoding('utf-8')

def write(url, title):
    f.writelines(str(title) + "\n")
    f.writelines(url + "\n\n")
    f.writelines("\n\n")

def spider(url):
    html = requests.get(url)
    selector = etree.HTML(html.text)

    project_list = selector.xpath('//a[starts-with(@href, "/details")]')
    for project in project_list:
        project_link = "http://www.android-arsenal.com" + "".join(project.xpath('@href'))
        project_title = "".join(project.xpath("text()"))
        project_image = "".join(project.xpath("parent::*/parent::*/following-sibling::*[1]/p/img/@data-layzr"))
        project_desc = "".join(project.xpath('parent::*/parent::*/following-sibling::*[1]/p/text()'))
        print project_title
        print project_link
        print project_image
        print project_desc
        print "******************one project end************************\n"
        write("".join(project_link), "".join(project_title))

    # for project in project_list:
    #     title = project.xpath('div[@class="header"]/div[@class="title"]/a[1]/text()')
    #     desc = project.xpath('div[@class="desc"]/p/text()')
    #     image = project.xpath('div[@class="desc"]/p[2]/img/@data-layzr')
    #     url = project.xpath('div[@class="header"]/div[@class="title"]/a/@href')
    #
    #     if len(title) > 0:
    #         item["title"] = title[0]
    #     else:
    #         item["title"] = ""
    #
    #     if len(desc) > 0:
    #         item["desc"] = " ".join(desc)
    #     else:
    #         item["desc"] = ""
    #
    #     item["image"] = " ".join(image)
    #
    #     if len(url) > 0:
    #         item["url"] = url[0]
    #     else:
    #         item["url"] = ""
    #
    #
    #     print "================================="
    #     print item["title"]
    #     print item["desc"]
    #     print image
    #     print item["url"]
    #     print "================================="

if __name__ == '__main__':
    pool = ThreadPool(1)
    f = open('detail_page_url_list.txt', 'w')
    page =[]
    for i in range(1, 2):
        newpage = "http://www.android-arsenal.com/?page=" + str(i) + "&sort=created"
        page.append(newpage)

    results = pool.map(spider, page)
    pool.close()
    pool.join()
    f.close()
