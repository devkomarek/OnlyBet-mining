# OnlyBet-mining
OnlyBet-mining jest modułem platformy OnlyBet. Moduł ten to zbiór statystyk drużyn, zawodników, rozegranych meczy.  
### Stak technologiczny:
#### -Mongo DB
#### -Spring Boot
#### -Ionic
#### -Scrapy
#### -Splash
#### -Docker
#### -scrapy-splash (pip module)
#### -JMESPath (pip module)

### Architektura
Cały system OnlyBet-mining opiera się na 2 elementach, mikroserwisu oraz botów (spiders). Za spoiwo tych dwóch elementów służy zespół kolejek. Wszystkie informacje zebrane są zapisywane do bazy danych. 

#### Mikrose

Za most łączący scraper-a i serwis restowy służy rabbitmq. Rabbitmq jest to open sourc-owy message broker. Rabbitmq tworzy kolejke do której można na wyjściu i wejściu podłączyć wiele urządzeń. Jako wzorzec messaging pattern wykorzystano Request-Reply. SpringAMQP przy każdym żądaniu geruje nowy Correlation Id.

