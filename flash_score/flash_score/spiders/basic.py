# -*- coding: utf-8 -*-
import json

import scrapy
import urllib.parse
from flash_score.items import ClubSearch

class BasicSpider(scrapy.Spider):
    name = 'basic'
    amqp_key = 'test'
    allowed_domains = ['https://www.flashscore.com/', 'https://www.s.flashscore.com/']

    def _make_request(self, mframe, hframe, body):
        print(body.decode())
        url = body.decode()
        return scrapy.Request(url, callback=self.parse)
    # def start_requests(self):
    #     splash = "http://localhost:8050/render.html?url="
    #     flash = urllib.parse.quote_plus("https://s.flashscore.com/search/?q="+self.query+"&l=1&s=1&f=1%3B1&pid=2&sid=1")
    #     yield scrapy.Request(splash+flash)
    def parse(self, response):
        item = ClubSearch()
        json_response = json.loads(response.xpath("//pre/text()").extract_first().split("Callback(",1)[1][:-2])
        for club in json_response["results"]:
            item["id"] = club["id"]
            item["url"] = club["url"]
            item["name"] = club["title"].split("(",1)[0].strip()
            item["country"] = club["title"].split("(",1)[1].strip()[:-1]
            #item["logo_url"] = club["logo_url"]
            yield item
