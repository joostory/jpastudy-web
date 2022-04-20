package net.joostory.jpastudy.exception

@SuppressWarnings("serial")
class NotEnoughStockException: RuntimeException {
    constructor(message: String, e: Exception?): super(message, e)
    constructor(message: String): super(message)
    constructor(e: Exception): super(e)
}
