Feature: Outlined Scenarios

  Background:
    Given ingreso a spotify

  @compras
  Scenario Outline: Compras
    Given tengo <valor_inicial> dolares
    When gasto <valor_compra> dolares
    Then me sobran <valor_final> dolares

    Examples:
      | valor_inicial | valor_compra | valor_final |
      | 10            | 5            | 5           |
      | 20            | 8            | 12          |
      | 120           | 20           | 100         |

  @username
  Scenario Outline: Verificar username
    Given que logueado en un sitio web
    And me encuentro en la pagina principal
    When busco el nombre de usuario <username>
    Then recibo un mensaje indicando <mensaje>

    Examples:
      | username     | mensaje                         |
      | "Marcus"     | "No se encuentra en el sistema" |
      | "Rox123"     | "Nombre de usuario invalido"    |
      | "Mariangela" | "Nombre de usuario inactivo"    |

  @spotify
  Scenario Outline: Spotify Registration Errors
    Given voy a la pagina de registro
    When ingreso un email <email>
    And ingreso la confirmacion <confirmation>
    Then obtengo el error <error>

    Examples:
      | email                              | confirmation             | error                                                                                                  |
      | "ffff "                            | "ffff"                   | "Este correo electrónico no es válido. Asegúrate de que tenga un formato como este: ejemplo@email.com" |
      | "test@test.com"                    | "test@test.com"          | "Este correo electrónico ya está conectado a una cuenta. Inicia sesión."                               |
      | "selenium2021setptiembre@test.com" | "test213123123@test.com" | "Las direcciones de correo electrónico no coinciden."                                                  |
