#Java 9

Breve apresentação sobre Java9

##JShell
```sh
jshell
```

### Features novas interessantes
Collection ganhou o metodo .of() que retorna uma ImmutableCollection
```java
List.of("Value1");
List.of("Value1","Value2");
Set.of("Value1","Value2");
Map.of("Key","Value");
Map.of("Key1","Value1","Key2","Value2");
```
Stream também ganhou o metodo .ofNullable()
```java
Stream.ofNullable(null);
```

Por exemplo os dias da semana
```java
//Exemplo
Map<Integer, String> weekDays = new HashMap<>(); 
weekDays.put( 1 , "Domingo" ); 
weekDays.put( 2 , "Segunda" ); 
weekDays.put( 3 , "Terça" ); 
weekDays.put( 4 , "Quarta" ); 
weekDays.put( 5 , "Quinta" ); 
weekDays.put( 6 , "Sexta" ); 
weekDays.put( 7 , "Sábado" );
weekDays.put( 8 , null );
```
Brincando com o stream
```java
//Exemplo
Map<Integer, String> weekDays = new HashMap<>(); 
weekDays.put( 1 , "Domingo" ); 
weekDays.put( 2 , "Segunda" ); 
weekDays.put( 3 , "Terça" ); 
weekDays.put( 4 , "Quarta" ); 
weekDays.put( 5 , "Quinta" ); 
weekDays.put( 6 , "Sexta" ); 
weekDays.put( 7 , "Sábado" );
weekDays.put( 8 , null );
```
```java
List<String> dayNames = weekDays .entrySet() .stream() .flatMap(e -> Stream.of(e.getValue())) .collect(Collectors.toList());
List<String> dayNames = weekDays .entrySet() .stream() .flatMap(e -> Stream.ofNullable(e.getValue())) .collect(Collectors.toList());
```
### Mais controle com dropWhile e takeWhile
Para fazer condiçoes de entrada no Stream
```java
IntStream.range( 0 , 10 ) .dropWhile(e -> e <= 5 ) .forEach(System.out::println);
```
obs. Assim que o condicional é aceito o dropWhile ou o takeWhile é ignorado e segue com os outros streams então a linta deve estar sempre ordenada

### Novos Collectors
Collectors.filtering
Collectors.flatMapping

### Atualizações no Optional
ifPresentOrElse()
Optional.stream 
### Encadeando optionals
Optional.empty().or(() -> Optional.empty()) .or(() -> Optional.empty()) .or(() -> Optional.of(10L));

##HTTP/2 Client API e modulos de imcubação
```java
URL url = new URL( "https://www.google.com.br/" ); 
URLConnection urlConnection = url.openConnection(); 
BufferedReader reader = new BufferedReader( 
        new InputStreamReader(urlConnection.getInputStream())); 
String line; while ((line = reader.readLine()) != null ) { 
    System.out.println(line); 
} 
reader.close();
```

```java
import jdk.incubator.http.*
HttpClient.newBuilder() 
    .followRedirects(HttpClient.Redirect.SECURE)
    .version(HttpClient.Version.HTTP_2) .build()
    .send(HttpRequest.newBuilder()
    .uri( 
            new URI( "https://google.com/" ))
            .GET()
            .build(), 
            HttpResponse.BodyHandler.asString())
            .body();
```
####Requisições assíncronas
```java
import jdk.incubator.http.*
CompletableFuture<HttpResponse<String>> response = HttpClient.newHttpClient()
    .sendAsync(HttpRequest.newBuilder()
    .uri( new URI( "https://turini.github.io/livro-java-9/books.csv" ))
    .GET().build(), HttpResponse.BodyHandler.asString());

```
```java
if (response.isDone()) {
    System.out.println(response.get().body()); 
} else { 
    System.out.println( "cancelando o request" ); response.cancel( true ); 
}
//ou
response.whenComplete((r,t) -> System.out.println(r.body()));
```

tbm trabalha facilmente com websocket

###Reactive Streams