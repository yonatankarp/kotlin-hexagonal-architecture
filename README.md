# kotlin-hexagonal-architecture

Kotlin implementation follows the [Hexagonal Architecture](https://www.happycoders.eu/software-craftsmanship/hexagonal-architecture/)
series of articles

---
The application provides the (simplified) backend for an online store that
includes the following functionalities:

1. Searching for products
2. Adding a product to the shopping cart
3. Retrieving the shopping cart with the products, their respective quantity, and the total price
4. Emptying the shopping cart

The core business logic we want to have in the application is as follow:

- The amount of a product added to the cart must be at least one.
- After adding a product, the total quantity of this product in the cart must
  not exceed the amount of the product available in the warehouse.

---

The repository is split into 3 different modules:

- [kotlin](./kotlin/README.md) - a plan Kotlin implementation of the application
- [quarkus](./quarkus/README.md) - replacing the Kotlin implementation with the usage of a
  framework, demonstrating how replacing the libraries we use can be.
- [springboot](./springboot/README.md) - replacing the quarkus implementation with another
  framework demonstrating how easy replacing a complete framework could be.
