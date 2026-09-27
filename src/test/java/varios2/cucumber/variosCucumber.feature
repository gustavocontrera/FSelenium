Feature: Probar la búsqueda en Google

Scenario: Busco algo en Google
    Given navego a google
    When busco algo
    Then obtengo resultados