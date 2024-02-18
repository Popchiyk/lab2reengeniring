# 2 Рефакторинг

1. **Константи**

  - Рекомендується використовувати константу замість жорсткого коду шляху до файлу. Це спростить оновлення шляху в майбутньому, якщо це буде потрібно.

  ```java
  private static final String FILE_PATH = "src/edu/pro/txt/harry.txt";
  ```
2. **Назва змінних**
  - Для покращення зрозуміння коду внести зміни: `freq` на `wordFrequencies` та `distincts` на `distinctWords`



5. **Інкапсуляція**

  - Необхідно розділити логіку обчислення частот у власний метод.  
  ```java
  private static int[] calculateWords(String[] words, String[] distinctWords)
  ```

# 2.1 та 2.2 Оптимiзацiя коду 

1. **Використання `Files.lines` та `flatMap`**

  - Замість читання всього вмісту файлу в рядок та подальшого розбиття на слова, можна скористатися `Files.lines` для ефективної роботи з великими файлами..

  ```java
  String[] words = Files.lines(Paths.get(FILE_PATH))
                     .flatMap(line -> Arrays.stream(line.split(" +")))
                     .toArray(String[]::new);
  ```

2. **Використання `distinct` для унікальних слів**

  - Замість ручного створення рядка для унікальних слів та подальшого розбиття, можна використати `distinct` для отримання унікальних слів.

  ```java
  String[] distincts = Arrays.stream(words)
                           .distinct()
                           .toArray(String[]::new);
  ```

3. **Використання `StringBuilder` для конкатенації рядків**

  - Замість конкатенації великої кількості рядків у циклі, можна використати `StringBuilder` для оптимізації конкатенації.

  ```java
  StringBuilder result = new StringBuilder();
  for (int i = 0; i < 30 && i < distincts.length; i++) {
      result.append(distincts[distincts.length - 1 - i]).append("\n");
  }
  System.out.println(result);
  ```

4. **Використання `groupingBy` для розрахунку частот**

  - Замість подвійного циклу для розрахунку частот, можна використати `groupingBy` для оптимізованого підрахунку частот.

  ```java
  Map<String, Long> wordFrequencies = Arrays.stream(words)
                                          .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
  String[] distincts = wordFrequencies.keySet().toArray(String[]::new);
  long[] freq = wordFrequencies.values().stream().mapToLong(Long::longValue).toArray();
  ```