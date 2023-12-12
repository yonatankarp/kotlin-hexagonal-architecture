# Bootstrap

Instantiates adapters and domain services and starts the built-in Undertow
web server.

## Run locally

To run the service locally, all you need to do is select the
[Launcher](src/main/kotlin/com/yonatankarp/shop/bootstrap/Launcher.kt) class and
run the `main()` function.

Once the service is up and running, you can use the
[sample-requests](../sample-requests.http) to test interactions with the
service.

ℹ️ Please note that the initial value of `Plastic Sheeting` is 55, and
each request adds 20 to the shopping cart, so after 3 calls you should be able
to see a bad request caused by not enough items in stock.

## Tests

This module contains the following tests on the entire application level:

* [End-to-End](src/test/kotlin/com/yonatankarp/shop/bootstrap/e2e)
