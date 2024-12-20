Feature: Currency Exchange Rates

  Scenario: Kursy walut
    Given Pobierz kursy walut
    Then Wyswietl kurs dla waluty o kodzie "USD"
    And Wyswietl kurs dla waluty o nazwie "dolar amerykański"
    And Wyswietl waluty o kursie powyzej "5"
    And Wyswietl waluty o kursie ponizej "3"