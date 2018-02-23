
# Java 9

## 1. introdução
O release oficial foi lançado em setembro de 2017 e trouxe algumas polemicas com ele, como a modularização, a troca do GC, pelo G1 e os "pacotes" de incubação.
Mas antes de falar do que é polemico. Vou começar com uma das coisas que muitos que gostam de ensinar, ou para quem quer mostrar aquele codigozinho Java para alguém, ja esperava a algum tempo. 

## 2. JShell
O jShel é um REPL (Read-Eval-Print-Loop) que nada mais é que um console onde você pode testar seu código sem ter o trabalho de compilar toda as classes. Ou até mesmo ter que abrir a IDE para rodar aquele main com 3 linhas de código para testar alguma coisa.

Para rodar basta rodar o comando ```jshel``` no terminal e pronto bora testar ele. 
```sh
jshell
```

## 3. Features 
Aproveitando o fato de agora ter um RPLR, vamos fazer uso dele, já apresentando algumas coisas, que eu pessoalmente, achei bem legais, que entraram na nova versão.

### 3.1 Collections
A classe Collection ganhou o metodo ```.of() ``` que retorna uma ```ImmutableCollection``` , e segue o mesmo principio dos metodos ```.of()``` de algumas outras classes que já usamos no dia-a-dia com o Java 8 (ex.: ```Optional.of()```)
A vantagem é que ele elem de resolver o problema de criação de lista ele ainda nos da a garantia de imutablididade. 

se quiser já pode tirar proveito do ```jshell```
```java
List.of("Value1");
List.of("Value1","Value2");
Set.of("Value1","Value1","Value2");
Map.of("Key","Value");
Map.of("Key1","Value1","Key2","Value2");
```
### 3.2 Stram

A Stream ganhou o método ```.ofNullable()```, da mesma forma que já conhecemos da classe ```Optional```; o legal dele é que evitamos mais uma ```exception``` e não precisamos mais ficar tratando ```streams``` com ```filters``` meio que corriqueiros 
```java
Stream.ofNullable(null);
```
ilustrando um pouco vamos pegar os dias da semana, supondo que tenha sido populado de alguma forma sistêmica, e que tenha vindo um valor ```null``` inesperado. 
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
Se não usar o stream vamos ver na lista do console um ```null``` que não queremos
```java
List<String> dayNames = weekDays
  .entrySet()
  .stream()
  .flatMap(e -> Stream.of(e.getValue()))
  .collect(Collectors.toList());
```
mudando só de ```.of()``` para ```.ofNullable``` ele já é filtrado de graça ;)  
```java
List<String> dayNames = weekDays
  .entrySet()
  .stream() 
  .flatMap(e -> Stream.ofNullable(e.getValue())) 
  .collect(Collectors.toList());
```

### 3.3 .dropWhile e .takeWhile

Esses dois métodos foram adicionados na classe Stream e ajuda a atender condições no meio das interações de stream.
```java
IntStream.range( 0 , 10 )
  .dropWhile(e -> e <= 5 ) 
  .forEach(System.out::println);
```
obs. Assim que o condicional é aceito o ```dropWhile``` ou o ```takeWhile``` é ignorado e segue com os outros métodos do Streams então a linta deve estar sempre ordenada

### 3.4 Novos Collectors .filtering e .flatMapping
Eles servem para continuar o algumas operações que fizerem sentido, mas no momento de coletar os dados e enviar para outra stream.

```java
Collectors.filtering
Collectors.flatMapping
```

```java 
List<Integer> numbers = List.of(1, 2, 3, 5, 5);
 
result = numbers.stream()
  .collect(Collectors.groupingBy(i -> i,
    Collectors.filtering(val -> val > 3, Collectors.counting())));
```
Obs.: o filter funcionaria munto bem, é mas para exemplificar mesmo
```java 
Blog blog1 = new Blog("1", "Nice", "Very Nice");
Blog blog2 = new Blog("2", "Disappointing", "Ok", "Could be better");
List<Blog> blogs = List.of(blog1, blog2);
         
Map<String, List<String>> authorComments2 = blogs.stream()
  .collect(Collectors.groupingBy(Blog::getAuthorName, 
        Collectors.flatMapping(blog -> blog.getComments().stream(), 
        Collectors.toList())));
```

### 3.4 Atualizações no Optional
ele ganhou o método  ```ifPresentOrElse()``` e o ```.or()``` dentro que ajuda a encadear condições caso ```null```.
O ```ifPresentOrElse()``` espera uma classe ```Consumer``` caso sucesso, se não um ele completa o método pela classe ```Runnable ``` 
```java
Optional
  .of(10)
  .ifPresentOrElse(
    integer -> System.out.println("Sou um Consumer" + (10 + integer)), 
    ()->System.out.println("eu sou um Runnable"));
```

Já o   ```.or()``` é mais simples, quando retornado ```Optional.empty()``` ele pula pro próximo. 
```java
Optional.empty()
  .or(() -> Optional.empty())
  .or(() -> Optional.empty())
  .or(() -> Optional.of(10L));
```

## 4. Módulos de incubação e HTTP/2 Client API

As polemicas começam aqui.
### 4.1 Módulos de incubação

Quando eles modularizaram o Java eles já aproveitaram e resolveram alguns problemas antigos, e como cada escolha é uma renuncia, criaram um novo modulo onde as coisas "Betas" (ou que serão testadas pela comunidade antes de ir para o release oficial). Isso significa que colocar código que funcionava na versão X. não necessariamente vai continuar funcionando na versão Y. 
Lembrando que este é um modulo de "teste", que teoricamente "nem deveria ir pra produção"

### 4.2 HTTP/2 Client API

Eles deixaram mais simples de usar a API HTTP.

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
E ela é assíncrona ;)

#### 4.3.1 Requisições assíncronas
```java
import jdk.incubator.http.*
CompletableFuture<HttpResponse<String>> response = HttpClient.newHttpClient()
    .sendAsync(HttpRequest.newBuilder()
    .uri( new URI( "https://raw.githubusercontent.com/vtferrari/primeiras-impressoes-java-9/master/products.csv" ))
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

## 5. Reactive Streams
O java 9 veio com a nova API de Fluxos Reativos e que esta no pacote ```java.util.concurrent.*```.
O paradigma reativo já esta no java a bastante tempo, com alguns projetos mantidos pela comunidade.
Segue uma lista das principais implementações de Streams reativos para java: 

- RXJava: https://github.com/ReactiveX/RxJava  
- Akka Streams: http://doc.akka.io/docs/akka/current/java/stream/index.html  
- Project Reactor: https://projectreactor.io  
- Reactive Streams: http://www.reactive-streams.org 
 
 ## 6. Shou me de coude
 ### 6.1 Compatibilidade com Spring-boot- 1.8.10
 Apesar de funcionar a própria documentação do Spring-Boot 1, desaprova a pratica pois, o java 9 mudou muito da versão anterior
 https://github.com/vtferrari/primeiras-impressoes-java-9
 ### 6.2 Java modular e suas vantagens 
O encapsulamento dos módulos ajuda na construção de um código muito mais limpo*
 https://github.com/vtferrari/java-9-modular
 
 obs.: para subir o projeto Exemplo
 ```shell
 --add-modules jdk.incubator.httpclient
```
## 7. Continuando os estudos:

### 7.1 Criando imagens de execução customizadas

```bash
jlink <options> --module-path <modulepath> --add-modules <module>[,<module>...] --output <imagename>
```


```bash
jlink --module-path $JAVA_HOME/jmods --add-modules java.base,jdk.incubator.httpclient --output small-JRE
```

ou


```bash
jlink --module-path $JAVA_HOME/jmods:mods --add-modules br.com.vtferrari --output JRE-vtferrari
```

### 7.2 Multi-Release JAR files

### 7.3 Logging API

### 7.4 Stack-Walking API

### 7.5 Garbage Collector e G1

### 7.6 Compatibilidade?
 
 
## 8. Referencias
- Livro Java 9 Interativo, reativo e modularizado: https://www.casadocodigo.com.br/products/livro-java9
- "Documentação" dos releases: http://openjdk.java.net/projects/jdk9/  
- Exemplo de codigos: https://github.com/Turini/livro-java-9
- Collectors: http://www.baeldung.com/java9-stream-collectors
- Java9 and SpringBoot: https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-with-Java-9