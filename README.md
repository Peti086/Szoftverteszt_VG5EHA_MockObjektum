# BankAccount + Mockito

A `BankAccount` osztály kiegészítése egy értesítési szolgáltatással, valamint egységtesztek a külső függőségek Mockitóval történő mockolásával.

## Osztályok

| Osztály / interfész | Szerep |
|---|---|
| `BankAccount` | Bankszámla: `deposit()`, `withdraw()`, `getBalance()`. Hibás összeg esetén `IllegalArgumentException`. |
| `NotificationService` | Értesítési szolgáltatás interfésze (`send(String message)`). |
| `BankService` | Befizetést hajt végre a számlán, majd értesítést küld. A `NotificationService`-t konstruktoron keresztül kapja meg (dependency injection). |
| `ExchangeRateService` | Árfolyam-szolgáltatás interfésze (`getRate(String from, String to)`). |


## A tesztek futtatása

### Parancssorból

```bash
git clone <repository-url>
cd <repository-mappa>
mvn clean test
```

Sikeres futás esetén a kimenet végén `Failures: 0, Errors: 0` és `BUILD SUCCESS` látható.

JDK 21 vagy újabb verzión a Mockito figyelmeztetést írhat ki arról, hogy futás közben csatlakoztatja magát Java-ügynökként (`Mockito is currently self-attaching...`). Ez csak figyelmeztetés, a tesztek eredményét nem befolyásolja.

### IntelliJ IDEA-ból

1. **File → Open**, majd a projekt mappájában a `pom.xml` kiválasztása (**Open as Project**).
2. A Maven-függőségek letöltése után a `src/test/java` mappán jobb klikk → **Run 'All Tests'**.

## Tesztesetek

### `BankServiceTest` – értesítési szolgáltatás mockolása

| # | Követelmény | Tesztmetódus | Mockito |
|---|---|---|---|
| 1 | A befizetés növeli a számla egyenlegét | `depositIncreasesBalance` | `mock()` |
| 2 | Sikeres befizetés után meghívódik a `send()` | `successfulDepositCallsSend` | `verify(..., times(1))` |
| 3 | A megfelelő értesítés kerül elküldésre | `successfulDepositSendsCorrectMessage` | `verify()` |
| 4 | Sikertelen befizetés (0 és negatív összeg) esetén nincs értesítés | `failedDepositSendsNoNotification` | `verify(..., never())` |

A tesztek a `NotificationService` helyett minden esetben a `mock()`-kal létrehozott ál-objektumot használják, így nem történik valódi értesítésküldés.

### `ExchangeRateServiceTest` – mock viselkedésének beállítása

| Teszt | Tesztmetódus | Mockito |
|---|---|---|
| A mock a beállított árfolyamot (EUR → HUF: 400.0) adja vissza | `returnsStubbedRate` | `when().thenReturn()`, `verify()` |
| Be nem állított paraméterekre a mock az alapértéket (0.0) adja | `returnsDefaultForUnstubbedArguments` | `when().thenReturn()` |

### `BankAccountTest`

A `BankAccount` osztály saját egységtesztjei (egyenleg, befizetés, pénzfelvétel, hibás összegek).
