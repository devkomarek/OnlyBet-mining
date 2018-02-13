# OnlyBet-mining (wersja dokumentacji v.0.7.1)
OnlyBet-mining jest modułem platformy OnlyBet. Moduł ten to zbiór statystyk drużyn, zawodników, rozegranych meczy.
### Zestaw technologii:
-Mongo DB
-Spring Boot
-Ionic
-Scrapy
-Splash
-Docker
-scrapy-splash (pip module)
### Architektura
Cały system OnlyBet-mining opiera się na 2 elementach, mikroserwisu oraz botów (spiders). Za spoiwo tych dwóch elementów służy zespół kolejek. Wszystkie informacje zebrane są zapisywane do bazy danych.

W poniższych opisach będę odwoływał się do nazewnictwa zamieszczonego wyżej modelu. I tak jeśli w opisie znajdzie się Sn to proszę to interpretować jako n-ty skrapujący bot (spider), a SM jako spider manager. Prosze mieć to na uwadze!
### Cechy projektu:
#### -Połącznie mikroserwisów jeden do wielu (1-n) gdzie 1 to SM, a n to Sn
#### -Wielojęzyczność (java-python-javascript)
#### -Kolejowa komunikacja mikroserwisów
#### -Całość system wykonany przy wykorzystaniu narzędzi, które mają otwarty kod źródłowy!
## Mikroserwisy
Idąc tokiem myślowym, im system prostszy tym łatwiej nim zarządzać to pojęcie mikroserwis jest tym czego szukamy. Możemy zauważyć zwiększający się trend firm idących w tego typu rozwiązania. Jedną z rodzimych firm jest Allegro. Na styczeń 2018 roku posiada ona ponad 400 mikroserwisów, co naprawdę nie jest małą liczbą.
Popularność SOA (Service Oriented Architecture), znacznie zmalała na rzecz mikroserwisów. SOA to zbiór pomniejszych serwisów, które realizują jakąś logikę biznesową. Mikroserwis to drobna część serwisu w architekturze SOA, pojedyncza operacja. W przypadku gdyby ten projekt był zrealizowany w SOA mielibyśmy tylko jedną instancję zwaną OnlyBet-mining, a tak w przypadku mikroserwisów mamy:
Kolejka RabbitMQ - odpowiada za dobrą i prawidłową komunikację
#### Sn - pojedyncza jednostka z nadanym celem do zrealizowania. Tutaj zebranie informacji ze wskazanej strony
#### SM - spider manager. Zarządza Sn
#### Splash - serwis odpowiadający za interpretowanie javascript-u
#### DB - baza danych odpowiedzialna za przechowywanie danych
#### Spring Boot - jednostka centralna, łącząca, koordynująca backend systemu OnlyBet-mining
#### Ionic - frontend systemu OnlyBet-mining

Takie wyodrębnienie serwisów daje większą swobodę uruchomienia OnlyBet. Świadomość możliwości postawienia każdego serwisu na osobnej maszynie jest czymś nieocenionym w budowaniu całej aplikacji.
Poprawianie błędów w poszczególnych serwisach odbywa się, niezależnie od pozostałych, a testowanie jest naprawdę czymś przyjemnym. 
Niżej zostaną omówione poszczególne elementy systemu.

## RabbitMQ
RabbitMQ jest Message brokerem, mostem łączącym serwisy. RabbitMQ sam w sobie jest bardzo rozbudowanym narzędziem. Do dyspozycji mamy agentów, opcje routingu, szereg ustawień kolejek, a to tylko wierzchołek góry. Każdy z tych klocków możemy łączyć na różne sposoby tworząc wydajne systemy. Posiada on również duże wsparcie języków programowania takich jak java,C#,python. 
RabbitMQ w systemie OnlyBet-mining działa w trybie RPC (Remote Procedure Call). Tryb ten jest wymuszony działaniem klienta. Klient z założenia nie chce czekać na wynik, dlatego tryb pracy RPC jest tutaj jak najbardziej na miejscu. Na czym polega działanie RPC? Tryb ten składa się z dwóch rodzajów wiadomości request i reply. Podczas zaistnienia potrzeby zdobycia informacji o drużynach. Wiadomość request trafia do kolejki Q1. Kiedy SM będzie posiadał dostateczną odpowiedź na zlecone zadanie to wówczas wysyła on do kolejki Q2 reply. Wszystko odbywa się bardzo szybko, tak naprawdę najdłuższym czasem postoju całej operacji jest czas oczekiwania SM na odpowiedź od Sn. Czas operacji można policzyć na podstawie sumy:
T=Tc1*2+Tr1+Tr2+Ts1+Tsp+Tr3+Tr4+Tdb
Gdzie, Tdb to czas operacji związany z bazą danych. Można zauważyć że wraz ze wzrostem czasu operacji Tdb nasz wzór przekształca się w T=Tdb+Tc1*2
Czas przetwarzania zapytania od klienta przez SpringBoot został tutaj pominięty ze względu na jego małe znaczenie.
Do połączenia się z RabbitMQ wykorzystywani są klienci SpringAMQP i pika :). Akronim AMQP pochodzi od Advance Message Queuing Protocol. Jest to otwarty standard protokołu komunikacji kolejkowej (ISO|IEC 19464). W warstwie sieci występuje on na 7 poziomie - warstwie aplikacji. RabbitMQ oczywiście jest zbudowany w oparciu o ten protokół.
## Uzasadnienie
RabbitMQ to bardzo wszechstronne narzędzie. Wykorzystanie z ulubionym językiem programowania nie stanowi problemu. Dlaczego RabbitMQ a nie bezpośrednia komunikacja przez HTTP? Z dwóch powodów. Ilość botów jest nieznana dla SpringBoota to SM zarządza ilością instancji. Ilość skrapowanych stron i ich różnorodność będzie się powiększać. Kolejki bardzo pomagają w zarządzaniu.
Dlaczego RabbitMQ, a nie Kafka? RabbitMQ jest starszy i jest ogólnego przeznaczenia. Głównym powodem odrzucającym Kafke był brak wsparcia dla innych języków, w przeciwieństwie RabbitMQ, który wsparcie ma świetne.


## Spider Manager
Sprider Manager to przede wszystkim klient pika. Napisany w pythonie. Jego rolą jest odbieranie wiadomości od RabbitMQ i tworzenia na ich podstawie skraperów. Później gdy dostanie odpowiedź zwrotną wysyła ją bezpośrednio do RabbitMQ. Obecnie nie ma potrzeby żeby wykonywał więcej czynności.
## Uzasadnienie
Spider Manager musi istnieć aby móc zapanować nad instancjami Sn. Dlaczego python? Głównie ze względu na niewielki wymiar funkcji jakie dostarcza SM, oraz z uwagi że Sn są napisane przy użyciu technologii Scrapy. Dla lepszej komunikacji pomiędzy SM a Sn, został on napisany w pythonie.


## Splash
Splash jest to serwis renderujący javascript oraz dysponujący interfejsem HTTP. Inaczej, minimalna przeglądarka internetowa napisana w pythonie przy użyciu technologii Twisted oraz QT5. Jak na tak małą przeglądarkę dysponuje on sporą funkcjonalność analityczną. Każde żądanie to zbiór informacji o szybkości przesyłu, renderingu.
## Uzasadnienie
Scrappy nie oferuje renderowania javascriptu, ponieważ jest czasochłonne. Czasami istnieje potrzeba zinterpretowania javascriptu, dlatego godzimy się na przeglądarkę Splash, mając świadomość z jakimi konsekwencjami musimy się liczyć. Dlaczego Splash? Splash został napisany w pythonie, co ułatwia jego wdrożenie do projektu. Poza tym w zupełności spełnia swoje zadanie.

## Spiders
Spiders to zbiór pojawiających się skraperów gotowych zebrać potrzebne informacje ze stron. Tutaj została wykorzystana technologia Scrapy. Całość została napisana w pythonie. Scrapy cechuję się lekkością. Robi tylko to co ma zrobić, nie przechowuje żadnych danych, nie pomaga  przy renderowaniu javascriptu. Daję nam to z jednej strony spore pole do popisu, z drugiej strony wymaga od nas więcej wysiłku. Dzięki popularności jesteśmy w stanie w szybki sposób uzyskać pomoc, jeśli wcześniej nie znajdziemy tego czego szukamy w sporej dokumentacji. Żywot spidera rozpoczyna się wraz z decyzją od SM. Wówczas dostaje on na wejściu informacje o danych do zebrania. Podczas wykonywania swojego zadania może się wspomóc Splashem. Kiedy uda mu się wykonać zadanie lub nie, informacje o przebiegu i wyniku wysyła do SM. Po tym instancja spidera znika. Wyżej opisany tok jest czymś co wykonuje się cały czas podczas scrapowania. Podczas procesu istnieje możliwość podłączenia się do scrapera, a namiarami do ustawienia połączenia dysponuje SM.
Do połączenia wykorzystywany jest telnet, ale również pojedynczy spider może istnieć jako serwis restowy. W obecnym projekcie nie jest to wykorzystywane, ponieważ nie ma potrzeby komunikowania się z instancjami.
## Uzasadnienie
Jeśli szukacie bardzo popularnego frameworka do zbierania informacji ze stron, lub macie gotowy system do którego chcielibyście takowe funkcjonalności dodać to Scrapy jest odpowiednim wyborem. Dlaczego Scrapy?
Alternatywy w postaci javy, lub innych języków odpadają. W pythonie jesteśmy wstanie napisać działającego scrapera wciągu jednej nocy, co nie jest takie oczywiste w przypadku kompilowanych języków. Szybkość w pisaniu to spory atut, ale co z alternatywami wśród pythona np. BeautifulSoup, lub Selenium? Selenium odpada już na samym początku. Jest to narzędzie przeznaczone dla testerów automatycznych. Jego główną wadą jest powolność. Tam gdzie scrapy uderza bezpośrednio pod wskazany URL, tam Selenium klika w przycisk. Również dostarczane narzędzia przez obie technologie są bardzo odrębne i widać że idą w innym kierunku. BeautifulSoup to poprostu bibliotek, która służy do parsowania stron HTML oraz XML. Za pomocą istnieje możliwość zbudowania scrapera, lecz nie celem tego projektu jest budowanie scrapera tylko jego wykorzystanie.

## Spring Boot
Dziecko z kuźni pivotal software. Bardzo popularne rozwiązanie wśród świata javy. Za pomocą Spring Boota można zbudować naprawdę dobrze działający serwis restowy. Dodatkowo oferuje masę bibliotek, które bez problemu można wdrożyć do projektu. W OnlyBet-mining zostały wykorzystane zależności Spring Data, SpringAMQP, MongoData. Wadą Spring Boota jest spory próg wejścia, chyba że wcześniej pracowaliście z Springiem. Spring Boot w większości opiera się na refleksji. Do prawidłowego funkcjonowania wykorzystujemy adnotacje springowe. Wszystko to sprawia że pisanie kodu tego co znamy z javy jest tutaj nie na miejscu. Poprostu to nie będzie działać. Lub wykonamy mnóstwo pracy, gdzie tak naprawdę wystarczyło użyć jednej adnotacji. Spring Boot w projekcie OnlyBet-mining pełni rolę serca. On sam nie wykonuje pracy w wydobyciu danych z serwisu, ale pomaga kompresować zapytania zapisywać, czytać dane z bazy, lub odpowiadać klientowi. Tak, Spring Boot tutaj jest punktem z którym  backend i frontend odbywa komunikację.  Komunikacja frontendu to endpointy serwisu restowego, a backend to klient RabbitMQ. 
Bardzo ważnym punktem jest obsługa bazy. To tutaj zachodzi proces zapisu zdobytych oraz przetworzonych danych. Zapis do bazy z poziomu Spring Boota odbywa się w dwóch trybach. Pierwszy, podczas interpretacji zapytań przychodzących z frontendu. Drugi, podczas pracy po przetworzeniu danych. Pierwszy proces jest oczywisty. Drugi to co warto jest asynchronicznie przeprowadzić na bazie. Większość danych potrzebnych do rozpatrzenia konkretnego modelu, wymagają n danych do ich obliczeń. Takie modele nie są wstanie być przekazane od razu po zapytaniu od klienta, zostaną one przekazane prawdopodobnie przy następnym takim samym zapytaniu. Prawdopodobnie, ponieważ może istnieć większa potrzeba eksploracji danych do osiągnięcia danego modelu.
## Uzasadnienie 
Spring Boot to narzędzie próbujące zrobić wszystko za ciebie. Przez to spotkałem się z sporymi problemami związanymi z debugowaniem aplikacji. Część błędów rzucanych przez silnik trafionych w złym momencie pisania kodu, powodują że z konkretnej informacji o błędzie dostajemy nic nie mówiący exception. Naprawdę kiedy w kodzie masz zaimplementowaną metodę HandleMessage, a on ci mówi że powinieneś zaimplementować metodę HandleMessage, to to sprawia że zaczynamy się patrzeć na monitor przez naprawdę długi okres czasu, czytając błąd oraz patrząc się na naszą metodę. Ja przy tym konkretnym przypadku straciłem 3 dni. Choć podejrzewałem gdzie był błąd, to nadal nie miałem 100% pewności że dobrze szukam. Ten ślepy maraton zniechęcił mnie do Spring Boota. Dlaczego Spring Boot? Hmm doświadczenie? Myślę że warto jest coś napisać w Springu z uwagi na ciekawy koncept. Wybranie Spring Boota było kierowane tym że dostarcza wszystkiego co jest mi potrzebne do połączenia całej architektury. Z jednej strony dysponuje narzędziami do bardzo wygodnej komunikacji z bazą danych, z drugiej strony podpinamy klienta AMQP i tworzymy połączenia z kolejką. 

## MongoDB
MongoDB baza be powiązań. Worek do którego wrzucamy dane i je wyciągamy. I choć wydawało by się to tak banalne to za wszystkimi koncepcjami, na których została zbudowana baza MongoDB jest poparta wyjaśnieniami dlaczego i po co funkcjonuje taki typ bazy. I choć nasz świat to przede wszystkim powiązania, czyli świat zapytań SQL to  w dobie systemów rozproszonych bazy pokroju MongoDB jednak bardziej zyskują na popularności. Ciężko się nie dziwić. Podczas projektowania baz opartych na tabelkach nikt nie myślał o rozproszeniu. Dlatego rozwiązania typu transakcje w sql się nie sprawdzają. Można by tu naprawdę długo wymieniać, leczy przyjrzyjmy się zastosowaniu bazy MongoDB w OnlyBet-mining. Baza nawiązuje komunikację tylko z Spring Bootem. Ze względów bezpieczeństwa jest ona oddzielona od klienta. Ze względów prostoty i łatwego utrzymania, baza nie pozwala na jakiekolwiek działania Sn oraz SM.
## Uzasadnienie 
Dlaczego MonogDB? Projekt OnlyBet-mining od strony botów scrapujących nie wymaga relacyjnych powiązań między danymi. Rezultat który został wyszukany jest w postaci listy JSON i tak jest wpisywany do bazy. 
