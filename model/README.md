# Model

Contains the domain model, i.e. those classes that represent the shopping cart
and the products. According to domain-driven design, we will distinguish here
between Entities (have an identity and are mutable) and Value Objects (have no
identity and are immutable).

## Class Diagram

![class-diagram](etc/class-diagram.png)

## Fixture

The project contains test fixture for the following classes:

- [CartFixture](src/testFixtures/kotlin/com/yonatankarp/shop/model/cart/CartFixture.kt)
- [MoneyFixture](src/testFixtures/kotlin/com/yonatankarp/shop/model/money/MoneyFixture.kt)
- [ProductFixture](src/testFixtures/kotlin/com/yonatankarp/shop/model/product/ProductFixture.kt)
