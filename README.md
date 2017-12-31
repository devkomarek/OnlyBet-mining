# OnlyBet-mining

### Stak technologiczny:
#### -Mongo DB
#### -Spring Boot
#### -Ionic
#### -Scrapy
#### -Splash
#### -Docker
#### -scrapy-splash (pip module)
#### -JMESPath (pip module)

Za most łączący scraper-a i serwis restowy służy rabbitmq. Rabbitmq jest to open sourc-owy message broker. Rabbitmq tworzy kolejke do której można na wyjściu i wejściu podłączyć wiele urządzeń. Jako wzorzec messaging pattern wykorzystano Request-Reply. SpringAMQP przy każdym żądaniu geruje nowy Correlation Id
